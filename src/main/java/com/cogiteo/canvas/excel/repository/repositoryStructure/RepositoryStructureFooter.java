package com.cogiteo.canvas.excel.repository.repositoryStructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.structure.Footer;

public interface RepositoryStructureFooter extends JpaRepository<Footer, Long> {

    @Query(value = "select coalesce(max(version), 0) from textstructurefooter;", nativeQuery = true)
    public long max();

    public List<Footer> findByVersion(long version);

}
