package com.example.demo;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

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

    @DeleteMapping("/{id}")
    private ResponseEntity deleteOne(@PathVariable int id){
         if (workWithJsonFile.deleteEmailById(id)){
             return ResponseEntity.ok().build();
         }
         else return ResponseEntity.badRequest().build();
    }

    @PutMapping(value="/newDateForOneEmail")
    private  ResponseEntity updateDeliveryDateForOneEmail(@RequestParam int  id,
                                                          @RequestParam Date date){

       if(workWithJsonFile.updateDateById(id, date)) {
           return ResponseEntity.ok().build();
       }
       else  return ResponseEntity.badRequest().build();
    }





}
