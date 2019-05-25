package com.example.demo.controller;

import com.example.demo.model.Message;
import com.example.demo.repo.JsonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.accepted;

@RestController()
@RequestMapping("/messages/")
public class MailSenderController {

    private JsonRepo jsonRepo;

    @Autowired
    public MailSenderController(JsonRepo jsonRepo) {
        this.jsonRepo = jsonRepo;
    }

    @GetMapping
    public List<Message> getMessages() {
        List<Message> sender = jsonRepo.loadFromJsonToList();
        return sender;
    }

    @GetMapping("{id}")
    public Message getMessage(@PathVariable int id) {
        Message message = jsonRepo.findById((long) id);
        return message;
    }

    @PostMapping
    public ResponseEntity addMessage(@RequestBody Message message) {
        jsonRepo.addMessage(message);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateFutureDate(@PathVariable long id,
                                           @RequestParam long date) {
        jsonRepo.changeDateById(id, date);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable long id) {
        jsonRepo.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
