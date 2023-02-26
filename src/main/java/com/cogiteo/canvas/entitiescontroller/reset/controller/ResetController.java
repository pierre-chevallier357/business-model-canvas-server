package com.cogiteo.canvas.entitiescontroller.reset.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.entities.Reset;
import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.reset.service.ResetService;
import com.cogiteo.canvas.mail.service.MailService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reset")
public class ResetController {

  @Autowired
  MailService mailService;

  @Autowired
  ResetService resetService;

  @GetMapping("/user")
  public ResponseEntity<Void> reset(@RequestParam String mail) {
    try {
      resetService.cleanDatabase();

      if (!resetService.IsExistedUser(mail))
        return new ResponseEntity<>(null, HttpStatus.OK);

      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      Random rnd = new Random();
      int count = 0;

      String generateCode = "";

      do {
        if (count == 3)
          break;
        int number = rnd.nextInt(999999);
        generateCode = String.format("%06d", number);
        count++;
      } while (!resetService.IsUniqueCode(generateCode, bCryptPasswordEncoder));

      if (count == 3)
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

      String hashedGenerateCode = bCryptPasswordEncoder.encode(generateCode);

      Reset newReset = new Reset(mail, hashedGenerateCode);
      newReset = resetService.create(newReset);

      if (newReset.getGUIDReset() > 0) {
        mailService.sendSimpleMessage(mail, "SFCanvas : Demande de réinitialisation de mot de passe",
            "Bonjour !\n\n" +
                "*** English below\n" +
                "Pour réinitialiser le mot de passe de votre compte Startup Founder Canvas, saisissez le code suivant : "
                + generateCode + ".\n" +
                "Veuillez remarquer que ce code expirera dans 15 minutes.\n\n" +
                "Si vous n'avez pas demandé cette réinitialisation, vous pouvez ignorer cet e-mail sans craindre pour votre compte.\n\n"
                +
                "Bien cordialement,\n\n" +
                "The Startup Founder Canvas Team\n" +
                "contact@startupfoundercanvas.com\n" +
                "www.startupfoundercanvas.com\n\n\n" +
                "*** Français au dessus\n" +
                "To reset the password for your Startup Founder Canvas account, enter the following code: "
                + generateCode + ".\n" +
                "Please note that this code will expire in 15 minutes.\n\n" +
                "If you did not request this reset, you can ignore this email without fear for your account.\n\n" +
                "Best regards,\n\n" +
                "The Startup Founder Canvas Team\n" +
                "contact@startupfoundercanvas.com\n" +
                "www.startupfoundercanvas.com\n\n\n");
      }

      return new ResponseEntity<>(null, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.OK);
    }
  }

  @GetMapping("")
  public ResponseEntity<Boolean> updatePassword(@RequestParam String mail, @RequestParam String code,
      @RequestParam String password) {
    try {
      resetService.cleanDatabase();

      if (!resetService.IsExistedUser(mail))
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

      Reset reset = resetService.findByMail(mail);

      if (reset == null)
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

      if (bCryptPasswordEncoder.matches(code, reset.getCode())) {
        Utilisateur u = resetService.getUserByMail(mail);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        resetService.updateUtilisateur(u);

        if (resetService.findByMail(mail) != null) {
          resetService.delete(reset);
        }

        mailService.sendSimpleMessage(mail, "SFCanvas : Mot de passe modifié",
            "Bonjour !\n\n" +
                "*** English below\n" +
                "Votre mot de passe a été modifié. Si vous n'êtes pas à l'origine de ce changement, mettez à jour votre compte.\n\n"
                +
                "Bien cordialement,\n\n" +
                "The Startup Founder Canvas Team\n" +
                "contact@startupfoundercanvas.com\n" +
                "www.startupfoundercanvas.com\n\n\n" +
                "*** Français au dessus\n" +
                "Your password has been changed. If you did not cause the change, please update your account.\n\n" +
                "Best regards,\n\n" +
                "The Startup Founder Canvas Team\n" +
                "contact@startupfoundercanvas.com\n" +
                "www.startupfoundercanvas.com\n\n\n");

        return new ResponseEntity<>(true, HttpStatus.OK);
      } else {
        reset.setTentative(reset.getTentative() - 1);
        resetService.updateReset(reset);
        return new ResponseEntity<>(false, HttpStatus.OK);
      }

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
