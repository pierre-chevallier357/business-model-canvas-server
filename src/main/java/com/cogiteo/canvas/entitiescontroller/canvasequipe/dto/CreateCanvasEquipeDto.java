package com.cogiteo.canvas.entitiescontroller.canvasequipe.dto;

import java.util.ArrayList;
import java.util.List;

public class CreateCanvasEquipeDto {

    private String nom;
    private UtilisateurDto utilisateurDto;
    private List<ElementDto> allElements = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

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

}
