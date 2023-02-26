package com.cogiteo.canvas.entitiescontroller.canvas.object;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.cogiteo.canvas.entities.Association;
import com.cogiteo.canvas.entities.Element;
import com.cogiteo.canvas.entities.Utilisateur;

public class Canvas implements Comparable<Canvas> {
    
    private String version;

    private LocalDate dateCreation;
    
    private LocalDate dateModification;

    private String nom;

    private String statut;
    
    private List<Element> allElements = new ArrayList<>();

    private Long GUIDCanvas;

    private Utilisateur utilisateur;

    private List<Association> allAssociations = new ArrayList<>();

    private String codeInvitation;

    private String type;

    private String columnToCompare;

    public Canvas(String version, LocalDate dateCreation, LocalDate dateModification, String nom, String statut,
            List<Element> allElements, Long gUIDCanvas, Utilisateur utilisateur, List<Association> allAssociations,
            String codeInvitation, String type, String columnToCompare) {
        this.version = version;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.nom = nom;
        this.statut = statut;
        this.allElements = allElements;
        GUIDCanvas = gUIDCanvas;
        this.utilisateur = utilisateur;
        this.allAssociations = allAssociations;
        this.codeInvitation = codeInvitation;
        this.type = type;
        this.columnToCompare = columnToCompare;
    }

    public Canvas(String version, LocalDate dateCreation, LocalDate dateModification, String nom, String statut,
            List<Element> allElements, Long gUIDCanvas, Utilisateur utilisateur, String type, String columnToCompare) {
        this.version = version;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.nom = nom;
        this.statut = statut;
        this.allElements = allElements;
        GUIDCanvas = gUIDCanvas;
        this.utilisateur = utilisateur;
        this.type = type;
        this.columnToCompare = columnToCompare;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<Element> getAllElements() {
        return allElements;
    }

    public void setAllElements(List<Element> allElements) {
        this.allElements = allElements;
    }

    public Long getGUIDCanvas() {
        return GUIDCanvas;
    }

    public void setGUIDCanvas(Long gUIDCanvas) {
        GUIDCanvas = gUIDCanvas;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Association> getAllAssociations() {
        return allAssociations;
    }

    public void setAllAssociations(List<Association> allAssociations) {
        this.allAssociations = allAssociations;
    }

    public String getCodeInvitation() {
        return codeInvitation;
    }

    public void setCodeInvitation(String codeInvitation) {
        this.codeInvitation = codeInvitation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColumnToCompare() {
        return columnToCompare;
    }

    public void setColumnToCompare(String columnToCompare) {
        this.columnToCompare = columnToCompare;
    }

    @Override
    public int compareTo(Canvas c) {
        switch(getColumnToCompare()) {
            case "guidcanvas":
                return this.GUIDCanvas.compareTo(c.GUIDCanvas);
            case "nom":
                return this.nom.toLowerCase().compareTo(c.getNom().toLowerCase());
            case "dateCreation":
                return this.dateCreation.compareTo(c.getDateCreation());
            case "dateModification":
                return this.dateModification.compareTo(c.getDateModification());
            case "version":
                return this.version.compareTo(c.version);
            case "statut":
                return this.statut.toLowerCase().compareTo(c.getStatut().toLowerCase());
            case "nomUser":
                return this.utilisateur.getNom().toLowerCase().compareTo(c.getUtilisateur().getNom().toLowerCase());
            case "prenomUser":
                return this.utilisateur.getPrenom().toLowerCase().compareTo(c.getUtilisateur().getPrenom().toLowerCase());
            case "entreprise":
                return this.utilisateur.getEntreprise().toLowerCase().compareTo(c.getUtilisateur().getEntreprise().toLowerCase());
            case "type":
                return this.getType().toLowerCase().compareTo(c.getType().toLowerCase());
            default:
                return this.GUIDCanvas.compareTo(c.GUIDCanvas);
        }
    }

}
