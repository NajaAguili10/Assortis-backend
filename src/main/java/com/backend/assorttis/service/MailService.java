package com.backend.assorttis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        if (mailSender == null) {
            System.out.println("CONSOLE LOG (SMTP Disabled):");
            System.out.println("To: " + to);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("technologybri@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendVerificationEmail(String to, String code) {
        String subject = "Your Assortis Verification Code";
        String body = "Hello,\n\n" +
                "Welcome to Assortis. Your verification code is: " + code + "\n\n" +
                "Please enter this code on the registration page to verify your email address.\n\n" +
                "Best regards,\n" +
                "The Assortis Team";
        sendEmail(to, subject, body);
    }
}
