package com.cogiteo.canvas.entitiescontroller.canvasequipe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.CanvasEquipe;
import com.cogiteo.canvas.entities.Element;
import com.cogiteo.canvas.entities.Utilisateur;
import com.cogiteo.canvas.entitiescontroller.element.repository.RepositoryElement;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.repository.RepositoryCanvasEquipe;

@Service
public class CanvasEquipeService {

  @Autowired
  RepositoryCanvasEquipe repository;

  @Autowired
  RepositoryElement elementRepository;

  @Autowired
  UtilisateurService userService;

public List<CanvasEquipe> getAllCanvasForUser(String userToken) {
  List<CanvasEquipe> allCanvasEquipeForUser = repository.AllCanvasEquipeOfUser(userToken);

  return allCanvasEquipeForUser;
}

public CanvasEquipe createNewCanvasIndividuel(CanvasEquipe newCanvasEquipe) {

  CanvasEquipe cEquipe = repository.save(newCanvasEquipe);
  for (Element element : newCanvasEquipe.getAllElements()) {
    element.setCanvasEquipe(cEquipe);
  }
  elementRepository.saveAll(newCanvasEquipe.getAllElements());

  return cEquipe;
}

public boolean IsExisted(Long guidCanvas) {
  CanvasEquipe canvas = repository.findByGUIDCanvas(guidCanvas);

  return canvas != null;
}

public void delete(Long guidCanvas) {
  repository.deleteById(guidCanvas);
}


public CanvasEquipe update(CanvasEquipe updateCanvasEquipe) {

 
  
  
  CanvasEquipe cEquipe = repository.save(updateCanvasEquipe);

  List<Element> elementsToDelete = new ArrayList<>();

  for (Element element : updateCanvasEquipe.getAllElements()) {
    if(element.getContenu()!=null){
      element.setCanvasEquipe(cEquipe);
    } 
    else{
      elementsToDelete.add(element);

    }
  }
  
  elementRepository.saveAll(updateCanvasEquipe.getAllElements());
  elementRepository.deleteAll(elementsToDelete);

  List<Element> updateElement = new ArrayList<>();
   for (Element elmentUpdaElement : updateCanvasEquipe.getAllElements()) {

    if(elmentUpdaElement.getContenu() != null){
      updateElement.add(elmentUpdaElement);
    }
    
   }
   cEquipe.setAllElements(updateElement);

  return cEquipe;
}

public CanvasEquipe getCanvasByCodeInvitation(String codeInvitation) {
    return repository.findByCodeInvitation(codeInvitation);
}

public CanvasEquipe getCanvasById(Long guidCanvas) {
    return repository.findByGUIDCanvas(guidCanvas);
}

public boolean IsExistedCodeInvitation(String codeInvitation) {
    return repository.findByCodeInvitation(codeInvitation) != null;
}

public boolean candeleteCanvas(String token, Long guidCanvas) {
  if(userService.IsAdmin(token)) {
    return true;
  }

  Utilisateur userReceive = userService.getUserByToken(token);
  Utilisateur userOwner = repository.findByGUIDCanvas(guidCanvas).getUtilisateur();

  return (userReceive != null && userReceive.getGUIDUtilisateur() == userOwner.getGUIDUtilisateur());
}

public String getLastVersion() {
    return repository.max();
}


}