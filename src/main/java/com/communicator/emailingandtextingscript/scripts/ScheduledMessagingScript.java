package com.communicator.emailingandtextingscript.scripts;

import com.communicator.emailingandtextingscript.entities.Communication;
import com.communicator.emailingandtextingscript.utils.EmailSender;
import com.communicator.emailingandtextingscript.utils.SmsSender;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: aoyekanmi
 * @date: 09/10/2020
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledMessagingScript {

    private final SmsSender smsSender;
    private final EmailSender emailSender;

    @Value("#{T(java.lang.Boolean).parseBoolean('${activate.script}')}")
    private Boolean isScriptActive;
    @Value("#{T(java.lang.Boolean).parseBoolean('${send.sms}')}")
    private Boolean sendSms;
    @Value("#{T(java.lang.Boolean).parseBoolean('${send.email}')}")
    private Boolean sendEmail;
    private List<Communication> communications;

    @Value("${document.format}")
    private String documentFormat;

    @PostConstruct
    public void init() {
        getCommunicationObjects();
    }

    private void getCommunicationObjects() {

        if(documentFormat.equalsIgnoreCase("json")){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                communications = objectMapper.readValue(
                        new File("src/main/resources/communication.json"),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Communication.class));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(documentFormat.equalsIgnoreCase("csv")){
            try {
                MappingIterator<Communication> iterator = new CsvMapper()
                                .readerWithTypedSchemaFor(Communication.class)
                                .readValues(new File("src/main/resources/communicationcsv.csv"));
                communications = iterator.readAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Scheduled(cron = "${dispatch.cron}", zone = "GMT+1")
    public void dispatchCommunication() {

        if (!isScriptActive) {
            log.info("Script is not active");
            return;
        }

        log.info("Script is active");
        communications.forEach(communication -> {
            if (sendSms) {
                smsSender.sendSms(communication.getPhoneNumber(), communication.getPhoneMessage());
            }

            if (sendEmail) {
                emailSender.sendEmail(communication.getEmail(), "#EndSarsNow", communication.getEmailMessage());
            }
        });
    }
}
