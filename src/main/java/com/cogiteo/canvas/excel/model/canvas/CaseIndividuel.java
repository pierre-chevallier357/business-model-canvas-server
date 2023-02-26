package com.cogiteo.canvas.excel.model.canvas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "textcaseindividuel")
public class CaseIndividuel {
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "libelleCase")
    private String libelleCase;

    @Column(name = "aide", length = 5000)
    private String aide;

    @Column(name = "version")
    private String version;

    public CaseIndividuel() {
    }

    public CaseIndividuel(String numeroCase, String libelleCase, String titre, String aide, String version) {
        this.libelleCase = libelleCase;
        this.aide = aide;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLibelleCase() {
        return libelleCase;
    }

    public void setLibelleCase(String libelleCase) {
        this.libelleCase = libelleCase;
    }

    public String getAide() {
        return aide;
    }

    public void setAide(String aide) {
        this.aide = aide;
    }

    @Override
    public String toString() {
        return "Case [id=" + id + ", libelleCase=" + libelleCase + ", aide=" + aide + ", version=" + version + "]";
    }
    
}
