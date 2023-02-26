package com.cogiteo.canvas.entitiescontroller.canvasindividuel.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.cogiteo.canvas.entities.CanvasIndividuel;
import com.cogiteo.canvas.entities.Element;
import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.element.repository.RepositoryElement;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.dto.CreateCanvasIndividuelDto;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.dto.ElementDto;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.dto.UpdateCanvasIndividuelDto;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.dto.UpdateElementDto;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.service.CanvasIndividuelService;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/canvasindividuel")
public class CanvasIndividuelController {

  @Autowired
  CanvasIndividuelService canvasIndividuelService;

  @Autowired
  RepositoryElement elementRepository;

  @Autowired
  UtilisateurService utilisateurService;

  @GetMapping("/all/{userToken}")
  public ResponseEntity<List<CanvasIndividuel>> getAllCanvasForUser(
      @PathVariable(value = "userToken") String userToken) {

    try {
      List<CanvasIndividuel> allCanvasIndividuelForUser = canvasIndividuelService.getAllCanvasForUser(userToken);

      if (allCanvasIndividuelForUser.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(allCanvasIndividuelForUser, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<CanvasIndividuel> createNewCanvasIndividuel(
      @RequestBody CreateCanvasIndividuelDto CreateCanvasIndividuelDto) {

    try {
      CanvasIndividuel newCanvasIndividuel = new CanvasIndividuel();

      List<ElementDto> allElementsDto = CreateCanvasIndividuelDto.getAllElements();

      List<Element> allElements = new ArrayList<>();
      for (ElementDto el : allElementsDto) {

        Element element = new Element();
        element.setCaseCanvas(el.getCaseCanvas());
        element.setContenu(el.getContenu());
        allElements.add(element);

      }

      Utilisateur user = utilisateurService.getUserByToken(CreateCanvasIndividuelDto.getUtilisateurDto().getToken());

      newCanvasIndividuel.setAllElements(allElements);
      newCanvasIndividuel.setUtilisateur(user);
      newCanvasIndividuel.setDateCreation(LocalDate.now());
      newCanvasIndividuel.setDateModification(LocalDate.now());
      newCanvasIndividuel.setNom(CreateCanvasIndividuelDto.getNom());
      newCanvasIndividuel.setStatutModification(CreateCanvasIndividuelDto.getStatutModification());
      newCanvasIndividuel.setVersion(canvasIndividuelService.getLastVersion());

      CanvasIndividuel cni = canvasIndividuelService.createNewCanvasIndividuel(newCanvasIndividuel);

      return new ResponseEntity<>(cni, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> delete(@RequestParam(required = true) String token,
      @RequestParam(required = true) Long guidcanvas) {

    try {
      if (canvasIndividuelService.IsExisted(guidcanvas)) {
        if (canvasIndividuelService.candeleteCanvas(token, guidcanvas) == false) {
          return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        canvasIndividuelService.delete(guidcanvas);
        return new ResponseEntity<>(true, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
      }

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/update")
  public ResponseEntity<CanvasIndividuel> update(@RequestBody UpdateCanvasIndividuelDto updateCanvasIndividuelDto) {

    try {

      CanvasIndividuel canvasIndividuel = canvasIndividuelService
          .getCanvasById(updateCanvasIndividuelDto.getGUIDCanvas());

      if (canvasIndividuel == null)
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

      if (!updateCanvasIndividuelDto.getUtilisateurDto().getToken()
          .equals(canvasIndividuel.getUtilisateur().getToken())) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
      }

      CanvasIndividuel updateCanvasIndividuel = new CanvasIndividuel();

      updateCanvasIndividuel.setUtilisateur(canvasIndividuel.getUtilisateur());
      updateCanvasIndividuel.setGUIDCanvas(updateCanvasIndividuelDto.getGUIDCanvas());
      updateCanvasIndividuel.setDateCreation(canvasIndividuel.getDateCreation());
      updateCanvasIndividuel.setDateModification(LocalDate.now());
      updateCanvasIndividuel.setNom(updateCanvasIndividuelDto.getNom());
      updateCanvasIndividuel.setVersion(updateCanvasIndividuelDto.getVersion());
      updateCanvasIndividuel.setStatutModification(updateCanvasIndividuelDto.getStatutModification());

      List<UpdateElementDto> allElementsDto = updateCanvasIndividuelDto.getAllElements();

      List<Element> allElementsFromBodody = new ArrayList<>();
      for (UpdateElementDto el : allElementsDto) {

        Element element = new Element();

        element.setGUIDElement(el.getGUIDElement());
        element.setCaseCanvas(el.getCaseCanvas());
        element.setContenu(el.getContenu());
        updateCanvasIndividuel.getAllElements().add(element);
        allElementsFromBodody.add(element);

      }
      // updateCanvasIndividuel.setAllElements(allElementsFromBodody);

      List<Element> elementsTodelete = canvasIndividuelService.findElementsToDelete(allElementsFromBodody,
          canvasIndividuel.getAllElements());

      if (elementsTodelete.size() > 0) {
        elementRepository.deleteAll(elementsTodelete);
      }

      CanvasIndividuel canvas = canvasIndividuelService.update(updateCanvasIndividuel);

      return new ResponseEntity<>(canvas, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
