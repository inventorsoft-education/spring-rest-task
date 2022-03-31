package com.service;

import com.dao.MailDao;
import com.dto.MailDto;
import com.model.Mail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class DaoMailService implements MailService {
    MailDao mailDao;

    @Override
    public void add(MailDto mailDto) {
        mailDao.add(mailDto);
    }

    @Override
    public List<Mail> getAll() {
        return mailDao.getAll();
    }

    @Override
    public void update(int id, String date) {
        mailDao.update(id, date);
    }

    @Override
    public void delete(int id) {
        mailDao.delete(id);
    }
}
