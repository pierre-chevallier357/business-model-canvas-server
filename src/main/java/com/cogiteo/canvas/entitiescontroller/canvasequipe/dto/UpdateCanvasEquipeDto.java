package com.cogiteo.canvas.entitiescontroller.canvasequipe.dto;

import java.util.ArrayList;
import java.util.List;

public class UpdateCanvasEquipeDto {

    private Long GUIDCanvas;
    private String version;
    private String nom;
    private String codeInvitation;
    private String statutEquipe;
    private UtilisateurDto utilisateurDto;
    private List<UpdateElementDto> allElements = new ArrayList<>();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getGUIDCanvas() {
        return GUIDCanvas;
    }

    public void setGUIDCanvas(Long gUIDCanvas) {
        GUIDCanvas = gUIDCanvas;
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

    public List<UpdateElementDto> getAllElements() {
        return allElements;
    }

    public void setAllElements(List<UpdateElementDto> allElements) {
        this.allElements = allElements;
    }

}
