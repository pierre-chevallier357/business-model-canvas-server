package com.cogiteo.canvas.excel.repository.repositoryUtilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.utilisateur.Connexion;

public interface RepositoryUtilisateurConnexion extends JpaRepository<Connexion, Long> {

    @Query(value = "select coalesce(max(version), 0) from textutilisateurconnexion;", nativeQuery = true)
    public long max();

    public List<Connexion> findByVersion(long version);

}