package com.inventor.email;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputConsole {
    private static final String REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Scanner scanner = new Scanner(System.in);

    private boolean isInput() {
        System.out.print("Would you like to enter email? (y/n): ");
        String ignore = "Y";
        String answer = scanner.nextLine();

        return answer.equalsIgnoreCase(ignore);
    }


    private String enterEmail() {
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        String address = null;
        do {
            System.out.print("Enter email: ");
            String a = scanner.nextLine();
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(a);
            if (matcher.matches()) {
                address = a;
                loop = false;
            } else {
                System.out.println("Email " + a + " is not valid, enter email again");
            }
        } while (loop);
        return address;
    }

    private LocalDateTime inputDate() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime date = null;
        boolean loop = true;
        do {
            System.out.print("Enter date (Form dd.MM.yyyy HH:mm): ");
            try {
                String now = scanner.nextLine();
                LocalDateTime formatDateTime = LocalDateTime.parse(now, formatter);
                date = formatDateTime;
                loop = false;
            } catch (DateTimeParseException e) {
                System.out.println("Form of date must be (dd.MM.yyyy HH:mm)");
            }

        }while (loop);
        return date;
    }

    private String inputText() {
        String input = "";
        Scanner space = new Scanner(System.in);
        String space2;
        while (space.hasNextLine()) {
            space2 = space.nextLine();
            if (space2.isEmpty()) {
                break;
            }
            input += space2 + "\n";
        }
        return input;
    }

    public ArrayList<Email> structureConsole() {
        ArrayList<Email> emails = new ArrayList<>();
        while (isInput()) {
            Email email = new Email();
            email.setReceiver(enterEmail());
            email.setDateOfReceiving(inputDate());
            System.out.println("Enter subject of your email: ");
            email.setSubject(inputText());
            System.out.println("Enter text of your email: ");
            email.setText(inputText());
            emails.add(email);
        }
        return emails;
    }

}
