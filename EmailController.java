package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("/Email")
public class EmailController {

    WorkWithJsonFile workWithJsonFile;

    @Autowired
    public EmailController(WorkWithJsonFile workWithJsonFile) {
        this.workWithJsonFile = workWithJsonFile;
    }

    @RequestMapping(value = "/activeEmails", method=GET)
    private ArrayList<Email> showsActiveEmails(){
        return (ArrayList<Email>) workWithJsonFile.loadFromJsonEmailsList().stream()
                                                        .filter(email -> email.getDeliveryDate().after(new Date()) && !email.getSended())
                                                        .collect(Collectors.toList());
    }

    @RequestMapping(value = "/newEmail", method=POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    private void addNew(@RequestBody Email newEmail){
        workWithJsonFile.saveEmailToJson(newEmail);
    }

    @DeleteMapping(value = "/delete")
    private boolean deleteOne(@RequestBody Email newEmail){
        ArrayList<Email> emails=workWithJsonFile.loadFromJsonEmailsList();
        if (emails.contains(newEmail)){
            emails.remove(newEmail);
            workWithJsonFile.updateList(emails);
            return true;
        }else {
            return false;
        }
    }

    @PutMapping(value="/updateDate")
    private boolean updateDate(@RequestBody Email newEmail,
                               @RequestParam Date date){

        ArrayList<Email> emails=workWithJsonFile.loadFromJsonEmailsList();
        if (emails.contains(newEmail)){
           for (Email counter :emails){
               if (counter.equals(newEmail)){
                   counter.setDeliveryDate(date);
               }
           }
            workWithJsonFile.updateList(emails);
            return true;
        }else {
            return false;
        }


    }





}
