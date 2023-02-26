package com.cogiteo.canvas.excel.repository.repositoryExcelMessage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.excel.model.messageObject.Message;

public interface RepositoryMessage extends JpaRepository<Message, Long> {

    @Query(value = "select coalesce(max(version), 0) from textmessage;", nativeQuery = true)
    public long max();

    public List<Message> findByVersion(long version);

}
