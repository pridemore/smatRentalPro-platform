package com.smatech.backendapiservice.common;

public class Utility{
    public static String generateNewUserEmailMessage(String username, String temporaryPassword) {

        StringBuilder sb = new StringBuilder();
        sb.append("Hi.\n\nWelcome to SmatechRentalPro. A profile has been generated for you.");
        sb.append("\n\nUsername: " + username + "\n Password: " + temporaryPassword);
        sb.append("\nIf this was a mistake kindly ignore this message");
        return sb.toString();
    }
}
