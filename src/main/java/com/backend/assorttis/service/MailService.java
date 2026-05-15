package com.backend.assorttis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        if (mailSender == null) {
            System.out.println("CONSOLE LOG (SMTP Disabled):");
            System.out.println("To: " + to);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("SUCCESS: Email sent to " + to);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to send email to " + to + ": " + e.getMessage());
            // Log to console as fallback if SMTP fails even if configured
            System.out.println("FALLBACK CONSOLE LOG:");
            System.out.println("To: " + to);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);
        }
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

    public void sendPasswordResetEmail(String to, String token) {
        String resetUrl = "http://localhost:5173/reset-password?token=" + token;
        String subject = "Reset Your Assortis Password";
        String body = "Hello,\n\n" +
                "We received a request to reset your password for your Assortis account.\n" +
                "Click the link below to set a new password:\n\n" +
                resetUrl + "\n\n" +
                "If you did not request a password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "The Assortis Team";
        sendEmail(to, subject, body);
    }
}
