package com.cogiteo.canvas.entitiescontroller.utilisateur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cogiteo.canvas.entities.Utilisateur;

import jakarta.transaction.Transactional;

public interface RepositoryUtilisateur extends JpaRepository<Utilisateur, Long> {

    public Utilisateur findByMail(String mail);

    @Query(value = "select count(*) from canvas_equipe where utilisateur = :user", nativeQuery=true)
    public int canDelete(@Param("user") long userId);

    public Utilisateur findByGUIDUtilisateur(Long guidUtilisateur);

    @Transactional
    public long deleteByGUIDUtilisateur(Long guidUtilisateur);

    @Query(value = "select count(*) from utilisateur where token = :token", nativeQuery=true)
    public int IsNotUniqueToken(@Param("token") String token);
    
    @Query(value = "select * from utilisateur where upper(entreprise) like upper(CONCAT('%',:entreprise, '%')) and upper(nom) like upper(CONCAT('%',:nom, '%')) and upper(prenom) like upper(CONCAT('%',:prenom, '%')) and upper(mail) like upper(CONCAT('%',:mail, '%'))", nativeQuery=true)
    public List<Utilisateur> filterUtilisateur(@Param("nom") String nom, @Param("prenom") String prenom, @Param("mail") String mail, @Param("entreprise") String entreprise);

    @Query(value = "select count(*) from utilisateur where est_admin = true", nativeQuery=true)
    public int countAdmin();

    public Utilisateur findByToken(String token);

    @Query(value = "select est_admin from utilisateur where token =:token", nativeQuery=true)
    public Boolean IsAdmin(@Param("token") String tokenAdmin);


}
