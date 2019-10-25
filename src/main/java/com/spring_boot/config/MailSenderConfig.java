package com.spring_boot.config;

import com.home_work.spring_boot.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MailSenderConfig {

    private PropertiesService propertiesService;

    @Autowired
    public MailSenderConfig(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        Map<String, String> mailProperties = propertiesService.getMailProperties();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setUsername(mailProperties.get("username"));
        mailSender.setPassword(mailProperties.get("password"));
        mailSender.setPort(Integer.parseInt(mailProperties.get("port")));
        mailSender.setHost(mailProperties.get("host"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
