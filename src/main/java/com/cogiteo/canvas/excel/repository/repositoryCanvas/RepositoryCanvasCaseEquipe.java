package com.cogiteo.canvas.excel.repository.repositoryCanvas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.canvas.CaseEquipe;

public interface RepositoryCanvasCaseEquipe extends JpaRepository<CaseEquipe, Long> {

    public List<CaseEquipe> findByVersion(String version);

    @Query(value = "select max(version) from textcaseequipe", nativeQuery = true)
    String max();
}
