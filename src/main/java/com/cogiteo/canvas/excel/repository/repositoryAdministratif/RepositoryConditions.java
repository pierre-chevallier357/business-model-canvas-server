package com.cogiteo.canvas.excel.repository.repositoryAdministratif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.administratif.Conditions;

public interface RepositoryConditions extends JpaRepository<Conditions, Long> {

    @Query(value = "select coalesce(max(version), 0) from textadministratifconditions;", nativeQuery = true)
    public long max();

    public List<Conditions> findByVersion(long version);

}
