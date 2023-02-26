package com.cogiteo.canvas.excel.model.administratif;

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
@Table(name = "textadministratifpolicy")
public class Policy {
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

    public String getJSON(String string, List<Policy> allPolicy, boolean b) {
        return null;
    }

    public String getJSON(String langue, List<Policy> allPolicy) throws JSONException {
        String title = "policy.title";
        JSONObject jsonFile = new JSONObject();

        JSONObject global = new JSONObject();

        JSONObject article1 = new JSONObject();
        JSONObject article2 = new JSONObject();
        JSONObject article3 = new JSONObject();
        JSONObject article4 = new JSONObject();
        JSONObject article5 = new JSONObject();
        JSONObject article6 = new JSONObject();
        JSONObject article7 = new JSONObject();
        JSONObject article8 = new JSONObject();

        for (Policy policy : allPolicy) {
            String[] splitted = policy.getKey().split("_");
            if (splitted.length == 1) {
                title = langue.equals("FR") ? policy.getValueFR() : policy.getValueEN();
            } else {
                switch (splitted[1]) {
                    case "1":
                        article1.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "2":
                        article2.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "3":
                        article3.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "4":
                        article4.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "5":
                        article5.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "6":
                        article6.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "7":
                        article7.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    case "8":
                        article8.put(splitted[0], langue.equals("FR") ? policy.getValueFR() : policy.getValueEN());
                        break;
                    default:
                        break;
                }
            }
        }
        global.put("title", title);
        global.put("article1", article1);
        global.put("article2", article2);
        global.put("article3", article3);
        global.put("article4", article4);
        global.put("article5", article5);
        global.put("article6", article6);
        global.put("article7", article7);
        global.put("article8", article8);

        jsonFile.put("policy", global);

        return jsonFile.toString();
    }

}
