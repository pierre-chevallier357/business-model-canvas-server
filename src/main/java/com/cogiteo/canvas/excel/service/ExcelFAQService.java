package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelFAQHelper;
import com.cogiteo.canvas.excel.model.FAQObject.FAQ;
import com.cogiteo.canvas.excel.model.FAQObject.HeaderFAQ;
import com.cogiteo.canvas.excel.repository.repositoryExcelFAQ.RepositoryFAQ;
import com.cogiteo.canvas.excel.repository.repositoryExcelFAQ.RepositoryHeaderFAQ;

@Service
public class ExcelFAQService extends ExcelService {

  @Autowired
  RepositoryFAQ repositoryFAQ;

  @Autowired
  RepositoryHeaderFAQ repositoryHeaderFAQ;

  // PARTIE FAQ
  public void saveFAQ(MultipartFile file) throws JSONException {
    try {
      long newVersion = repositoryFAQ.max() + 1;
      List<Object> FAQItems = ExcelFAQHelper.excelToFAQ(file.getInputStream(), newVersion);
      repositoryHeaderFAQ.save((HeaderFAQ) FAQItems.get(0));
      repositoryFAQ.saveAll((List<FAQ>) FAQItems.get(1));

      HeaderFAQ headerFAQ = getLastVersionHeaderFAQ();

      uploadFile("www/assets/i18n/faq/", headerFAQ.getFileName("FR"), headerFAQ.getJSON("FR"), false);
      uploadFile("www/assets/i18n/faq/", headerFAQ.getFileName("EN"), headerFAQ.getJSON("EN"), false);

      List<FAQ> itemsFAQ = getLastVersionFAQ();
      StringBuilder frJSON = new StringBuilder("[");
      StringBuilder enJSON = new StringBuilder("[");
      for (FAQ item : itemsFAQ) {
        frJSON.append(item.getJSON("FR") + ",");
        enJSON.append(item.getJSON("EN") + ",");
      }

      removeLastChar(frJSON);
      removeLastChar(enJSON);

      frJSON.append("]");
      enJSON.append("]");

      uploadFile("www/assets/i18n/faq/", "faq-fr.json", frJSON.toString(), false);
      uploadFile("www/assets/i18n/faq/", "faq-en.json", enJSON.toString(), false);

    } catch (IOException e) {
      throw new RuntimeException("fail to store excel data: " + e.getMessage());
    }
  }

  public ByteArrayInputStream loadFAQ() {
    List<FAQ> allFAQs = getLastVersionFAQ();
    HeaderFAQ header = getLastVersionHeaderFAQ();

    ByteArrayInputStream in = ExcelFAQHelper.faqToExcel(header, allFAQs);
    return in;
  }

  private HeaderFAQ getLastVersionHeaderFAQ() {
    long version = repositoryHeaderFAQ.max();
    List<HeaderFAQ> list = repositoryHeaderFAQ.findByVersion(version);
    return list.get(0);
  }

  public List<FAQ> getLastVersionFAQ() {
    long version = repositoryFAQ.max();
    return repositoryFAQ.findByVersion(version);
  }

  public void removeLastChar(StringBuilder sb) {
    sb.deleteCharAt(sb.length() - 1);
  }

}
