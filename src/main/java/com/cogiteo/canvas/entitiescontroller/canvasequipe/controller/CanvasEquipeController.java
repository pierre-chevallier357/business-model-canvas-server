package com.cogiteo.canvas.entitiescontroller.canvasequipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cogiteo.canvas.entities.CanvasEquipe;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.dto.CreateCanvasEquipeDto;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.dto.UpdateCanvasEquipeDto;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.service.CanvasEquipeService;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/canvascollectif")
public class CanvasEquipeController {

  @Autowired
  CanvasEquipeService canvasEquipeService;

  @Autowired
  UtilisateurService utilisateurService;

  // TODO - UPGRADE VERS COLLECTIF
  @GetMapping("/all/{userToken}")
  public ResponseEntity<List<CanvasEquipe>> getAllCanvasForUser(@PathVariable(value = "userToken") String userToken) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // try {
    // List<CanvasEquipe> allCanvasEquipeForUser =
    // canvasEquipeService.getAllCanvasForUser(userToken);

    // if (allCanvasEquipeForUser.isEmpty()) {
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

    // return new ResponseEntity<>(allCanvasEquipeForUser, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
  }

  // TODO - UPGRADE VERS COLLECTIF
  @PostMapping("/create")
  public ResponseEntity<CanvasEquipe> createNewCanvasEquipe(@RequestBody CreateCanvasEquipeDto createCanvasEquipeDto) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // try {

    // CanvasEquipe newCanvasEquipe = new CanvasEquipe();

    // List<ElementDto> allElementsDto = createCanvasEquipeDto.getAllElements();

    // List<Element> allElements = new ArrayList<>();
    // for (ElementDto el : allElementsDto) {

    // Element element = new Element();
    // element.setCaseCanvas(el.getCaseCanvas());
    // element.setContenu(el.getContenu());
    // allElements.add(element);

    // }

    // Utilisateur user =
    // utilisateurService.getUserByToken(createCanvasEquipeDto.getUtilisateurDto().getToken());

    // newCanvasEquipe.setAllElements(allElements);
    // newCanvasEquipe.setUtilisateur(user);
    // newCanvasEquipe.setDateCreation(LocalDate.now());
    // newCanvasEquipe.setDateModification(LocalDate.now());
    // newCanvasEquipe.setNom(createCanvasEquipeDto.getNom());
    // newCanvasEquipe.setStatutEquipe("créé");
    // newCanvasEquipe.setVersion(canvasEquipeService.getLastVersion());

    // do{
    // newCanvasEquipe.setCodeInvitation(utilisateurService.generateNewToken().substring(0,6).toUpperCase());
    // }while(canvasEquipeService.IsExistedCodeInvitation(newCanvasEquipe.getCodeInvitation()));

    // CanvasEquipe cn =
    // canvasEquipeService.createNewCanvasIndividuel(newCanvasEquipe);

    // return new ResponseEntity<>(cn, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
  }

  // TODO - UPGRADE VERS COLLECTIF
  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> delete(@RequestParam(required = true) String token,
      @RequestParam(required = true) Long guidcanvas) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // try {

    // if( canvasEquipeService.IsExisted(guidcanvas)){

    // if(canvasEquipeService.candeleteCanvas(token,guidcanvas) == false){
    // return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    // }
    // canvasEquipeService.delete(guidcanvas);
    // return new ResponseEntity<>(true, HttpStatus.OK);
    // }
    // else{
    // return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
    // }

    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
  }

  // TODO - UPGRADE VERS COLLECTIF :
  // /!\ Attention cette méthode contient des bugs
  // Reprendre le fonctionnement du Canvas individuel en changeant le type du
  // canvas.
  /**
   * @param updateCanvasEquipeDto
   * @return
   */
  @PutMapping("/update")
  public ResponseEntity<CanvasEquipe> update(@RequestBody UpdateCanvasEquipeDto updateCanvasEquipeDto) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // try {

    // CanvasEquipe canvasEquipe =
    // canvasEquipeService.getCanvasById(updateCanvasEquipeDto.getGUIDCanvas());

    // if(canvasEquipe == null)
    // return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    // CanvasEquipe updateCanvasEquipe = new CanvasEquipe();

    // updateCanvasEquipe.setUtilisateur(canvasEquipe.getUtilisateur());
    // updateCanvasEquipe.setGUIDCanvas(updateCanvasEquipeDto.getGUIDCanvas());
    // updateCanvasEquipe.setNom(updateCanvasEquipeDto.getNom());
    // updateCanvasEquipe.setStatutEquipe(updateCanvasEquipeDto.getStatutEquipe());
    // updateCanvasEquipe.setCodeInvitation(updateCanvasEquipeDto.getCodeInvitation());
    // updateCanvasEquipe.setDateModification(LocalDate.now());
    // updateCanvasEquipe.setDateCreation(canvasEquipe.getDateCreation());
    // updateCanvasEquipe.setVersion(updateCanvasEquipeDto.getVersion());

    // List<UpdateElementDto> allElementsDto =
    // updateCanvasEquipeDto.getAllElements();

    // List<Element> allElements = new ArrayList<>();
    // for (UpdateElementDto el : allElementsDto) {

    // Element element = new Element();

    // element.setGUIDElement(el.getGUIDElement());
    // element.setCaseCanvas(el.getCaseCanvas());
    // element.setContenu(el.getContenu());
    // allElements.add(element);

    // }
    // updateCanvasEquipe.setAllElements(allElements);

    // CanvasEquipe cn = canvasEquipeService.update(updateCanvasEquipe);

    // return new ResponseEntity<>(cn, HttpStatus.OK);
    // } catch (Exception e) {
    // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
  }

}
