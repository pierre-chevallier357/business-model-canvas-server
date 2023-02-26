package com.cogiteo.canvas.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Element")
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GUIDElement")
    private Long GUIDElement;

    @Column(name = "caseCanvas", nullable = false)
    private int caseCanvas;

    @Column(name = "contenu", nullable = true, length = 200)
    private String contenu;

    @ManyToOne
    @JoinColumn(name ="canvasEquipe", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CanvasEquipe canvasEquipe;

    @ManyToOne
    @JoinColumn(name ="canvasIndividuel", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private CanvasIndividuel canvasIndividuel;

    public Long getGUIDElement() {
        return GUIDElement;
    }

    public void setGUIDElement(Long gUIDElement) {
        GUIDElement = gUIDElement;
    }

    public int getCaseCanvas() {
        return caseCanvas;
    }

    public void setCaseCanvas(int caseCanvas) {
        this.caseCanvas = caseCanvas;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public CanvasEquipe getCanvasEquipe() {
        return canvasEquipe;
    }

    public void setCanvasEquipe(CanvasEquipe canvasEquipe) {
        this.canvasEquipe = canvasEquipe;
    }

    public CanvasIndividuel getCanvasIndividuel() {
        return canvasIndividuel;
    }

    public void setCanvasIndividuel(CanvasIndividuel canvasIndividuel) {
        this.canvasIndividuel = canvasIndividuel;
    }
}
