package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repo.JsonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/message")
public class MailSenderController {

    @Autowired
    private JsonRepo jsonRepo;

    @GetMapping()
    public List<Message> getMessages() {
        List<Message> sender = jsonRepo.loadFromJsonToList();
        return sender;
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable int id) {
        Message message = jsonRepo.findById((long) id);
        return message;
    }

    @PostMapping("/new")
    public void addMessage(@RequestBody Message message) {
        jsonRepo.addMessage(message);
    }

    @PutMapping("/updateDate/{id}/{date}")
    public void updateFutureDate(@PathVariable long id,
                                 @PathVariable long date) {
        jsonRepo.changeDateById(id, date);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMessage(@PathVariable long id) {
        jsonRepo.deleteById(id);
    }
}
