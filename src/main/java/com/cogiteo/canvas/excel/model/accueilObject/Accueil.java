package com.cogiteo.canvas.excel.model.accueilObject;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "texthome")
public class Accueil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "key")
    private String key;

    @Column(name = "valueFR", length = 10000)
    private String valueFR;

    @Column(name = "valueEN", length = 10000)
    private String valueEN;

    @Column(name = "version")
    private long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValueFR() {
        return valueFR;
    }

    public void setValueFR(String valueFR) {
        this.valueFR = valueFR;
    }

    public String getValueEN() {
        return valueEN;
    }

    public void setValueEN(String valueEN) {
        this.valueEN = valueEN;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getJSON(String langue, List<Accueil> allAccueil) throws JSONException {
        JSONObject jsonFile = new JSONObject();

        JSONObject global = new JSONObject();

        JSONObject canvasSection = new JSONObject();
        JSONObject methodSection = new JSONObject();
        JSONObject whoSection = new JSONObject();
        JSONObject whoSectionCard = new JSONObject();
        JSONObject end = new JSONObject();

        for (Accueil accueil : allAccueil) {
            String[] splitted = accueil.getKey().split("_");
            switch (splitted[0]) {
                case "home":
                    global.put(splitted[1], langue.equals("FR") ? accueil.getValueFR() : accueil.getValueEN());
                    break;
                case "canvas-section":
                    canvasSection.put(splitted[1],
                            langue.equals("FR") ? accueil.getValueFR() : accueil.getValueEN());
                    break;
                case "method-section":
                    methodSection.put(splitted[1],
                            langue.equals("FR") ? accueil.getValueFR() : accueil.getValueEN());
                    break;
                case "who-section":
                    whoSection.put(splitted[1],
                            langue.equals("FR") ? accueil.getValueFR() : accueil.getValueEN());
                    break;
                case "who-section-card":
                    whoSectionCard.put(splitted[1],
                            langue.equals("FR") ? accueil.getValueFR() : accueil.getValueEN());
                    break;
                case "end":
                    end.put(splitted[1],
                            langue.equals("FR") ? accueil.getValueFR() : accueil.getValueEN());
                    break;
                default:
                    break;
            }
        }
        global.put("canvas-section", canvasSection);
        global.put("method-section", methodSection);
        global.put("who-section", whoSection);
        whoSection.put("card", whoSectionCard);
        global.put("end", end);

        jsonFile.put("home", global);

        return jsonFile.toString();
    }
}
