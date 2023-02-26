package com.cogiteo.canvas.entitiescontroller.canvasequipe.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cogiteo.canvas.entities.CanvasEquipe;

public interface RepositoryCanvasEquipe extends JpaRepository<CanvasEquipe, Long> {

	@Query(value = "select ce.* from canvas_equipe as ce"+
    " inner join utilisateur as u on ce.utilisateur = u.guidutilisateur"+
    " where u.token = :userToken", nativeQuery=true)
    List<CanvasEquipe> AllCanvasEquipeOfUser(@Param("userToken") String userToken);

    @Query(value = "select ce.* from canvas_equipe as ce"+
    " inner join utilisateur as u on ce.utilisateur = u.guidutilisateur"+
    " where upper(ce.nom) like upper(CONCAT('%',:nom, '%'))"+
    " and upper(ce.statut_equipe) like upper(CONCAT('%',:statut, '%'))"+
    " and ce.date_modification between :dateStart and :dateEnd"+
    " and upper(u.nom) like upper(CONCAT('%',:nomUser,'%'))"+
    " and upper(u.prenom) like upper(CONCAT('%',:prenomUser,'%'))"+
    " and upper(u.entreprise) like upper(CONCAT('%',:entreprise,'%'))"+
    " and upper(ce.version) like upper(CONCAT('%',:version,'%'))", nativeQuery=true)
    List<CanvasEquipe> filterEquipe(@Param("nom") String nom, @Param("statut") String statut, @Param("nomUser") String nomUser, @Param("prenomUser") String prenomUser, @Param("version") String version, @Param("entreprise") String entreprise, @Param("dateStart") Calendar dateStart, @Param("dateEnd") Calendar dateEnd);
    
    CanvasEquipe findByGUIDCanvas(Long guidCanvas);

    @Query(value = "select * from canvas_equipe where code_invitation = :codeInvitation", nativeQuery=true)
    CanvasEquipe findByCodeInvitation(@Param("codeInvitation") String codeInvitation);

    @Query(value = "select max(version) from textcaseequipe", nativeQuery=true)
    String max();

}