package com.spring_boot.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan("com")
@EnableWebMvc
public class MailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailSenderApplication.class, args);
    }
}
