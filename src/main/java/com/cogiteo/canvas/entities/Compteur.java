package com.cogiteo.canvas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Compteur")
public class Compteur {
    
    @Id
    @Column(name = "count")
    private Long ID;

    public Compteur() {
    }

    public Compteur(Long iD) {
        ID = iD;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long iD) {
        ID = iD;
    }
    
}
