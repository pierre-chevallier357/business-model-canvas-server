package com.cogiteo.canvas.mail.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.mail.service.MailService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @GetMapping("/test")
    public ResponseEntity<Boolean> test(@RequestParam(required = true) String adminToken) {
        try {
            if (!mailService.isAdmin(adminToken)) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            List<String> to = new ArrayList<String>();
            to.add("yanis.mokni@gmail.com");
            to.add("pierre.chevallier2000@gmail.com");
            to.add("briancon.guillaume8@gmail.com");
            to.add("jersclub@gmail.com");
            to.add("loris.guerrin@gmail.com");
            to.add("touretorrax@gmail.com");

            mailService.sendSimpleMessage(to, "[features/MIAG-12] TEST",
                    "Ce courriel est envoyé depuis le serveur mail Alwaysdata.");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
