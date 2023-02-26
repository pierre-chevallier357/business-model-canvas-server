package com.cogiteo.canvas.excel.repository.repositoryCanvas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.canvas.CaseIndividuel;

public interface RepositoryCanvasCaseIndividuel extends JpaRepository<CaseIndividuel, Long> {

    public List<CaseIndividuel> findByVersion(String version);

    @Query(value = "select max(version) from textcaseindividuel", nativeQuery = true)
    String max();
}
