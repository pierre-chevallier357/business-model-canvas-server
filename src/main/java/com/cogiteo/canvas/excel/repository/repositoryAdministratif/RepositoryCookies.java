package com.cogiteo.canvas.excel.repository.repositoryAdministratif;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.administratif.Cookies;

public interface RepositoryCookies extends JpaRepository<Cookies, Long> {

    @Query(value = "select coalesce(max(version), 0) from textadministratifcookies;", nativeQuery = true)
    public long max();

    public List<Cookies> findByVersion(long version);

}
