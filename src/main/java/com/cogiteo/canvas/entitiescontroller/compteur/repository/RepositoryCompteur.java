package com.cogiteo.canvas.entitiescontroller.compteur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cogiteo.canvas.entities.Compteur;

import jakarta.transaction.Transactional;

public interface RepositoryCompteur extends JpaRepository<Compteur, Long> {
    
	@Query(value = "select coalesce(max(count), 0) from compteur;", nativeQuery=true)
	public long max();

	@Modifying
    @Transactional
	@Query(value = "DELETE FROM COMPTEUR WHERE count < :max ;", nativeQuery=true)
    public void clear(@Param("max") long max);
}
