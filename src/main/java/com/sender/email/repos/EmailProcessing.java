package com.sender.email.repos;

import com.sender.email.Email;

import java.util.Date;
import java.util.List;

public interface EmailProcessing {
    public List<Email> getAll();
    public List<Email> getUnsent();
    public void addNewEmail(List<Email> emails);
    public void delete(int index);
    public void changeDate(int index, Date newDate);
    public void changeStatus(Email email);
    public void loadIntoFile();
    public void loadFromFile();
}
