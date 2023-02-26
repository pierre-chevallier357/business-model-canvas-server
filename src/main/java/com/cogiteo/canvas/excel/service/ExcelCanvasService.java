package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelCanvasHelper;
import com.cogiteo.canvas.excel.model.canvas.HeaderCanvas;
import com.cogiteo.canvas.excel.repository.repositoryCanvas.RepositoryCanvasCase;
import com.cogiteo.canvas.excel.repository.repositoryCanvas.RepositoryCanvasCaseEquipe;
import com.cogiteo.canvas.excel.repository.repositoryCanvas.RepositoryCanvasCaseIndividuel;
import com.cogiteo.canvas.excel.repository.repositoryCanvas.RepositoryCanvasFooter;
import com.cogiteo.canvas.excel.repository.repositoryCanvas.RepositoryCanvasHeader;
import com.cogiteo.canvas.excel.model.canvas.Case;
import com.cogiteo.canvas.excel.model.canvas.CaseEquipe;
import com.cogiteo.canvas.excel.model.canvas.CaseIndividuel;
import com.cogiteo.canvas.excel.model.canvas.FooterCanvas;

@Service
public class ExcelCanvasService extends ExcelService {
    @Autowired
    RepositoryCanvasCaseIndividuel repositoryCaseIndividuel;

    @Autowired
    RepositoryCanvasCaseEquipe repositoryCaseEquipe;

    @Autowired
    RepositoryCanvasHeader repositoryHeader;

    @Autowired
    RepositoryCanvasFooter repositoryFooter;

    @Autowired
    RepositoryCanvasCase repositoryCase;

    // PARTIE CANVAS
    @SuppressWarnings("fallthrough")
    public boolean saveCase(MultipartFile file) throws JSONException {
        int sheet = 0;
        String version = "";
        List<HeaderCanvas> saveHeader = new ArrayList<HeaderCanvas>();
        List<Case> saveCase = new ArrayList<Case>();
        List<CaseIndividuel> saveIndividuel = new ArrayList<CaseIndividuel>();
        List<CaseEquipe> saveEquipe = new ArrayList<CaseEquipe>();

        try {
            version = ExcelCanvasHelper.extractVersion(file.getInputStream());

            // if version existe return error
            if (repositoryHeader.existVersion(version) > 0) {
                deleteVersion(version);
            }

            ///////// SHEET 1 HEADER
            List<HeaderCanvas> caseHeaderItems = ExcelCanvasHelper.excelToHeader(file.getInputStream(), version);
            saveHeader = repositoryHeader.saveAll(caseHeaderItems);

            JSONObject jsonHeaderFR = new JSONObject();
            JSONObject jsonHeaderEN = new JSONObject();

            for (HeaderCanvas header : caseHeaderItems) {
                if (header.getLangue().equals("FR")) {
                    JSONObject global = new JSONObject();
                    global.put("header-title", header.getHeaderTitle());
                    global.put("canvas-title", header.getCanvasTitle());
                    global.put("help", header.getHelp());
                    global.put("date", header.getDate());
                    global.put("aide-globale-indiv", header.getAideIndividuel());
                    global.put("aide-globale-equipe", header.getAideEquipe());

                    jsonHeaderFR.put("canvas-header", global);
                } else {
                    JSONObject global = new JSONObject();
                    global.put("header-title", header.getHeaderTitle());
                    global.put("canvas-title", header.getCanvasTitle());
                    global.put("help", header.getHelp());
                    global.put("date", header.getDate());
                    global.put("aide-globale-indiv", header.getAideIndividuel());
                    global.put("aide-globale-equipe", header.getAideEquipe());

                    jsonHeaderEN.put("canvas-header", global);
                }
            }

            uploadFile("www/assets/i18n/canvas/", "canvas-header-fr-" + version.replace('.', '-') + ".json",
                    jsonHeaderFR.toString(), false);
            uploadFile("www/assets/i18n/canvas/", "canvas-header-en-" + version.replace('.', '-') + ".json",
                    jsonHeaderEN.toString(), false);

            sheet++;

            ///////// SHEET 2 FOOTER
            List<FooterCanvas> caseFooterItems = ExcelCanvasHelper.excelToFooter(file.getInputStream(), version);
            repositoryFooter.saveAll(caseFooterItems);

            JSONObject jsonFooterFR = new JSONObject();
            JSONObject jsonFooterEN = new JSONObject();

            for (FooterCanvas footer : caseFooterItems) {
                if (footer.getLangue().equals("FR")) {
                    JSONObject global = new JSONObject();
                    global.put("description", footer.getDescription());

                    jsonFooterFR.put("canvas-footer", global);
                } else {
                    JSONObject global = new JSONObject();
                    global.put("description", footer.getDescription());

                    jsonFooterEN.put("canvas-footer", global);
                }
            }

            uploadFile("www/assets/i18n/canvas/", "canvas-footer-fr" + ".json", jsonFooterFR.toString(), false);
            uploadFile("www/assets/i18n/canvas/", "canvas-footer-en" + ".json", jsonFooterEN.toString(), false);

            sheet++;

            ///////// SHEET 3 CASE

            List<Case> itemsCase = ExcelCanvasHelper.excelToCase(file.getInputStream(), version);
            saveCase = repositoryCase.saveAll(itemsCase);

            JSONArray arrayCaseFR = new JSONArray();
            JSONArray arrayCaseEN = new JSONArray();

            for (Case caseItem : itemsCase) {
                if (caseItem.getLangue().equals("FR")) {
                    JSONObject global = new JSONObject();
                    global.put("number", caseItem.getNumeroCase());
                    global.put("title", caseItem.getTitre());

                    arrayCaseFR.put(global);
                } else {
                    JSONObject global = new JSONObject();
                    global.put("number", caseItem.getNumeroCase());
                    global.put("title", caseItem.getTitre());

                    arrayCaseEN.put(global);
                }
            }

            JSONObject jsonCaseFR = new JSONObject();
            jsonCaseFR.put("boxListFr", arrayCaseFR);
            JSONObject jsonCaseEN = new JSONObject();
            jsonCaseEN.put("boxListEn", arrayCaseEN);

            uploadFile("www/assets/i18n/canvas/", "box-list-fr-" + version.replace('.', '-') + ".json",
                    jsonCaseFR.toString(), false);
            uploadFile("www/assets/i18n/canvas/", "box-list-en-" + version.replace('.', '-') + ".json",
                    jsonCaseEN.toString(), false);

            sheet++;

            ///////// SHEET 4 INDIVIDUEL

            List<CaseIndividuel> caseIndividuelItems = ExcelCanvasHelper.excelToCaseIndividuel(file.getInputStream(),
                    version);
            saveIndividuel = repositoryCaseIndividuel.saveAll(caseIndividuelItems);

            List<CaseIndividuel> itemsCaseIndividuel = getVersionCaseIndividuel(version);

            JSONObject jsonIndivFR = new JSONObject();
            JSONObject jsonIndivEN = new JSONObject();

            for (CaseIndividuel caseIndividuel : itemsCaseIndividuel) {
                String[] splittedLibelle = caseIndividuel.getLibelleCase().split("-");
                if (splittedLibelle[splittedLibelle.length - 1].equals("fr")) {
                    jsonIndivFR.put(caseIndividuel.getLibelleCase(), caseIndividuel.getAide());
                } else {
                    jsonIndivEN.put(caseIndividuel.getLibelleCase(), caseIndividuel.getAide());
                }
            }

            uploadFile("www/assets/i18n/canvas/", "canvas-indiv-fr-" + version.replace('.', '-') + ".json",
                    jsonIndivFR.toString(), false);
            uploadFile("www/assets/i18n/canvas/", "canvas-indiv-en-" + version.replace('.', '-') + ".json",
                    jsonIndivEN.toString(), false);

            sheet++;

            ///////// SHEET 5 EQUIPE

            List<CaseEquipe> caseEquipeItems = ExcelCanvasHelper.excelToCaseEquipe(file.getInputStream(), version);
            saveEquipe = repositoryCaseEquipe.saveAll(caseEquipeItems);

            List<CaseEquipe> itemsCaseEquipe = getVersionCaseEquipe(version);

            JSONObject jsonEquipeFR = new JSONObject();
            JSONObject jsonEquipeEN = new JSONObject();

            for (CaseEquipe caseEquipe : itemsCaseEquipe) {
                String[] splittedLibelle = caseEquipe.getLibelleCase().split("-");
                if (splittedLibelle[splittedLibelle.length - 1].equals("fr")) {
                    jsonEquipeFR.put(caseEquipe.getLibelleCase(), caseEquipe.getAide());
                } else {
                    jsonEquipeEN.put(caseEquipe.getLibelleCase(), caseEquipe.getAide());
                }
            }

            uploadFile("www/assets/i18n/canvas/", "canvas-equipe-fr-" + version.replace('.', '-') + ".json",
                    jsonEquipeFR.toString(), false);
            uploadFile("www/assets/i18n/canvas/", "canvas-equipe-en-" + version.replace('.', '-') + ".json",
                    jsonEquipeEN.toString(), false);

            sheet++;

            return true;
        } catch (IOException e) {

            switch (sheet) {
                case 4:
                    repositoryCaseEquipe.deleteAll(saveEquipe);
                    uploadFile("www/assets/i18n/canvas/", "canvas-equipe-fr-" + version.replace('.', '-') + ".json",
                            null, true);
                    uploadFile("www/assets/i18n/canvas/", "canvas-equipe-en-" + version.replace('.', '-') + ".json",
                            null, true);

                case 3:
                    repositoryCaseIndividuel.deleteAll(saveIndividuel);
                    uploadFile("www/assets/i18n/canvas/", "canvas-indiv-fr-" + version.replace('.', '-') + ".json",
                            null, true);
                    uploadFile("www/assets/i18n/canvas/", "canvas-indiv-en-" + version.replace('.', '-') + ".json",
                            null, true);

                case 2:
                    repositoryCase.deleteAll(saveCase);
                    uploadFile("www/assets/i18n/canvas/", "box-list-fr-" + version.replace('.', '-') + ".json", null,
                            true);
                    uploadFile("www/assets/i18n/canvas/", "box-list-en-" + version.replace('.', '-') + ".json", null,
                            true);

                case 1:
                    repositoryHeader.deleteAll(saveHeader);
                    uploadFile("www/assets/i18n/canvas/", "canvas-header-fr-" + version.replace('.', '-') + ".json",
                            null, true);
                    uploadFile("www/assets/i18n/canvas/", "canvas-header-en-" + version.replace('.', '-') + ".json",
                            null, true);

                default:
                    break;
            }

            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    private void deleteVersion(String version) {
        repositoryCaseIndividuel.deleteAll(repositoryCaseIndividuel.findByVersion(version));
        repositoryCaseEquipe.deleteAll(repositoryCaseEquipe.findByVersion(version));
        repositoryCase.deleteAll(repositoryCase.findByVersion(version));
        repositoryHeader.deleteAll(repositoryHeader.findByVersion(version));
        repositoryFooter.deleteAll(repositoryFooter.findByVersion(version));
    }

    public ByteArrayInputStream loadCaseIndividuel() {
        List<HeaderCanvas> headers = getLastVersionHeader();
        List<Case> cases = getLastVersionCase();
        List<CaseIndividuel> individuels = getLastVersionCaseIndividuel();
        List<CaseEquipe> equipes = getLastVersionCaseEquipe();
        List<FooterCanvas> footers = getLastVersionFooter();

        ByteArrayInputStream in = ExcelCanvasHelper.canvasToExcel(headers, footers, cases, individuels, equipes);
        return in;
    }

    private List<HeaderCanvas> getLastVersionHeader() {
        String version = repositoryHeader.max();
        return getVersionHeader(version);
    }

    private List<Case> getLastVersionCase() {
        String version = repositoryCase.max();
        return getVersionCase(version);
    }

    public List<CaseIndividuel> getLastVersionCaseIndividuel() {
        String version = repositoryCaseIndividuel.max();
        return getVersionCaseIndividuel(version);
    }

    private List<CaseEquipe> getLastVersionCaseEquipe() {
        String version = repositoryCaseEquipe.max();
        return getVersionCaseEquipe(version);
    }

    private List<FooterCanvas> getLastVersionFooter() {
        String version = repositoryFooter.max();
        return getVersionFooter(version);
    }

    private List<HeaderCanvas> getVersionHeader(String version) {
        return repositoryHeader.findByVersion(version);
    }

    private List<Case> getVersionCase(String version) {
        return repositoryCase.findByVersion(version);
    }

    private List<CaseIndividuel> getVersionCaseIndividuel(String version) {
        return repositoryCaseIndividuel.findByVersion(version);
    }

    private List<CaseEquipe> getVersionCaseEquipe(String version) {
        return repositoryCaseEquipe.findByVersion(version);
    }

    private List<FooterCanvas> getVersionFooter(String version) {
        return repositoryFooter.findByVersion(version);
    }

    public String getLastVersionCanvas() {
        return repositoryHeader.max();
    }

}
