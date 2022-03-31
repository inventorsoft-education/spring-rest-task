package com.dao;

import com.dto.MailDto;
import com.model.Mail;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LocalJsonMailDAO implements MailDao {

    private List<MailDto> data = new ArrayList<>();

    @PostConstruct
    public void init() {
        load();
    }

    @PreDestroy
    public void destroy() {
        save();
    }

    @Override
    public void add(MailDto mailDto) {
        data.add(mailDto);
    }

    @Override
    public List<Mail> getAll() {
        return data.stream()
                .map(maildto -> new Mail(data.indexOf(maildto), maildto))
                .collect(Collectors.toList());
    }

    @Override
    public void update(int id, String date) {
        if (id > data.size()) return;
        data.get(id).setDate(date);
    }

    @Override
    public void delete(int id) {
        data.remove(id);
    }

    private void load() {
        JSONParser parser = new JSONParser();
        JSONArray MailArray = null;
        try {
            MailArray = (JSONArray) parser.parse(new FileReader("JsonFile.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        for (Object o : MailArray)
        {
            JSONObject mail = (JSONObject) o;

            String sender = (String) mail.get("sender");
            String receiver = (String) mail.get("receiver");
            String context = (String) mail.get("context");
            String date = (String) mail.get("date");
            data.add(new MailDto(sender,receiver,context,date));
        }
    }

    private void save() {
        try {
            File file = new File("JsonFile.json");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (MailDto dto : data) {
                JSONObject mail = new JSONObject();
                mail.put("sender", dto.getSender());
                mail.put("receiver", dto.getReceiver());
                mail.put("content", dto.getContent());
                mail.put("date", dto.getDate());

                fileWriter.write(mail.toJSONString());
                fileWriter.flush();
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
