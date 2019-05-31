package com.example.homework6.controllers;

import com.example.homework6.*;
import com.example.homework6.repos.EmailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class EmailController {

    @Autowired
    EmailRepo emailRepo;

    @Autowired
    EmailSender emailSender;

    @Autowired
    TaskSchedule taskSchedule;


    @GetMapping(value = "/create")
    private String createEmail(Model model) {
        model.addAttribute("emailItem", new EmailItem());
        return "create";
    }

    @PostMapping(value = "/saveEmail")
    private String saveEmail(@Valid @ModelAttribute("emailItem") EmailItem emailItem, BindingResult result){

        if(result.hasErrors()){
            return "create";
        }else if(!DateValidator.validate(emailItem.getDeliveryDate())){
            FieldError error = new FieldError("emailItem", "deliveryDate",
                    "Date is wrong! Please, be carefully! DATE format is 'dd-MM-yyyy HH:mm'!");
            result.addError(error);
            return "create";
        }

        emailRepo.save(emailItem);
        return "index";
    }

    @GetMapping(value = "/emails")
    private ModelAndView getEmails(){
        Iterable<EmailItem> emails = emailRepo.findAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("emails");
        mv.addObject("emails", emails);
        mv.getModel();
        return mv;
    }

    @GetMapping(value = "/email")
    private @ResponseBody EmailItem getEmail(Integer emailId) {
        EmailItem email = new EmailItem();
       Optional<EmailItem> e =  emailRepo.findById(emailId);
        if(e.isPresent()) {
            email = e.get();
        } else {
            //TODO there is no Email in the repo with 'id'
        }
        return email;
    }

    @PutMapping(value = "/update")
    private ModelAndView updateEmail(@RequestParam Integer id, String emailRecipient, String emailSubject, String emailBody, String deliveryDate){
        //TODO validation
        Optional<EmailItem> e =  emailRepo.findById(id);
        if(e.isPresent()) {
            EmailItem email = e.get();
            email.setEmailRecipient(emailRecipient);
            email.setDeliveryDate(deliveryDate);
            email.setEmailBody(emailBody);
            email.setEmailSubject(emailSubject);
            email.setStatusDelivery(false);
            emailRepo.save(email);
            return getEmails();
        } else {
            //TODO there is no Email in the repo with 'id'
            return null;
        }
    }

    @DeleteMapping(value = "/delete")
    private @ResponseBody String deleteEmail(Integer emailId) {
        String msg = "";
        Optional<EmailItem> e =  emailRepo.findById(emailId);
        if(e.isPresent()) {
            emailRepo.delete(e.get());
            msg = "Email deleted!";
        } else {
            msg = "Sorry, I can't do it...";
        }
        return msg;
    }


    public ArrayList<EmailItem> getActive(){
       return findAllActive(emailRepo.findAll());
    }

    public boolean isAllSended(){
        if(findAllActive(emailRepo.findAll()).size()>0){
            return false;
        }
        return true;
    }

    public static ArrayList<EmailItem> findAllActive(Iterable<EmailItem> emailItems1){
        Iterable<EmailItem> emailItems = emailItems1;
        ArrayList<EmailItem> itemArrayList = new ArrayList<>();
        for (EmailItem email: emailItems) {
            if(!email.isStatusDelivery()){
                itemArrayList.add(email);
            }
        }


        return (sortByDate(itemArrayList));
    }

    public static ArrayList<EmailItem> sortByDate(ArrayList<EmailItem> emails){
        boolean isSorted = false;
        EmailItem value;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < emails.size()-1; i++) {
                if(emails.get(i).compareTo(emails.get(i+1)) > 0){
                    isSorted = false;

                    value = emails.get(i);
                    emails.add(i,emails.get(i+1));
                    emails.add(i+1, value);
                }
            }
        }
        return emails;

    }

}

