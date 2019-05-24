package com.example.demo.exception;

public class IntegerValidator {
    public static boolean tryParse(String text) {
        boolean result = true;
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("You need to enter a number !");
            result = false;
        }
        return result;
    }
}
