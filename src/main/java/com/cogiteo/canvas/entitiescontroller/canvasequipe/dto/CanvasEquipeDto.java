package com.cogiteo.canvas.entitiescontroller.canvasequipe.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CanvasEquipeDto {

    private int version;
    private LocalDate dateCreation;
    private LocalDate dateModification;
    private String nom;
    private String codeInvitation;
    private String statutEquipe;
    private UtilisateurDto utilisateurDto;
    private List<ElementDto> allElements = new ArrayList<>();

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeInvitation() {
        return codeInvitation;
    }

    public void setCodeInvitation(String codeInvitation) {
        this.codeInvitation = codeInvitation;
    }

    public String getStatutEquipe() {
        return statutEquipe;
    }

    public void setStatutEquipe(String statutEquipe) {
        this.statutEquipe = statutEquipe;
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
