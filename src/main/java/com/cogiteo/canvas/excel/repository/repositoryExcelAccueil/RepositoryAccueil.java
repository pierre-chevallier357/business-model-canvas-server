package com.cogiteo.canvas.excel.repository.repositoryExcelAccueil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.accueilObject.Accueil;

public interface RepositoryAccueil extends JpaRepository<Accueil, Long> {

    @Query(value = "select coalesce(max(version), 0) from texthome;", nativeQuery = true)
    public long max();

    public List<Accueil> findByVersion(long version);

}
