package com.cogiteo.canvas.excel.repository.repositoryUtilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.utilisateur.Inscription;

public interface RepositoryUtilisateurInscription extends JpaRepository<Inscription, Long> {

    @Query(value = "select coalesce(max(version), 0) from textutilisateurinscription;", nativeQuery = true)
    public long max();

    public List<Inscription> findByVersion(long version);
}
