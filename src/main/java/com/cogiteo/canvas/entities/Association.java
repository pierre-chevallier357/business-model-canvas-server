package com.cogiteo.canvas.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Association")
@IdClass(Association.class)
public class Association {
    
    @Id
    @ManyToOne
    @JoinColumn(name ="canvasEquipe", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CanvasEquipe canvasEquipe;

    @Id
    @ManyToOne
    @JoinColumn(name ="canvasIndividuel", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CanvasIndividuel canvasIndividuel;

   
    @Column(name = "statutIndividuel", nullable = true, length = 200)
    private String statutIndividuel;


    public CanvasIndividuel getCanvasIndividuel() {
        return canvasIndividuel;
    }

    public void setCanvasIndividuel(CanvasIndividuel canvasIndividuel) {
        this.canvasIndividuel = canvasIndividuel;
    }
    
    public String getStatutIndividuel() {
        return statutIndividuel;
    }

    public void setStatutIndividuel(String statutIndividuel) {
        this.statutIndividuel = statutIndividuel;
    }

    public CanvasEquipe getCanvasEquipe() {
        return canvasEquipe;
    }

    public void setCanvasEquipe(CanvasEquipe canvasEquipe) {
        this.canvasEquipe = canvasEquipe;
    }
}

