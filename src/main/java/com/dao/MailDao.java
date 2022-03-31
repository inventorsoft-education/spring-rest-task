package com.dao;

import com.dto.MailDto;
import com.model.Mail;

import java.util.Date;
import java.util.List;

public interface MailDao {
    public void add(MailDto mail);
    public List<Mail> getAll();
    public void update(int id, String date);
    public void delete(int id);
}
