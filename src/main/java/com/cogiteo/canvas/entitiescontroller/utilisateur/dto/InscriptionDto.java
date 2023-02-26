package com.cogiteo.canvas.entitiescontroller.utilisateur.dto;

public class InscriptionDto {
    
    private String prenom;
    private String nom;
    private String mail;
    private String password;
    private String entreprise;
    private boolean estAdmin;
  
   
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

    
}
