package com.example.homework6.controllers;

import com.example.homework6.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class DateController {

    static DateValidator dateValidator;
    static private final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    @Autowired
    public DateController(DateValidator dateValidator1){
        dateValidator = dateValidator1;
    }

    public static Date dateMaker(String dateString){

        try {
            return (new SimpleDateFormat(DATE_FORMAT)).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
