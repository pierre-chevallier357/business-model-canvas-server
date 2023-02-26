package com.cogiteo.canvas.entitiescontroller.element.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cogiteo.canvas.entities.Element;

public interface RepositoryElement extends JpaRepository<Element, Long> {
    
}
