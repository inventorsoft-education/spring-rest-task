package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repo.JsonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/messages")
public class MailSenderController {

    private JsonRepo jsonRepo;

    @Autowired
    public MailSenderController(JsonRepo jsonRepo) {
        this.jsonRepo = jsonRepo;
    }

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


    @PutMapping("/{id}")
    public void updateFutureDate(@PathVariable long id,
                                 @RequestParam long date) {
        jsonRepo.changeDateById(id, date);
    }

    @DeleteMapping("/removing/{id}")
    public void deleteMessage(@PathVariable long id) {
        jsonRepo.deleteById(id);
    }

    @PostMapping("/creation")
    public void addMessage(@RequestBody Message message) {
        jsonRepo.addMessage(message);
    }

}
