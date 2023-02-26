package com.cogiteo.canvas.excel.repository.repositoryExcelFAQ;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.FAQObject.HeaderFAQ;

public interface RepositoryHeaderFAQ extends JpaRepository<HeaderFAQ, Long> {

    @Query(value = "select coalesce(max(version), 0) from textheaderfaq;", nativeQuery = true)
    public long max();

    public List<HeaderFAQ> findByVersion(long version);
}
