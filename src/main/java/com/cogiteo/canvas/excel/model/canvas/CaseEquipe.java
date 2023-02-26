package com.cogiteo.canvas.excel.model.canvas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "textcaseequipe")
public class CaseEquipe {

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
}
