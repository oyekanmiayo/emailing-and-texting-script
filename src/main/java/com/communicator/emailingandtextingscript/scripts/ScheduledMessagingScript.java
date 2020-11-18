package com.communicator.emailingandtextingscript.scripts;

import com.communicator.emailingandtextingscript.entities.Communication;
import com.communicator.emailingandtextingscript.utils.EmailSender;
import com.communicator.emailingandtextingscript.utils.SmsSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    public List<Communication> communications;

    final String DEFAULT_PATH_NAME = "src/main/resources/communication.json";

    @PostConstruct
    public void init() {
        initCommunicationObjects(new File(DEFAULT_PATH_NAME));
    }

    public void initCommunicationObjects(File file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Communication.class);
            communications = objectMapper.readValue(file, listType);
        } catch (IOException e) {
            e.printStackTrace();
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

    public List<Communication> getCommunications() {
        return communications;
    }
}
