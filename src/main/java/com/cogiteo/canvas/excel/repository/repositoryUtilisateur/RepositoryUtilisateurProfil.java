package com.cogiteo.canvas.excel.repository.repositoryUtilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.utilisateur.Profil;

public interface RepositoryUtilisateurProfil extends JpaRepository<Profil, Long> {

    @Query(value = "select coalesce(max(version), 0) from textutilisateurmdpoublie;", nativeQuery = true)
    public long max();

    public List<Profil> findByVersion(long version);
}
