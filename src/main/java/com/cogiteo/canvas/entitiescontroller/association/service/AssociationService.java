package com.cogiteo.canvas.entitiescontroller.association.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.Association;
import com.cogiteo.canvas.entitiescontroller.association.repository.RepositoryAssociation;

@Service
public class AssociationService {

  @Autowired
  RepositoryAssociation repository;

public Association CreateAssociationCanvasEquipeIndividuel(Association association) {
    return repository.save(association);
}

public List<Association> getAllCanvas() {
    return repository.findAll();
}


public List<Association> canvasIndividuelOfEquipe(Long canvasEquipeId) {
  return repository.findAllByIdCanvasEquipe(canvasEquipeId);
}

    
}
