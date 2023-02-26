package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelAccueilHelper;
import com.cogiteo.canvas.excel.model.accueilObject.Accueil;
import com.cogiteo.canvas.excel.repository.repositoryExcelAccueil.RepositoryAccueil;

@Service
public class ExcelAccueilService extends ExcelService {

    @Autowired
    RepositoryAccueil repositoryAccueil;

    public void saveAccueil(MultipartFile file) throws JSONException {
        try {
            long newVersionAccueil = repositoryAccueil.max() + 1;
            List<Accueil> accueilItems = ExcelAccueilHelper.excelToAccueil(file.getInputStream(), newVersionAccueil);
            repositoryAccueil.saveAll(accueilItems);

            // ACCUEIL
            Accueil accueil = new Accueil();

            // recup last version
            List<Accueil> allAccueil = getLastVersionAccueil();

            // upload & construit json
            uploadFile("www/assets/i18n/home/", "home-fr.json", accueil.getJSON("FR", allAccueil),
                    false);
            uploadFile("www/assets/i18n/home/", "home-en.json", accueil.getJSON("EN", allAccueil),
                    false);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    private List<Accueil> getLastVersionAccueil() {
        long version = repositoryAccueil.max();
        return repositoryAccueil.findByVersion(version);
    }

    public InputStream loadAccueil() {
        List<Accueil> allAccueils = getLastVersionAccueil();

        ByteArrayInputStream in = ExcelAccueilHelper.accueilToExcel(allAccueils);
        return in;
    }

}
