package com.spring_boot.controllers;

import com.spring_boot.entity.Letter;
import com.spring_boot.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "mails")
public class MailController {

    private MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> saveMail(@RequestBody Letter letter){
        mailService.saveMail(letter);
        return ResponseEntity.ok("Mail saved");
    }
}
