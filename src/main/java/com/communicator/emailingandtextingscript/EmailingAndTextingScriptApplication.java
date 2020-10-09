package com.communicator.emailingandtextingscript;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class EmailingAndTextingScriptApplication {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("#{T(java.lang.Integer).parseInt('${spring.mail.port}')}")
    private Integer mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${mail.smtp.auth:true}")
    private String enableAuth;

    @Value("${mail.smtp.connection.timeout:5000}")
    private String connectionTimeout;

    @Value("${mail.smtp.timeout:5000}")
    private String timeout;

    @Value("${mail.smtp.write.timeout:5000}")
    private String writeTimeout;

    @Value("${mail.smtp.starttls.enable:true}")
    private String enableTLS;

    @Value("${mail.debug:true}")
    private String enableDebug;

    public static void main(String[] args) {
        SpringApplication.run(EmailingAndTextingScriptApplication.class, args);
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", enableAuth);
        properties.put("mail.smtp.connectiontimeout", connectionTimeout);
        properties.put("mail.smtp.timeout", timeout);
        properties.put("mail.smtp.writetimeout", writeTimeout);
        properties.put("mail.smtp.starttls.enable", enableTLS);
        properties.put("mail.debug", enableDebug);

        return mailSender;
    }
}

