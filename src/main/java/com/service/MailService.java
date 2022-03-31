package com.service;

import com.dto.MailDto;
import com.model.Mail;

import java.util.Date;
import java.util.List;

public interface MailService {
    public void add(MailDto mailDto);

    public List<Mail> getAll();

    public void update(int id, String date);

    public void delete(int id);
}
