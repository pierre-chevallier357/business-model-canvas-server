package com.cogiteo.canvas.entitiescontroller.canvasindividuel.dto;

import java.util.ArrayList;
import java.util.List;

public class CreateCanvasIndividuelDto {

    private String nom;
    private String statutModification;
    private UtilisateurDto utilisateurDto;
    private List<ElementDto> allElements = new ArrayList<>();


    public UtilisateurDto getUtilisateurDto() {
        return utilisateurDto;
    }

    public void setUtilisateurDto(UtilisateurDto utilisateurDto) {
        this.utilisateurDto = utilisateurDto;
    }

    public List<ElementDto> getAllElements() {
        return allElements;
    }

    public void setAllElements(List<ElementDto> allElements) {
        this.allElements = allElements;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStatutModification() {
        return statutModification;
    }

    public void setStatutModification(String statutModification) {
        this.statutModification = statutModification;
    }

}
