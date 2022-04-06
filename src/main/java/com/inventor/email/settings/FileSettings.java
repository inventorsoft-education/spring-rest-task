package com.inventor.email.settings;

import com.inventor.email.Email;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FileSettings implements EmailSettings {
    private final String FILE_PATH = "data.cer";
    private List<Email> emails;

    @Override
    public List<Email> getAll() {
        loadOutOfFile();
        return emails;
    }

    @Override
    public List<Email> getUnsent() {
        return emails.stream()
                .filter(e -> !e.getSend())
                .collect(Collectors.toList());
    }

    @Override
    public void deleteEmail(int index) {
        emails.remove(index);
        loadInFile();
    }

    @Override
    public void changeDate(int index, LocalDateTime newDate) {
        emails.get(index).setDateOfReceiving(newDate);
        loadInFile();
    }

    @Override
    public void changeCondition(Email email) {
        emails.get(emails.indexOf(email)).setSend(true);
        loadInFile();
    }

    FileSettings() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            loadOutOfFile();
        } else {
            emails = new ArrayList<>();
        }
    }

    @Override
    public void addEmail(List<Email> emails1) {
        this.emails.addAll(emails1);
        loadInFile();
    }

    @Override
    public void loadInFile() {
        try {
            FileOutputStream fileStream = new FileOutputStream(FILE_PATH);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(emails);
            fileStream.close();
            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to write file: " + e);
        }
    }

    @Override
    public void loadOutOfFile() {
        try {
            FileInputStream fileStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            List<Email> data = (List<Email>) objectStream.readObject();
            this.emails = data;
            fileStream.close();
            objectStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to read from file: " + e);
            this.emails = new ArrayList<>();
        }
    }
}
