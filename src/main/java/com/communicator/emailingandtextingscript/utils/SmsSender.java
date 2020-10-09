package com.communicator.emailingandtextingscript.utils;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: aoyekanmi
 * @date: 09/10/2020
 */
@Service
@RequiredArgsConstructor
public class SmsSender {

    @Value("${account.sid}")
    private String ACCOUNT_SID;
    @Value("${auth.token}")
    private String AUTH_TOKEN;
    @Value("${from.phone.number}")
    private String fromPhoneNumber;
    @Value("#{T(java.lang.Integer).parseInt('${maximum.sms.retries}')}")
    private Integer maxRetries;
    private int retries = 0;

    @Async
    public void sendSms(String toPhoneNumber, String phoneMessage) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message.creator(new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber), phoneMessage).create();
            retries = 0;
        } catch (ApiException e) {

            //Retry
            if (retries != maxRetries)
                sendSms(toPhoneNumber, phoneMessage);

            retries++;
        }
    }
}
