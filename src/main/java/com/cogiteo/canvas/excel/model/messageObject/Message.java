package com.cogiteo.canvas.excel.model.messageObject;

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
@Table(name = "textmessage")
public class Message {

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

    public String getJSON(String langue, List<Message> allMessage) throws JSONException {
        JSONObject jsonFile = new JSONObject();

        JSONObject global = new JSONObject();

        JSONObject pdf = new JSONObject();
        JSONObject canvas = new JSONObject();
        JSONObject deleteConfirm = new JSONObject();
        JSONObject profil = new JSONObject();
        JSONObject mdpoublie = new JSONObject();

        for (Message message : allMessage) {
            String[] splitted = message.getKey().split("_");
            switch (splitted[0]) {
                case "pdf":
                    pdf.put(splitted[1], langue.equals("FR") ? message.getValueFR() : message.getValueEN());
                    break;
                case "canvas":
                    canvas.put(splitted[1],
                            langue.equals("FR") ? message.getValueFR() : message.getValueEN());
                    break;
                case "delete-confirm":
                    deleteConfirm.put(splitted[1],
                            langue.equals("FR") ? message.getValueFR() : message.getValueEN());
                    break;
                case "profil":
                    profil.put(splitted[1],
                            langue.equals("FR") ? message.getValueFR() : message.getValueEN());
                    break;
                case "mdpoublie":
                    mdpoublie.put(splitted[1],
                            langue.equals("FR") ? message.getValueFR() : message.getValueEN());
                    break;
                default:
                    break;
            }
        }
        global.put("pdf", pdf);
        global.put("canvas", canvas);
        canvas.put("delete-confirm", deleteConfirm);
        global.put("profil", profil);
        global.put("mdpoublie", mdpoublie);

        jsonFile.put("popup", global);

        return jsonFile.toString();
    }

}
