package com.smatech.backendapiservice.common;

public class Utility{
    public static String generateNewUserEmailMessage(String username, String temporaryPassword,String link) {
        String content = "<p>Hi,</p>"
                + "<p>Welcome to SmatechRentalPro. A profile has been generated for you.</p>"
                +"Username:" + username
                +"<br>Password:" + temporaryPassword
                + "<p>Click the link below to activate your account:</p>"
                + "<p><a href=\"" +link + "\">Activate Account</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        return content;

    }

}
