package com.spring_boot.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class PropertiesService {

    public static final String MAIL_USERNAME = "username";
    public static final String MAIL_PASSWORD = "password";
    public static final String MAIL_HOST = "host";
    public static final String MAIL_PORT = "port";
    public static final String PATH = "path";
    public static final String FILE_NAME_TO_SEND = "fileNameToSend";
    public static final String FILE_NAME_OF_SENT = "fileNameOfSent";


    private Properties openProperties(String path) {

        InputStream inputStream = PropertiesService.class.getResourceAsStream(path);

        Properties properties = new Properties();

        try {

            properties.load(inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;

    }

    @Bean
    public Properties getProperties() {
        return openProperties("/resources/application.properties");
    }

    public Map<String, String> getMailProperties() {

        Properties properties = getProperties();
        Map<String, String> mailProperties = new HashMap<>();

        mailProperties.put(MAIL_USERNAME, properties.getProperty("mail.username"));
        mailProperties.put(MAIL_PASSWORD, properties.getProperty("mail.password"));
        mailProperties.put(MAIL_HOST, properties.getProperty("mail.host"));
        mailProperties.put(MAIL_PORT, properties.getProperty("mail.port"));

        return mailProperties;

    }

    public Map<String, String> getFileProperties(){
        Properties properties = getProperties();
        Map<String, String> fileProperties = new HashMap<>();

        fileProperties.put(PATH, properties.getProperty("file.path"));
        fileProperties.put(FILE_NAME_TO_SEND, properties.getProperty("file.fileNameToSend"));
        fileProperties.put(FILE_NAME_OF_SENT, properties.getProperty("file.fileNameOfSent"));
        return fileProperties;
    }
}
