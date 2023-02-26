package com.cogiteo.canvas.entitiescontroller.utilisateur.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.ConnectionDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.InscriptionDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.UpdateDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.dto.UtilisateurDto;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurController {

  @Autowired
  UtilisateurService userService;

  @GetMapping("")
  public ResponseEntity<Utilisateur> getUserWithToken(@RequestParam(required = true) String token) {
    try {
      Utilisateur user = userService.getUserByToken(token);

      if (user == null) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("isUser")
  public ResponseEntity<Boolean> isUserWithToken(@RequestParam(required = true) String token) {
    try {
      Utilisateur user = userService.getUserByToken(token);

      if (user == null) {
        return new ResponseEntity<>(false, HttpStatus.OK);
      }

      return new ResponseEntity<>(true, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("isAdmin")
  public ResponseEntity<Boolean> isAdminWithToken(@RequestParam(required = true) String token) {
    try {

      if (!userService.IsAdmin(token)) {
        return new ResponseEntity<>(false, HttpStatus.OK);
      }

      return new ResponseEntity<>(true, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<UtilisateurDto> register(@RequestBody InscriptionDto inscriptionDto) {
    try {

      if (userService.IsExistedUser(inscriptionDto.getMail()))
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);

      // hasher le mot de passe avec Bcrypt
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

      Utilisateur registerUser = new Utilisateur();
      registerUser.setPrenom(inscriptionDto.getPrenom());
      registerUser.setNom(inscriptionDto.getNom());
      registerUser.setMail(inscriptionDto.getMail());
      registerUser.setEntreprise(inscriptionDto.getEntreprise());
      registerUser.setPassword(bCryptPasswordEncoder.encode(inscriptionDto.getPassword()));
      registerUser.setEstAdmin(false);

      do {
        registerUser.setToken(userService.generateNewToken());
      } while (userService.IsNotUniqueToken(registerUser.getToken()));

      UtilisateurDto utilisateur = new UtilisateurDto();

      utilisateur.setPrenom(inscriptionDto.getPrenom());
      utilisateur.setNom(inscriptionDto.getNom());
      utilisateur.setMail(inscriptionDto.getMail());
      utilisateur.setEntreprise(inscriptionDto.getEntreprise());
      utilisateur.setEstAdmin(registerUser.isEstAdmin());
      utilisateur.setToken(registerUser.getToken());

      userService.register(registerUser);

      return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/login")
  public ResponseEntity<UtilisateurDto> login(@RequestBody ConnectionDto connectionDto) {
    try {
      if (!userService.IsExistedUser(connectionDto.getMail()))
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

      // hasher le mot de passe avec Bcrypt
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

      Utilisateur loginUser = userService.getUserByMail(connectionDto.getMail());

      if (!bCryptPasswordEncoder.matches(connectionDto.getPassword(), loginUser.getPassword()))
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

      UtilisateurDto utilisateur = new UtilisateurDto();

      utilisateur.setPrenom(loginUser.getPrenom());
      utilisateur.setNom(loginUser.getNom());
      utilisateur.setMail(loginUser.getMail());
      utilisateur.setEntreprise(loginUser.getEntreprise());
      utilisateur.setEstAdmin(loginUser.isEstAdmin());
      utilisateur.setToken(loginUser.getToken());

      return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/update")
  public ResponseEntity<UtilisateurDto> update(@RequestBody UpdateDto updateDto) {

    try {

      Utilisateur actualUser = userService.getUserByMail(updateDto.getMail());

      if (actualUser == null)
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

      if (!actualUser.getToken().equals(updateDto.getToken()))
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

      // hasher le mot de passe avec Bcrypt
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

      Utilisateur updateUser = new Utilisateur();

      if (updateDto.getOlPassword().isEmpty()) {
        updateUser.setGUIDUtilisateur(actualUser.getGUIDUtilisateur());
        updateUser.setPrenom(updateDto.getPrenom());
        updateUser.setNom(updateDto.getNom());
        updateUser.setMail(updateDto.getMail());
        updateUser.setEntreprise(updateDto.getEntreprise());
        updateUser.setEstAdmin(actualUser.isEstAdmin());
        updateUser.setPassword(actualUser.getPassword());
      } else {

        if (!bCryptPasswordEncoder.matches(updateDto.getOlPassword(), actualUser.getPassword())) {
          return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        updateUser.setGUIDUtilisateur(actualUser.getGUIDUtilisateur());
        updateUser.setPrenom(updateDto.getPrenom());
        updateUser.setNom(updateDto.getNom());
        updateUser.setMail(updateDto.getMail());
        updateUser.setEntreprise(updateDto.getEntreprise());
        updateUser.setEstAdmin(actualUser.isEstAdmin());

        updateUser.setPassword(bCryptPasswordEncoder.encode(updateDto.getNewPassword()));

      }

      do {
        updateUser.setToken(userService.generateNewToken());
      } while (userService.IsNotUniqueToken(updateUser.getToken()));

      UtilisateurDto utilisateur = new UtilisateurDto();

      utilisateur.setPrenom(updateUser.getPrenom());
      utilisateur.setNom(updateUser.getNom());
      utilisateur.setMail(updateUser.getMail());
      utilisateur.setEntreprise(updateUser.getEntreprise());
      utilisateur.setEstAdmin(updateUser.isEstAdmin());
      utilisateur.setToken(updateUser.getToken());

      userService.update(updateUser);

      return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/deleteByToken")
  public ResponseEntity<Boolean> Delete(@RequestParam(required = true) String token) {
    try {

      Utilisateur user = userService.getUserByToken(token);

      if (user == null) {
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
      }

      if (user.isEstAdmin() && userService.IsLastAdmin(user.getGUIDUtilisateur()))
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

      if (user.getMail().equals("michel.cezon@cogiteo.net")) {
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
      }
      if (userService.CanDelete(user.getGUIDUtilisateur())) {

        userService.Delete(user.getGUIDUtilisateur());
        return new ResponseEntity<>(true, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<Object>> getUtilisateur(@RequestParam(required = true) String tokenAdmin,
      @RequestParam(required = false, defaultValue = "") String nom,
      @RequestParam(required = false, defaultValue = "") String prenom,
      @RequestParam(required = false, defaultValue = "") String mail,
      @RequestParam(required = false, defaultValue = "") String entreprise,
      @RequestParam(required = false) Boolean admin,
      @RequestParam(required = false, defaultValue = "guidutilisateur") String sortColumn,
      @RequestParam(required = false, defaultValue = "desc") String sortWay,
      @RequestParam(required = false, defaultValue = "1") Integer page) {
    try {

      if (!userService.IsAdmin(tokenAdmin)) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }
      List<Object> filterResult = userService.filterUtilisateur(nom, prenom, mail, entreprise, admin, sortColumn,
          sortWay.toUpperCase(), page);

      List<Utilisateur> l = (List<Utilisateur>) filterResult.get(2);
      if (l.isEmpty()) {
        return new ResponseEntity<>(filterResult, HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(filterResult, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PatchMapping("/grant")
  public ResponseEntity<Boolean> setAdmin(@RequestParam(required = true) Long idUtilisateur,
      @RequestParam(required = true) Boolean admin, @RequestParam(required = true) String adminToken) {
    try {
      if (!userService.IsAdmin(adminToken))
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

      if (!userService.IsExistedUser(idUtilisateur))
        return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

      Utilisateur updateUser = userService.getUserByGUIDtilisateur(idUtilisateur);

      if (updateUser.getMail().equals("michel.cezon@cogiteo.net")) {
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
      }

      if (updateUser.isEstAdmin() && userService.IsLastAdmin(updateUser.getGUIDUtilisateur()))
        return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);

      updateUser.setEstAdmin(admin);

      userService.update(updateUser);

      return new ResponseEntity<>(true, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
