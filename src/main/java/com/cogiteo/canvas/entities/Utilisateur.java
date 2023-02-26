package com.cogiteo.canvas.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name = "Utilisateur")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Utilisateur implements Comparable<Utilisateur> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GUIDUtilisateur")
    private Long GUIDUtilisateur;

    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Column(name = "mail", nullable = false, unique = true, length = 75)
    private String mail;

    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    private String password;

    @Column(name = "token", unique = true, nullable = false, length = 50)
    private String token;

    @Column(name = "entreprise", nullable = false, length = 50)
    private String entreprise;

    @Column(name = "estAdmin", nullable = false)
    private boolean estAdmin;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<CanvasIndividuel> allCanvasIndividuels = new ArrayList<>();

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<CanvasEquipe> allCanvasEquipe = new ArrayList<>();

    @Transient
    @JsonIgnore
    private String columnToCompare;
 
    public Long getGUIDUtilisateur() {
        return GUIDUtilisateur;
    }

    public void setGUIDUtilisateur(Long gUIDUtilisateur) {
        GUIDUtilisateur = gUIDUtilisateur;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public boolean isEstAdmin() {
        return estAdmin;
    }

    public void setEstAdmin(boolean estAdmin) {
        this.estAdmin = estAdmin;
    }

    public List<CanvasIndividuel> getAllCanvasIndividuels() {
        return allCanvasIndividuels;
    }

    public void setAllCanvasIndividuels(List<CanvasIndividuel> allCanvasIndividuels) {
        this.allCanvasIndividuels = allCanvasIndividuels;
    }
    
    public List<CanvasEquipe> getAllCanvasEquipe() {
        return allCanvasEquipe;
    }

    public void setAllCanvasEquipe(List<CanvasEquipe> allCanvasEquipe) {
        this.allCanvasEquipe = allCanvasEquipe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getColumnToCompare() {
        return columnToCompare;
    }

    public void setColumnToCompare(String columnToCompare) {
        this.columnToCompare = columnToCompare;
    }

    @Override
    public int compareTo(Utilisateur o) {
        switch(getColumnToCompare()) {
            case "guidutilisateur":
                return this.GUIDUtilisateur.compareTo(o.GUIDUtilisateur);
            case "nom":
                return this.nom.toLowerCase().compareTo(o.getNom().toLowerCase());
            case "prenom":
                return this.prenom.toLowerCase().compareTo(o.getPrenom().toLowerCase());
            case "entreprise":
                return this.entreprise.toLowerCase().compareTo(o.getEntreprise().toLowerCase());
            case "mail":
                return this.mail.toLowerCase().compareTo(o.getMail().toLowerCase());
            case "admin":
                return Boolean.compare(this.estAdmin, o.isEstAdmin());
            default:
                return this.GUIDUtilisateur.compareTo(o.GUIDUtilisateur);
        }
    }

    
}