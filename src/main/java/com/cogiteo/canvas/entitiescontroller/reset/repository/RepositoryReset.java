package com.cogiteo.canvas.entitiescontroller.reset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cogiteo.canvas.entities.Reset;

import jakarta.transaction.Transactional;

public interface RepositoryReset extends JpaRepository<Reset, Long> {

	@Modifying
    @Transactional
    @Query(value="DELETE FROM RESET WHERE expiration < now() OR tentative <= 0", nativeQuery=true)
    public void cleanDatabase();

    public Reset findByMail(String mail);
    
}
