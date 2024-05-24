package com.smatech.backendapiservice.service.impl;

import com.smatech.backendapiservice.service.api.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executor;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    private final Executor taskExecutor;

    public void sendEmail(String to, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("pridemoreviriri@gmail.com");
        message.setSubject("SmatechRentalPro Password Reset");
        message.setText(body);
        mailSender.send(message);
    }

    public void sendEmail(String to, String subject, String body) {
        Runnable task = new Runnable() {
            @Override
            public void run() {

                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                    helper.setFrom("info@smatech.co.zw");
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(body);
                    mailSender.send(message);
                    log.info("Email Sent!");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }

            }
        };

        taskExecutor.execute(task);

    }

    public void sendEmail(String recipientEmail, String link, int value) {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("pridemoreviriri@gmail.com", "SmatechRentalPro Support");
            helper.setTo(recipientEmail);

            String subject = "Here's the link to reset your password";

            String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>"
                    + "<p><a href=\"" + link + "\">Change my password</a></p>"
                    + "<br>"
                    + "<p>Ignore this email if you do remember your password, "
                    + "or you have not made the request.</p>";

            helper.setSubject(subject);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException ex) {

        }
    }

}
