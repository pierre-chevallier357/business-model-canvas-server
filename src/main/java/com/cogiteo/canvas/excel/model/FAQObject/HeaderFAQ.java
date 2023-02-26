package com.cogiteo.canvas.excel.model.FAQObject;

import org.json.JSONException;
import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "textheaderfaq")
public class HeaderFAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "titreEN")
    private String titreEN;

    @Column(name = "titreFR")
    private String titreFR;

    @Column(name = "version")
    private long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitreEN() {
        return titreEN;
    }

    public void setTitreEN(String titreEN) {
        this.titreEN = titreEN;
    }

    public String getTitreFR() {
        return titreFR;
    }

    public void setTitreFR(String titreFR) {
        this.titreFR = titreFR;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getFileName(String langue) {
        return langue.equals("FR") ? "faqPage-fr.json" : "faqPage-en.json";
    }

    public String getJSON(String langue) throws JSONException {
        JSONObject jsonFile = new JSONObject();
        JSONObject content = new JSONObject();

        if (langue.equals("FR")) {
            content.put("title", this.getTitreFR());
        } else {
            content.put("title", this.getTitreEN());
        }

        jsonFile.put("faqPage", content);
        return jsonFile.toString();
    }

}
