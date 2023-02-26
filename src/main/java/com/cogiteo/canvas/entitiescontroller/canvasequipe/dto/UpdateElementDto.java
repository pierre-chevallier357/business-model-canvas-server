package com.cogiteo.canvas.entitiescontroller.canvasequipe.dto;

public class UpdateElementDto {
    private Long GUIDElement;
    private int caseCanvas;
    private String contenu;

    public Long getGUIDElement() {
        return GUIDElement;
    }

    public void setGUIDElement(Long gUIDElement) {
        GUIDElement = gUIDElement;
    }

    public int getCaseCanvas() {
        return caseCanvas;
    }

    public void setCaseCanvas(int caseCanvas) {
        this.caseCanvas = caseCanvas;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

}
