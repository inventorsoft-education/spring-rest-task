package com.example.demo.service;

import com.example.demo.model.Message;
import com.example.demo.model.Status;
import com.example.demo.repo.InputValue;
import com.example.demo.repo.JsonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class ShedulerService {

    private JsonRepo jsonRepo;
    private Set<Message> messageListDoNotSend;
    private List<Message> messageLists;
    private Message message;
    private MailSenderService messageService;

    @Autowired
    public ShedulerService(JsonRepo jsonRepo, MailSenderService messageService) {
        this.messageService = messageService;
        this.jsonRepo = jsonRepo;
        this.messageListDoNotSend = new LinkedHashSet<>();
        this.message = new Message();
    }

    @Scheduled(fixedDelay = 1000)
    private void dataCheck() {
        messageLists = jsonRepo.loadFromJsonToList();

        if (messageListDoNotSend.size() != 0) {
            sendMessageInFuture();
        }
        if (messageLists.size() != 0) {
            checkStatus();
        }
    }

    private void sendMessageInFuture() {
        Date currentDate = new Date();
        Iterator iter = messageListDoNotSend.iterator();
        while (iter.hasNext()) {
            message = (Message) iter.next();
            if (currentDate.getTime() >= InputValue.dateTransformer(message.getFutureDate())) {
                iter.remove();
                changeStatus((int) message.getId(), message);
                messageService.send(message);
            }
        }
    }

    private void checkStatus() {
        messageListDoNotSend = messageLists.stream()
                .filter(ml -> ml.getStatus().equals(Status.NOT_SENT))
                .collect(Collectors.toSet());
    }

    private void changeStatus(int id, Message message) {
        message.setStatus(Status.SENT);
        messageLists.set(id, message);
        jsonRepo.updateListJson(messageLists);
    }
}
