package com.sender.email.repos;

import com.sender.email.Email;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmailFileProcessingImpl implements EmailProcessing {
    private final String FILE_PATH = "cache.cer";
    private List<Email> emails;

    EmailFileProcessingImpl() {
        File file = new File(FILE_PATH);
        if(file.exists()) {
            loadFromFile();
        } else {
            emails = new ArrayList<>();
        }
    }

    @Override
    public void addNewEmail(List<Email> emails1) {
        this.emails.addAll(emails1);
        loadIntoFile();
    }

    @Override
    public void loadIntoFile() {
        try {
            FileOutputStream fileStream = new FileOutputStream(FILE_PATH);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(emails);

            fileStream.close();
            objectStream.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Writing into file error: "  + e);
        }
    }

    @Override
    public void loadFromFile() {
        try {
            FileInputStream fileStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            List<Email> data = (List<Email>)objectStream.readObject();
            this.emails = data;

            fileStream.close();
            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Reading from file error: " + e);
            this.emails = new ArrayList<>();
        }
    }

    @Override
    public List<Email> getAll() {
        loadFromFile();
        return emails;
    }

    @Override
    public List<Email> getUnsent() {
        return emails.stream()
                .filter(e -> !e.getIsSent())
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int index) {
        emails.remove(index);
        loadIntoFile();
    }

    @Override
    public void changeDate(int index, Date newDate) {
        emails.get(index).setDeliveryDate(newDate);
        loadIntoFile();
    }

    @Override
    public void changeStatus(Email email) {
        emails.get(emails.indexOf(email)).setIsSent(true);
        loadIntoFile();
    }
}
