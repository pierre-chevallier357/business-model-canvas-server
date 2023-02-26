package com.cogiteo.canvas.excel.repository.repositoryAdministratif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.administratif.Policy;

public interface RepositoryPolicy extends JpaRepository<Policy, Long> {

    @Query(value = "select coalesce(max(version), 0) from textadministratifpolicy;", nativeQuery = true)
    public long max();

    public List<Policy> findByVersion(long version);

}
