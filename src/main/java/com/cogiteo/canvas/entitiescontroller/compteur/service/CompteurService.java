package com.cogiteo.canvas.entitiescontroller.compteur.service;

import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.Compteur;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.repository.RepositoryCanvasEquipe;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.repository.RepositoryCanvasIndividuel;
import com.cogiteo.canvas.entitiescontroller.compteur.repository.RepositoryCompteur;

@Service
public class CompteurService {

  @Autowired
  RepositoryCompteur repositoryCompteur;

  @Autowired
  RepositoryCanvasEquipe repositoryEquipe;

  @Autowired
  RepositoryCanvasIndividuel repositoryIndividuel;

  @Autowired
  UtilisateurService userService;

  public void increment() {
    long max = repositoryCompteur.max();
    repositoryCompteur.save(new Compteur(max + 1));
  }

  public long getCompteurInitie() {
    long max = repositoryCompteur.max();
    repositoryCompteur.clear(max);
    return max;
  }

  public long getCompteurSave() {
    long compteurEquipe = repositoryEquipe.count();
    long compteurIndividuel = repositoryIndividuel.count();
    return compteurEquipe + compteurIndividuel;
  }

  public boolean isAdmin(String adminToken) {
    return userService.IsAdmin(adminToken);
  }
}
