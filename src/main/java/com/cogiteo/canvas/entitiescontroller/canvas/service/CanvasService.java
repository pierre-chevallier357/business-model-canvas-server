package com.cogiteo.canvas.entitiescontroller.canvas.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.CanvasEquipe;
import com.cogiteo.canvas.entities.CanvasIndividuel;
import com.cogiteo.canvas.entitiescontroller.canvas.object.Canvas;
import com.cogiteo.canvas.entitiescontroller.canvasequipe.repository.RepositoryCanvasEquipe;
import com.cogiteo.canvas.entitiescontroller.canvasindividuel.repository.RepositoryCanvasIndividuel;
import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;

@Service
public class CanvasService {
  @Autowired
  RepositoryCanvasEquipe repositoryEquipe;

  @Autowired
  RepositoryCanvasIndividuel repositoryIndividuel;

  @Autowired
  UtilisateurService userService;

  public void filterCanvas(String nom, String statut, String version, String nomUser, String prenomUser,
      String entreprise, String type, Calendar dateStart, Calendar dateEnd, String sortColumn, String sortWay,
      Integer page,
      List<Object> result) {

    List<CanvasEquipe> allCanvasEquipeFilter = repositoryEquipe.filterEquipe(nom, statut, nomUser, prenomUser, version,
        entreprise, dateStart, dateEnd);

    List<CanvasIndividuel> allCanvasIndividuelFilter = repositoryIndividuel.filterIndividuel(nom, statut, nomUser,
        prenomUser, version, entreprise, dateStart, dateEnd);

    if (allCanvasIndividuelFilter.size() != 0 || allCanvasEquipeFilter.size() != 0) {

      for (CanvasIndividuel canvasI : allCanvasIndividuelFilter) {
        canvasI.setColumnToCompare(sortColumn);
      }

      for (CanvasEquipe canvasE : allCanvasEquipeFilter) {
        canvasE.setColumnToCompare(sortColumn);
      }

      List<Canvas> listCanvasReturn = new ArrayList<Canvas>();

      for (CanvasEquipe canvasE : allCanvasEquipeFilter) {
        if (type.equals("") || type.toUpperCase().equals("COLLECTIF")) {
          Canvas cNew = new Canvas(canvasE.getVersion(), canvasE.getDateCreation(), canvasE.getDateModification(),
              canvasE.getNom(), canvasE.getStatutEquipe(), canvasE.getAllElements(), canvasE.getGUIDCanvas(),
              canvasE.getUtilisateur(), canvasE.getAllAssociations(), canvasE.getCodeInvitation(), "COLLECTIF",
              sortColumn);
          listCanvasReturn.add(cNew);
        }
      }

      for (CanvasIndividuel canvasI : allCanvasIndividuelFilter) {
        if (type.equals("") || type.toUpperCase().equals("INDIVIDUEL")) {
          Canvas cNew = new Canvas(canvasI.getVersion(), canvasI.getDateCreation(), canvasI.getDateModification(),
              canvasI.getNom(), canvasI.getStatutModification(), canvasI.getAllElements(), canvasI.getGUIDCanvas(),
              canvasI.getUtilisateur(), "INDIVIDUEL", sortColumn);
          listCanvasReturn.add(cNew);
        }
      }

      if (listCanvasReturn.size() != 0) {

        if (sortWay.equals("ASC")) {
          Collections.sort(listCanvasReturn);
        } else {
          Collections.sort(listCanvasReturn, Collections.reverseOrder());
        }

        int nbElement = 25;
        int start = 0;
        int end = page * nbElement;

        result.add(listCanvasReturn.size());
        result.add((int) Math.ceil((double) listCanvasReturn.size() / nbElement));

        if (page == result.get(1)) {
          end = ((page - 1) * nbElement) + (listCanvasReturn.size() - ((page - 1) * nbElement));
        }

        if (page != 1) {
          start = ((page - 1) * nbElement);
        }

        result.add(listCanvasReturn.subList(start, end));
      } else {
        result.add(0);
        result.add(0);
        result.add(new ArrayList<Object>());
      }
    } else {
      result.add(0);
      result.add(0);
      result.add(new ArrayList<Object>());
    }
  }

  public boolean isAdmin(String tokenAdmin) {

    return userService.IsAdmin(tokenAdmin);
  }
}