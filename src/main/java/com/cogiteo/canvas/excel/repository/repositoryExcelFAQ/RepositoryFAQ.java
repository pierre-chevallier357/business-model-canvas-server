package com.cogiteo.canvas.excel.repository.repositoryExcelFAQ;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.FAQObject.FAQ;

public interface RepositoryFAQ extends JpaRepository<FAQ, Long> {

	@Query(value = "select coalesce(max(version), 0) from textfaq;", nativeQuery = true)
	public long max();

	public List<FAQ> findByVersion(long version);
}
