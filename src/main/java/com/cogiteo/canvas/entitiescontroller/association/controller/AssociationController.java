package com.cogiteo.canvas.entitiescontroller.association.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.entities.Association;
import com.cogiteo.canvas.entities.CanvasIndividuel;
import com.cogiteo.canvas.entitiescontroller.association.dto.AssociationDto;
import com.cogiteo.canvas.entitiescontroller.association.service.AssociationService;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.service.CanvasEquipeService;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.service.CanvasIndividuelService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/association")
public class AssociationController {

  @Autowired
  AssociationService associationService;

  @Autowired
  CanvasIndividuelService canvasIndividuelService;

  @Autowired
  CanvasEquipeService canvasEquipeService;

  // TODO - UPGRADE VERS COLLECTIF
  @GetMapping("/allcanvasindividuel/{canvasEquipeId}")
  public ResponseEntity<List<CanvasIndividuel>> getAllCanvasIndividuelOfEquipe(
      @PathVariable(value = "canvasEquipeId") Long canvasEquipeId) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // try {
    // List<Association> allAssociationOfEquipe =
    // associationService.canvasIndividuelOfEquipe(canvasEquipeId);

    // List<CanvasIndividuel> allCanvasIndividuelOfThisEquipe = new ArrayList<>();

    // for (Association association : allAssociationOfEquipe) {
    // allCanvasIndividuelOfThisEquipe.add(association.getCanvasIndividuel());
    // }

    // if (allCanvasIndividuelOfThisEquipe.isEmpty()) {
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

    // return new ResponseEntity<>(allCanvasIndividuelOfThisEquipe, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
  }

  // TODO - UPGRADE VERS COLLECTIF
  @PostMapping("/create")
  public ResponseEntity<Association> associateCanvasIndividuelToEquipe(@RequestBody AssociationDto associationDto) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // try {

    // CanvasEquipe canvasEquipe =
    // canvasEquipeService.getCanvasByCodeInvitation(associationDto.getCodeInvitation());
    // CanvasIndividuel canvasIndividuel =
    // canvasIndividuelService.getCanvasById(associationDto.getGuidcanvasIndividuel());

    // if(canvasEquipe.getStatutEquipe().toLowerCase().equals("en cours") &&
    // canvasEquipe.getStatutEquipe().toLowerCase().equals("terminé") ){
    // return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    // }

    // Association association = new Association();

    // association.setCanvasEquipe(canvasEquipe);
    // association.setCanvasIndividuel(canvasIndividuel);
    // association.setStatutIndividuel("créé");

    // associationService.CreateAssociationCanvasEquipeIndividuel(association);

    // return new ResponseEntity<>(association, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
  }

}
