package com.cogiteo.canvas.excel.repository.repositoryCanvas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cogiteo.canvas.excel.model.canvas.HeaderCanvas;

public interface RepositoryCanvasHeader extends JpaRepository<HeaderCanvas, Long> {

    @Query(value = "SELECT COUNT(*) FROM textcaseheader WHERE version = :version", nativeQuery = true)
    int existVersion(@Param("version") String version);

    @Query(value = "select max(version) from textcaseheader t", nativeQuery = true)
    public String max();

    List<HeaderCanvas> findByVersion(String version);

}
