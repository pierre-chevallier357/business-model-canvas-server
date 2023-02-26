package com.cogiteo.canvas.entitiescontroller.compteur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.entitiescontroller.compteur.service.CompteurService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/compteur")
public class CompteurController {

  @Autowired
  CompteurService compteurService;

  @PutMapping("/increment")
  public ResponseEntity<String> increment() {
    try {

      compteurService.increment();

      return new ResponseEntity<>(null, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/initie")
  public ResponseEntity<String> compteurInitie(@RequestParam(required = true) String adminToken) {
    try {
      if (!compteurService.isAdmin(adminToken)) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }

      String count = String.valueOf(compteurService.getCompteurInitie());

      return new ResponseEntity<>(count, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/save")
  public ResponseEntity<String> compteurSave(@RequestParam(required = true) String adminToken) {
    try {
      if (!compteurService.isAdmin(adminToken)) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }

      String count = String.valueOf(compteurService.getCompteurSave());

      return new ResponseEntity<>(count, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
