package com.sender.email;

import org.springframework.stereotype.Component;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

@Component
public class EmailConsoleProcessing {
    private String enterEmail() {
        Scanner scanner = new Scanner(System.in);
        String email;
        InternetAddress address = null;
        boolean valid = false;
        while (!valid) {
            System.out.println("Enter recipient's email pls");
            try {
                email = scanner.nextLine();
                address = new InternetAddress(email);
                valid = true;
            } catch (AddressException e) {
                System.out.println("Incorrect email input: " + e.getMessage());
            }
        }
        return address.toString();
    }

    private Date enterDate() {
        Scanner scanner = new Scanner(System.in);
        String inputDate;
        Date date = null;
        boolean valid = false;
        while (!valid) {
            System.out.println("Please enter date when u want to send the email in format dd-MM-yyyy HH:mm");
            try {
                inputDate = scanner.nextLine();
                date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(inputDate);
                valid = true;
            } catch (ParseException e)  {
                System.out.println("Date parsing error: " + e.getMessage());
            }
        }
        return date;
    }

    private String enterText() {
        String input = "";
        Scanner keyboard = new Scanner(System.in);
        String line;
        while (keyboard.hasNextLine()) {
            line = keyboard.nextLine();
            if (line.isEmpty()) {
                break;
            }
            input += line + "\n";
        }
        return input;
    }

    public ArrayList<Email> consoleInput() {
        ArrayList<Email> emails = new ArrayList<>();
        while (isInput()) {
            Email email = new Email();
            email.setRecipient(enterEmail());
            email.setDeliveryDate(enterDate());
            System.out.println("Enter subject of email:");
            email.setSubject(enterText());
            System.out.println("Enter text of email: ");
            email.setBody(enterText());
            emails.add(email);
        }

        return emails;
    }

    private boolean isInput() {
        System.out.println("Do you want to enter next email (y/n):");
        char answer = new Scanner(System.in).nextLine().charAt(0);
        return (answer == 'y') ? true : false;
    }


}
