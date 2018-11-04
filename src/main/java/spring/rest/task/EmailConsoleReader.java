package spring.rest.task;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Repository
public class EmailConsoleReader {
    private String writeRecipientAddress() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the recipient's email address: ");
        String emailAddress = null;
        try {
            emailAddress = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isValidEmailAddress(emailAddress)) {
            System.out.println("Please enter the recipient's email address: ");
            try {
                emailAddress = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return emailAddress;
    }
    private boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    private String writeSubject(){
        BufferedReader bufferedReaderSubject = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the email subject: ");
        String subject = null;
        try {
            subject = bufferedReaderSubject.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (subject == null || subject.isEmpty()) {
            System.out.println("Please enter the email subject: ");
            try {
                subject = bufferedReaderSubject.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return subject;
    }
    private String writeText() {
        BufferedReader bufferedReaderText = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the email text: ");
        String text = null;
        try {
            text = bufferedReaderText.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (text == null || text.isEmpty()) {
            System.out.println("Please enter the email text: ");
            try {
                text = bufferedReaderText.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }
    public boolean isDateValid(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateString);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    private Date writeDate() {

        BufferedReader bufferedReaderDate = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the date of sending: ");
        String dateString = null;
        try {
            dateString = bufferedReaderDate.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while ( ! isDateValid(dateString) ) {
            System.out.println(" Date is invalid. Please enter in format: Day.Month.Year Hours:minutes");
            try {
                dateString = bufferedReaderDate.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return (new SimpleDateFormat("dd.MM.yy HH:mm")).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
    public SimpleMailMessage readEmail() {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(writeRecipientAddress());
        email.setSubject(writeSubject());
        email.setText(writeText());
        email.setSentDate(writeDate());
        return email;
    }
}