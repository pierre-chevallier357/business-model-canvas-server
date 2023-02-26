package com.cogiteo.canvas.excel.repository.repositoryUtilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.utilisateur.MdpOublie;

public interface RepositoryUtilisateurMdpOublie extends JpaRepository<MdpOublie, Long> {

    @Query(value = "select coalesce(max(version), 0) from textutilisateurmdpoublie;", nativeQuery = true)
    public long max();

    public List<MdpOublie> findByVersion(long version);
}
