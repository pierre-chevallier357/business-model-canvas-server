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
@Table(name = "textfaq")
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "questionFR", length = 255)
    private String questionFR;

    @Column(name = "reponseFR", length = 1000)
    private String reponseFR;

    @Column(name = "questionEN", length = 255)
    private String questionEN;

    @Column(name = "reponseEN", length = 1000)
    private String reponseEN;

    @Column(name = "version")
    private long version;

    public FAQ() {
    }

    public FAQ(String questionFR, String reponseFR, String questionEN, String reponseEN, long version) {
        this.questionFR = questionFR;
        this.reponseFR = reponseFR;
        this.questionEN = questionEN;
        this.reponseEN = reponseEN;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public String getQuestionFR() {
        return questionFR;
    }

    public String getReponseFR() {
        return reponseFR;
    }

    public String getQuestionEN() {
        return questionEN;
    }

    public String getReponseEN() {
        return reponseEN;
    }

    public long getVersion() {
        return version;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuestionFR(String questionFR) {
        this.questionFR = questionFR;
    }

    public void setReponseFR(String reponseFR) {
        this.reponseFR = reponseFR;
    }

    public void setQuestionEN(String questionEN) {
        this.questionEN = questionEN;
    }

    public void setReponseEN(String reponseEN) {
        this.reponseEN = reponseEN;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "FAQ [id=" + id + ", questionFR=" + questionFR + ", reponseFR=" + reponseFR + ", questionEN="
                + questionEN + ", reponseEN=" + reponseEN + ", version=" + version + "]";
    }

    public String getJSON(String langue) throws JSONException {
        JSONObject content = new JSONObject();

        if (langue.equals("FR")) {
            content.put("question", this.getQuestionFR());
            content.put("reponse", this.getReponseFR());
        } else {
            content.put("question", this.getQuestionEN());
            content.put("reponse", this.getReponseEN());
        }

        return content.toString();
    }

}
