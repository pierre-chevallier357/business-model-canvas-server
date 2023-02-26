package com.cogiteo.canvas.entitiescontroller.canvasindividuel.dto;

import java.util.ArrayList;
 import java.util.List;

public class UpdateCanvasIndividuelDto {

    private Long GUIDCanvas;
    private String version;
    private String nom;
    private String statutModification;
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
    public String getStatutModification() {
        return statutModification;
    }
    public void setStatutModification(String statutModification) {
        this.statutModification = statutModification;
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



