package com.spring_boot.repository.impl;

import com.spring_boot.entity.Letter;
import com.spring_boot.repository.MailDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;

@Repository
public class MailDaoImpl implements MailDao {

    @Value("${file.fileNameToSend}")
    private String mailsToSend;

    @Value("${file.fileNameOfSent}")
    private String sentMails;

    @Value("${file.path}")
    private String path;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailDaoImpl.class);

    public void saveMail(Letter letter, String fileToSave) {
        LinkedList<Letter> letters = getMails(fileToSave);
        giveId(letter, letters);
        letters.add(letter);
        outPutStream(letters, fileToSave);
    }

    private void outPutStream(LinkedList<Letter> letters, String fileToSave) {
        try (OutputStream outputStream = new FileOutputStream(getPathAndNameOfFile(fileToSave));
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

            objectOutputStream.writeObject(letters);
            objectOutputStream.flush();

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void giveId(Letter letter, LinkedList<Letter> letters) {
        final long ID_INCREMENT = 1;
        final long DEFAULT_ID = 0;
        if (letters.isEmpty()) {
            letter.setId(DEFAULT_ID);
        } else {
            letter.setId(letters.get(letters.size() - 1).getId() + ID_INCREMENT);
        }
    }

    public LinkedList<Letter> getMails(String fileName) {

        LinkedList<Letter> letters = null;
        try (InputStream inputStream = new FileInputStream(isFileExist(fileName));
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            letters = (LinkedList<Letter>) objectInputStream.readObject();

        } catch (EOFException e) {
            return new LinkedList<>();
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.error(e.getMessage());
        }
        return letters;
    }

    private File isFileExist(String fileName) {
        File file = new File(getPathAndNameOfFile(fileName));
        try {
            if (file.createNewFile())
                return file;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return file;
    }

    private String getPathAndNameOfFile(String fileName) {
        StringBuilder pathAndNameOfFile = new StringBuilder(path);
        pathAndNameOfFile.append(File.separator);
        pathAndNameOfFile.append(fileName);
        return pathAndNameOfFile.toString();
    }

    public void removeSentMail(Letter letter) {
        LinkedList<Letter> letters = getMails(mailsToSend);
        letters.remove(letter);
        outPutStream(letters, mailsToSend);
        saveMail(letter, sentMails);
    }
}
