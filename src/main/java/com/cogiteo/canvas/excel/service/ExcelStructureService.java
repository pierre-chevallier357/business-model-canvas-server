package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelStructureHelper;
import com.cogiteo.canvas.excel.model.structure.Footer;
import com.cogiteo.canvas.excel.model.structure.HeaderStructure;
import com.cogiteo.canvas.excel.repository.repositoryStructure.RepositoryStructureFooter;
import com.cogiteo.canvas.excel.repository.repositoryStructure.RepositoryStructureHeader;

@Service
public class ExcelStructureService extends ExcelService {
    @Autowired
    RepositoryStructureHeader repositoryStructureHeader;

    @Autowired
    RepositoryStructureFooter repositoryStructureFooter;

    public void saveStructure(MultipartFile file) throws JSONException {
        try {
            long newVersionHeader = repositoryStructureHeader.max() + 1;
            long newVersionFooter = repositoryStructureFooter.max() + 1;

            List<HeaderStructure> headerItems = ExcelStructureHelper.excelToHeader(file.getInputStream(),
                    newVersionHeader);
            List<Footer> footerItems = ExcelStructureHelper.excelToFooter(file.getInputStream(), newVersionFooter);
            repositoryStructureHeader.saveAll(headerItems);
            repositoryStructureFooter.saveAll(footerItems);

            // HEADER
            HeaderStructure header = new HeaderStructure();

            // recup last version
            List<HeaderStructure> allHeader = getLastVersionHeader();

            // upload & construit json
            uploadFile("www/assets/i18n/header/", "header-fr.json", header.getJSON("FR", allHeader),
                    false);
            uploadFile("www/assets/i18n/header/", "header-en.json", header.getJSON("EN", allHeader),
                    false);

            // FOOTER
            Footer footer = new Footer();

            // recup last version
            List<Footer> allFooter = getLastVersionFooter();

            // upload & construit json
            uploadFile("www/assets/i18n/footer/", "footer-fr.json", footer.getJSON("FR", allFooter),
                    false);
            uploadFile("www/assets/i18n/footer/", "footer-en.json", footer.getJSON("EN", allFooter),
                    false);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    private List<Footer> getLastVersionFooter() {
        long version = repositoryStructureFooter.max();
        return repositoryStructureFooter.findByVersion(version);
    }

    private List<HeaderStructure> getLastVersionHeader() {
        long version = repositoryStructureHeader.max();
        return repositoryStructureHeader.findByVersion(version);
    }

    public InputStream loadStructure() {
        List<HeaderStructure> allHeader = getLastVersionHeader();
        List<Footer> allFooter = getLastVersionFooter();

        ByteArrayInputStream in = ExcelStructureHelper.structureToExcel(allHeader, allFooter);
        return in;
    }
}
