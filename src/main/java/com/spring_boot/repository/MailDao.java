package com.spring_boot.repository;

import com.spring_boot.entity.Letter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;

@Service
public interface MailDao {

    void saveMail(Letter letter, String fileToSave);

    LinkedList<Letter> getMails(String fileName);

    void removeSentMail(Letter letter);
}
