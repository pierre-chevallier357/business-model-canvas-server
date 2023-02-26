package com.cogiteo.canvas.entitiescontroller.element.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cogiteo.canvas.entities.Element;
import com.cogiteo.canvas.entitiescontroller.element.repository.RepositoryElement;

@Service
public class ElementService {

  @Autowired
  RepositoryElement repository;

  public Element createElement( Element element){
    return repository.save(element);
  }

  public List<Element> createElements( List<Element> elements){
    return repository.saveAll(elements);
  }
    
}

	