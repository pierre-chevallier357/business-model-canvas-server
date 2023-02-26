package com.cogiteo.canvas.entitiescontroller.canvasindividuel.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cogiteo.canvas.entities.CanvasIndividuel;
import com.cogiteo.canvas.entities.Element;

public interface RepositoryCanvasIndividuel extends JpaRepository<CanvasIndividuel, Long> {

	@Query(value = "select ci.* from canvas_individuel as ci"+
    " inner join utilisateur as u on ci.utilisateur = u.guidutilisateur"+
    " where u.token = :userToken", nativeQuery=true)
    List<CanvasIndividuel> AllCanvasIndividuelOfUser(@Param("userToken") String userToken);
    
    @Query(value = "select ci.* from canvas_individuel as ci"+
                   " inner join utilisateur as u on ci.utilisateur = u.guidutilisateur"+
                   " where upper(ci.nom) like upper(CONCAT('%',:nom, '%'))"+
                   " and upper(ci.statut_modification) like upper(CONCAT('%',:statut, '%'))"+
                   " and ci.date_modification between :dateStart and :dateEnd"+
                   " and upper(u.nom) like upper(CONCAT('%',:nomUser,'%'))"+
                   " and upper(u.prenom) like upper(CONCAT('%',:prenomUser,'%'))"+
                   " and upper(u.entreprise) like upper(CONCAT('%',:entreprise,'%'))"+
                   " and upper(ci.version) like upper(CONCAT('%',:version,'%'))", nativeQuery=true)
    List<CanvasIndividuel> filterIndividuel(@Param("nom") String nom, @Param("statut") String statut, @Param("nomUser") String nomUser, @Param("prenomUser") String prenomUser, @Param("version") String version, @Param("entreprise") String entreprise, @Param("dateStart") Calendar dateStart, @Param("dateEnd") Calendar dateEnd);

    @Query(value = "select max(version) from textcaseindividuel", nativeQuery=true)
    String max();

    //@Query(value = "select * from canvas_individuel where guidcanvas = guidCanvas ", nativeQuery=true)
    CanvasIndividuel findByGUIDCanvas(Long guidCanvas);



}