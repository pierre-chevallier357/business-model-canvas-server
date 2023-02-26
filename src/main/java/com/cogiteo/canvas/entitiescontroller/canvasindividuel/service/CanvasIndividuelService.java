package com.cogiteo.canvas.entitiescontroller.canvasindividuel.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.CanvasIndividuel;
import com.cogiteo.canvas.entities.Element;
import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.element.repository.RepositoryElement;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.repository.RepositoryCanvasIndividuel;

@Service
public class CanvasIndividuelService {
  @Autowired
  RepositoryCanvasIndividuel repository;

  @Autowired
  RepositoryElement elementRepository;

  @Autowired
  UtilisateurService userService;

  public List<CanvasIndividuel> getAllCanvasForUser(String userToken) {
    List<CanvasIndividuel> allCanvasIndividuelForUser = repository.AllCanvasIndividuelOfUser(userToken);

    return allCanvasIndividuelForUser;
  }

  public CanvasIndividuel createNewCanvasIndividuel(CanvasIndividuel newCanvasIndividuel) {

    CanvasIndividuel cIndiv = repository.save(newCanvasIndividuel);
    for (Element element : newCanvasIndividuel.getAllElements()) {
      element.setCanvasIndividuel(cIndiv);
    }
    elementRepository.saveAll(newCanvasIndividuel.getAllElements());

    return cIndiv;
  }

  public void delete(Long guidCanvas) {
    repository.deleteById(guidCanvas);
  }

  public boolean IsExisted(Long guidCanvas) {

    CanvasIndividuel canvas = repository.findByGUIDCanvas(guidCanvas);

    return canvas != null;

  }

  public CanvasIndividuel getCanvasById(Long guidCanvas) {
    return repository.findByGUIDCanvas(guidCanvas);
  }

  public CanvasIndividuel update(CanvasIndividuel updateCanvasIndividuel) {

    CanvasIndividuel cIndiv = repository.save(updateCanvasIndividuel);

    List<Element> elementsToDelete = new ArrayList<>();

    for (Element element : updateCanvasIndividuel.getAllElements()) {
      if (element.getContenu() != null) {
        element.setCanvasIndividuel(cIndiv);
      } else {
        elementsToDelete.add(element);

      }
    }
    elementRepository.saveAll(updateCanvasIndividuel.getAllElements());
    elementRepository.deleteAll(elementsToDelete);

    List<Element> updateElement = new ArrayList<>();
    for (Element elmentUpdaElement : updateCanvasIndividuel.getAllElements()) {

      if (elmentUpdaElement.getContenu() != null) {
        updateElement.add(elmentUpdaElement);
      }

    }
    cIndiv.setAllElements(updateElement);
    return cIndiv;

  }

  public boolean candeleteCanvas(String token, Long guidCanvas) {
    if (userService.IsAdmin(token)) {
      return true;
    }

    Utilisateur userReceive = userService.getUserByToken(token);
    Utilisateur userOwner = repository.findByGUIDCanvas(guidCanvas).getUtilisateur();

    return (userReceive != null && userReceive.getGUIDUtilisateur() == userOwner.getGUIDUtilisateur());
  }

  public String getLastVersion() {
    return repository.max();
  }

  public boolean isElementOfList(Element element, List<Element> elements) {
    for (Element elem : elements) {
      boolean result = false;
      if (element.getGUIDElement().equals(elem.getGUIDElement())) {
        result = true;
      }
      if(result==true) return result;
    }
    return false;
  }

  public List<Element> findElementsToDelete(List<Element> allElementsFromBodody, List<Element> allElements) {

    List<Element> elementsBDToDelete = new ArrayList<>();

    Iterator<Element> i = allElementsFromBodody.iterator();
    while (i.hasNext()) {
      Element s = i.next(); 
      if (s.getGUIDElement() == null) {
        i.remove();
      }

    }

    if (allElements.size() >= allElementsFromBodody.size()) {
      Iterator<Element> it = allElements.iterator();
      while (it.hasNext()) {
        Element s = it.next();
        if (isElementOfList(s, allElementsFromBodody) == false) {
          elementsBDToDelete.add(s);
        }
      }
    }
   

    return elementsBDToDelete;

  }

}
