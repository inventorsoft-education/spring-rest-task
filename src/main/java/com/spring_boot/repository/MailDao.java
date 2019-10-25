package com.spring_boot.repository;

import com.home_work.spring_boot.entity.Letter;
import com.home_work.spring_boot.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.LinkedList;
import java.util.Map;

@Repository
public class MailDao {

    private static final String MAILS_TO_SEND = "fileNameToSend";
    private static final String SENT_MAILS = "fileNameOfSent";


    private PropertiesService propertiesService;

    @Autowired
    public MailDao(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return letters;
    }

    private File isFileExist(String fileName) {
        File file = new File(getPathAndNameOfFile(fileName));
        try {
            if (file.createNewFile())
                return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private String getPathAndNameOfFile(String fileName) {
        Map<String, String> fileProperties = propertiesService.getFileProperties();
        StringBuilder pathAndNameOfFile = new StringBuilder(fileProperties.get("path"));
        pathAndNameOfFile.append(File.separator);
        pathAndNameOfFile.append(fileProperties.get(fileName));
        return pathAndNameOfFile.toString();
    }

    public void removeSentMail(Letter letter) {
        LinkedList<Letter> letters = getMails(MAILS_TO_SEND);
        letters.remove(letter);
        outPutStream(letters, MAILS_TO_SEND);
        saveMail(letter, SENT_MAILS);
    }
}
