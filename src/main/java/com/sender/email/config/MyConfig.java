package com.sender.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class MyConfig {

    @Bean
    public ThreadPoolTaskScheduler getThread(){
        ThreadPoolTaskScheduler thread = new ThreadPoolTaskScheduler();
        thread.setPoolSize(3);
        thread.setThreadNamePrefix("Task Scheduler");
        return thread;
    }

}


