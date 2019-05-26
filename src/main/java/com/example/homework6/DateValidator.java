package com.example.homework6;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateValidator {

    static private final String DATE_FORMAT = "dd-MM-yyyy HH:mm";

    public static boolean validate(String s) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            dateFormat.parse(s);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static Date getDate(String s){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            //TODO make sth with this
            return null;
        }

    }
}
