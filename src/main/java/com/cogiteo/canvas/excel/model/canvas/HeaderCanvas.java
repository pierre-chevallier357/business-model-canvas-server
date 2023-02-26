package com.cogiteo.canvas.excel.model.canvas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "textcaseheader")
public class HeaderCanvas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "langue")
    private String langue;

    @Column(name = "headerTitle")
    private String headerTitle;

    @Column(name = "canvasTitle")
    private String canvasTitle;

    @Column(name = "help")
    private String help;

    @Column(name = "date")
    private String date;

    @Column(name = "aideIndividuel", length = 5000)
    private String aideIndividuel;

    @Column(name = "aideEquipe", length = 5000)
    private String aideEquipe;

    @Column(name = "version")
    private String version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getCanvasTitle() {
        return canvasTitle;
    }

    public void setCanvasTitle(String canvasTitle) {
        this.canvasTitle = canvasTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAideIndividuel() {
        return aideIndividuel;
    }

    public void setAideIndividuel(String aideIndividuel) {
        this.aideIndividuel = aideIndividuel;
    }

    public String getAideEquipe() {
        return aideEquipe;
    }

    public void setAideEquipe(String aideEquipe) {
        this.aideEquipe = aideEquipe;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

}
