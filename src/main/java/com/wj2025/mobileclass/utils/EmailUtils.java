package com.wj2025.mobileclass.utils;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSenderImpl mailSender;
    public EmailUtils(JavaMailSenderImpl mailSender){
        this.mailSender = mailSender;
    }

    public boolean sendMail(String to,String subject,String textContent) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(username);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(textContent);
            mailSender.send(simpleMailMessage);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
