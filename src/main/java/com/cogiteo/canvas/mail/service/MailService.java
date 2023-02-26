package com.cogiteo.canvas.mail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;

@Component
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    UtilisateurService userService;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("startupfoundercanvas-noreply@alwaysdata.net");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
    }

    public void sendSimpleMessage(List<String> allTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("startupfoundercanvas-noreply@alwaysdata.net");
        message.setSubject(subject); 
        message.setText(text);
        for (String to : allTo) {
            message.setTo(to); 
            emailSender.send(message);
        }
    }

    public boolean isAdmin(String adminToken) {
        return userService.IsAdmin(adminToken);
    }
}