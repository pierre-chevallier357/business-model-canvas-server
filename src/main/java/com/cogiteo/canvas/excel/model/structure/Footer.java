package com.cogiteo.canvas.excel.model.structure;

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
@Table(name = "textstructurefooter")
public class Footer {
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

    public String getJSON(String langue, List<Footer> allFooter) throws JSONException {
        JSONObject jsonFile = new JSONObject();

        JSONObject global = new JSONObject();

        for (Footer footer : allFooter) {
            global.put(footer.getKey(), langue.equals("FR") ? footer.getValueFR() : footer.getValueEN());
        }

        jsonFile.put("footer", global);

        return jsonFile.toString();
    }

}
