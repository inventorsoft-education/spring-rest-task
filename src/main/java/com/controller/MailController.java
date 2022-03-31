package com.controller;

import com.dto.MailDto;
import com.model.Mail;
import com.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MailController {

    MailService mailService;

    @GetMapping("/mails")
    public List<Mail> getMails(){
        return mailService.getAll();
    }

    @PostMapping()
    public void addMail(@RequestParam String sender,@RequestParam String receiver,
                        @RequestParam String content,@RequestParam String date){
        mailService.add(new MailDto(sender,receiver,content,date));
    }

    @DeleteMapping("/{id}")
    public void deleteMail(@PathVariable int id){
        mailService.delete(id);
    }

    @PatchMapping("/{id}")
    public void updateDate(@PathVariable int id, @RequestParam String date){
        mailService.update(id,date);
    }
}
