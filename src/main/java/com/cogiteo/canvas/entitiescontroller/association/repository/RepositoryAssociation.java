package com.cogiteo.canvas.entitiescontroller.association.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cogiteo.canvas.entities.Association;

public interface RepositoryAssociation extends JpaRepository<Association, Long> {

    @Query(value = "select * from association where association.canvas_equipe = association.canvas_equipe", nativeQuery=true)
    List<Association> findAllByIdCanvasEquipe(@Param("association.canvas_equipe")Long canvasEquipeId);
    
}
