package com.cogiteo.canvas.entitiescontroller.utilisateur.dto;

public class UpdateDto {

    private String prenom;
    private String nom;
    private String mail;
    private String olPassword;
    private String newPassword;
    private String entreprise;
    private boolean estAdmin;
    private String token;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOlPassword() {
        return olPassword;
    }

    public void setOlPassword(String olPassword) {
        this.olPassword = olPassword;
    }

    public boolean getEstAdmin() {
        return estAdmin;
    }

    public void setEstAdmin(boolean estAdmin) {
        this.estAdmin = estAdmin;
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

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
