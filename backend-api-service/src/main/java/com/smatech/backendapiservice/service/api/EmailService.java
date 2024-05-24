package com.smatech.backendapiservice.service.api;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String to, String body) throws MessagingException;
    void sendEmail(String to, String subject, String body);
    void sendEmail(String to, String subject, int value);
}
