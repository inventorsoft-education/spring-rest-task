package com.inventor.email;

import com.inventor.email.settings.EmailSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EmailApp {

    public static void main(String[] args) {
        ApplicationContext content = SpringApplication.run(EmailApp.class, args);
        InputConsole console = content.getBean(InputConsole.class);
        EmailSettings making = content.getBean(EmailSettings.class);
        making.addEmail(console.structureConsole());
        System.out.println(making.getAll());


    }
}
