package com.cogiteo.canvas.excel.repository.repositoryCanvas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.canvas.Case;

public interface RepositoryCanvasCase extends JpaRepository<Case, Long> {

    @Query(value = "select max(version) from textcase", nativeQuery = true)
    String max();

    List<Case> findByVersion(String version);

}
