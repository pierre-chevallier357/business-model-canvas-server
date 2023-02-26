package com.cogiteo.canvas.excel.model.canvas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "textcase")
public class Case {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "numeroCase")
    private String numeroCase;

    @Column(name = "titre")
    private String titre;
    
    @Column(name = "langue")
    private String langue;

    @Column(name = "version")
    private String version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroCase() {
        return numeroCase;
    }

    public void setNumeroCase(String numeroCase) {
        this.numeroCase = numeroCase;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
    
}
