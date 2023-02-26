package com.cogiteo.canvas.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "CanvasEquipe")
public class CanvasEquipe implements Comparable<CanvasEquipe> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GUIDCanvas")
    private Long GUIDCanvas;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "dateCreation", nullable = false, length = 50)
    private LocalDate dateCreation = LocalDate.now();
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
 
    @Column(name = "codeInvitation", nullable = false, unique = true, length = 10)
    private String codeInvitation;

    @Column(name = "statutEquipe", nullable = false, length = 50)
    private String statutEquipe;

    @Column(name = "dateModification", nullable = false, length = 50)
    private LocalDate dateModification = LocalDate.now();

    @ManyToOne
    @JoinColumn(name ="utilisateur", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"allCanvasEquipe","allCanvasIndividuels"})
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "canvasEquipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Element> allElements = new ArrayList<>();

    @OneToMany(mappedBy = "canvasEquipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Association> allAssociations = new ArrayList<>();

    @Transient
    @JsonIgnore
    private String columnToCompare;

    public Long getGUIDCanvas() {
        return GUIDCanvas;
    }

    public void setGUIDCanvas(Long gUIDCanvas) {
        GUIDCanvas = gUIDCanvas;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
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

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Element> getAllElements() {
        return allElements;
    }

    public void setAllElements(List<Element> allElements) {
        this.allElements = allElements;
    }

    public List<Association> getAllAssociations() {
        return allAssociations;
    }

    public void setAllAssociations(List<Association> allAssociations) {
        this.allAssociations = allAssociations;
    }

    public String getColumnToCompare() {
        return columnToCompare;
    }

    public void setColumnToCompare(String columnToCompare) {
        this.columnToCompare = columnToCompare;
    }
    public LocalDate getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDate dateModification) {
        this.dateModification = dateModification;
    }
    
    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    @Override
    public int compareTo(CanvasEquipe ce) {
        switch(getColumnToCompare()) {
            case "guidcanvas":
                return this.GUIDCanvas.compareTo(ce.GUIDCanvas);
            case "nom":
                return this.nom.toLowerCase().compareTo(ce.getNom().toLowerCase());
            case "dateCreation":
                return this.dateCreation.compareTo(ce.getDateCreation());
            case "dateModification":
                return this.dateModification.compareTo(ce.getDateModification());
            case "version":
                return this.version.compareTo(ce.version);
            case "statut_equipe":
                return this.statutEquipe.toLowerCase().compareTo(ce.getStatutEquipe().toLowerCase());
            case "nomUser":
                return this.utilisateur.getNom().toLowerCase().compareTo(ce.getUtilisateur().getNom().toLowerCase());
            case "prenomUser":
                return this.utilisateur.getPrenom().toLowerCase().compareTo(ce.getUtilisateur().getPrenom().toLowerCase());
            case "entreprise":
                return this.utilisateur.getEntreprise().toLowerCase().compareTo(ce.getUtilisateur().getEntreprise().toLowerCase());
            default:
                return this.GUIDCanvas.compareTo(ce.GUIDCanvas);
        }
    }
}
