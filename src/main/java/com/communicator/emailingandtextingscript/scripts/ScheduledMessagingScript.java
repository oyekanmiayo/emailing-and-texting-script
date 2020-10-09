package com.communicator.emailingandtextingscript.scripts;

import com.communicator.emailingandtextingscript.entities.Communication;
import com.communicator.emailingandtextingscript.utils.EmailSender;
import com.communicator.emailingandtextingscript.utils.SmsSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
@Component
@RequiredArgsConstructor
public class ScheduledMessagingScript {

    private final SmsSender smsSender;
    private final EmailSender emailSender;

    @Value("#{T(java.lang.Boolean).parseBoolean('${start.cron}')}")
    private Boolean isScriptActive;
    private List<Communication> communications;
    private int index;

    @PostConstruct
    public void init() {
        index = 0;
        getCommunicationObjects();
    }

    private void getCommunicationObjects() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            communications = objectMapper.readValue(
                    new File("src/main/resources/communication.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Communication.class));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${dispatch.cron}", zone = "GMT+1")
    public void dispatchCommunication() {

        if (!isScriptActive) {
            return;
        }

        // This resets index, so that communication is started from the beginning
        // May not be what you want
        if (index == communications.size()) {
            index = 0;
        }

        Communication communication = communications.get(index);
        smsSender.sendSms(communication.getPhoneNumber(), communication.getPhoneMessage());
        emailSender.sendEmail(communication.getEmail(), "#EndSarsNow", communication.getEmailMessage());
        index++;
    }
}
