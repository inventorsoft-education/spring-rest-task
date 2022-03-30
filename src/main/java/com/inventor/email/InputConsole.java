package com.inventor.email;

import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputConsole {
    private boolean isInput() {
        System.out.print("Чи бажаєте ви ввести пошту? (y/n): ");
        char answer = new Scanner(System.in).nextLine().charAt(0);
        if(answer == 'y' || answer == 'Y'){
            return true;
        }
        else {
            return false;
        }
    }

    private static final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private String enterEmail() {
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        String address = null;
        do {
            System.out.print("Введіть пошту: ");
            String a = scanner.nextLine();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(a);
            if (matcher.matches()){
                address = a;
                loop = false;
            }
            else {
                System.out.println("Пошта " + a + " не є можливою, введіть пошту заново");
            }
        }while (loop);
        return address;
    }

    private Date inputDate() {
        Scanner scanner = new Scanner(System.in);
        String inputDate;
        Date date = null;
        boolean okey = false;
        while (!okey) {
            System.out.println("Напишіть дату відправлення повідомлення. Формат: (дд.мм.рррр гг:хв)");
            try {
                inputDate = scanner.nextLine();
                date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(inputDate);
                okey = true;
            } catch (ParseException e)  {
                System.out.println("Неправильний формат дати! Формат: (дд.мм.рррр гг:хв)");
            }
        }
        return date;
    }

    private String inputText() {
        String input = "";
        Scanner probil = new Scanner(System.in);
        String vidstup;
        while (probil.hasNextLine()) {
            vidstup = probil.nextLine();
            if (vidstup.isEmpty()) {
                break;
            }
            input += vidstup + "\n";
        }
        return input;
    }

    public ArrayList<Email> structureConsole() {
        ArrayList<Email> emails = new ArrayList<>();
        while (isInput()) {
            Email email = new Email();
            email.setOderzhuvach(enterEmail());
            email.setDataOtrymannya(inputDate());
            System.out.println("Введіть ТЕМУ вашого повідомлення: ");
            email.setTema(inputText());
            System.out.println("Введіть ТЕКСТ вашого повідомлення: ");
            email.setTekst(inputText());
            emails.add(email);
        }
        return emails;
    }
}
