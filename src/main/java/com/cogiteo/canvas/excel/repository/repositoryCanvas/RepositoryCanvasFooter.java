package com.cogiteo.canvas.excel.repository.repositoryCanvas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.canvas.FooterCanvas;

public interface RepositoryCanvasFooter extends JpaRepository<FooterCanvas, Long> {

    @Query(value = "select max(version) from textcasefooter", nativeQuery = true)
    String max();

    List<FooterCanvas> findByVersion(String version);
}