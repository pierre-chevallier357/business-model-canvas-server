package com.cogiteo.canvas.excel.repository.repositoryAdministratif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.administratif.Mentions;

public interface RepositoryMentions extends JpaRepository<Mentions, Long> {

    @Query(value = "select coalesce(max(version), 0) from textadministratifmentions;", nativeQuery = true)
    public long max();

    public List<Mentions> findByVersion(long version);

}
