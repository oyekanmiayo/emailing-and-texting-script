package com.communicator.emailingandtextingscript.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author: aoyekanmi
 * @date: 09/10/2020
 */
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;
    @Value("#{T(java.lang.Integer).parseInt('${maximum.email.retries}')}")
    private Integer maxRetries;
    private int retries = 0;

    @Async
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setText(body, true);
            helper.setSubject(subject);

            javaMailSender.send(message);
            retries = 0;
        } catch (MessagingException e) {
            //Retry
            if (retries != maxRetries)
                sendEmail(to, subject, body);

            retries++;
        }
    }

}
