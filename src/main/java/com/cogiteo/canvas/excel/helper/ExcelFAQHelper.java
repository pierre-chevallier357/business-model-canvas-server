package com.cogiteo.canvas.excel.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cogiteo.canvas.excel.model.FAQObject.FAQ;
import com.cogiteo.canvas.excel.model.FAQObject.HeaderFAQ;

public class ExcelFAQHelper {

  static String[] FAQFIRSTHEADERs = { "TitleFR", "TitleEN" };

  static String[] FAQSECONDHEADERs = { "questionFR", "reponseFR", "questionEN", "reponseEN" };
  static String FAQSHEET = "FAQ";

  public static ByteArrayInputStream faqToExcel(HeaderFAQ header, List<FAQ> allFAQs) {

    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(FAQSHEET);

      // Header one
      Row firsHeaderRow = sheet.createRow(0);

      for (int col = 0; col < FAQFIRSTHEADERs.length; col++) {
        Cell cell = firsHeaderRow.createCell(col);
        cell.setCellValue(FAQFIRSTHEADERs[col]);
      }

      Row titleRow = sheet.createRow(1);
      titleRow.createCell(0).setCellValue(header.getTitreFR());
      titleRow.createCell(1).setCellValue(header.getTitreEN());

      // Header two
      Row secondHeaderRow = sheet.createRow(2);

      for (int col = 0; col < FAQSECONDHEADERs.length; col++) {
        Cell cell = secondHeaderRow.createCell(col);
        cell.setCellValue(FAQSECONDHEADERs[col]);
      }

      int rowIdx = 3;
      for (FAQ faq : allFAQs) {
        Row row = sheet.createRow(rowIdx++);

        row.createCell(0).setCellValue(faq.getQuestionFR());
        row.createCell(1).setCellValue(faq.getReponseFR());
        row.createCell(2).setCellValue(faq.getQuestionEN());
        row.createCell(3).setCellValue(faq.getReponseEN());
      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

  public static List<Object> excelToFAQ(InputStream is, long newVersion) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(FAQSHEET);
      Iterator<Row> rows = sheet.iterator();

      List<FAQ> allItemFAQ = new ArrayList<FAQ>();
      List<Object> allFAQ = new ArrayList<Object>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();
        Iterator<Cell> cellsInRow = currentRow.iterator();
        int cellIdx = 0;

        // skip header
        if (rowNumber == 0 || rowNumber == 2) {
          rowNumber++;
          continue;
        } else if (rowNumber == 1) {
          HeaderFAQ headerFAQ = new HeaderFAQ();
          headerFAQ.setVersion(newVersion);
          while (cellsInRow.hasNext()) {
            Cell currentCell = cellsInRow.next();
            switch (cellIdx) {
              case 0:
                headerFAQ.setTitreFR(currentCell.getStringCellValue());
                break;
              case 1:
                headerFAQ.setTitreEN(currentCell.getStringCellValue());
                break;
              default:
                break;
            }
            cellIdx++;
          }
          rowNumber++;
          allFAQ.add(headerFAQ);
          continue;
        }

        FAQ itemFAQ = new FAQ();
        itemFAQ.setVersion(newVersion);

        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();

          switch (cellIdx) {
            case 0:
              itemFAQ.setQuestionFR(currentCell.getStringCellValue());
              break;

            case 1:
              itemFAQ.setReponseFR(currentCell.getStringCellValue());
              break;

            case 2:
              itemFAQ.setQuestionEN(currentCell.getStringCellValue());
              break;

            case 3:
              itemFAQ.setReponseEN(currentCell.getStringCellValue());
              break;

            default:
              break;
          }

          cellIdx++;
        }
        allItemFAQ.add(itemFAQ);
      }
      allFAQ.add(allItemFAQ);
      workbook.close();

      return allFAQ;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }

}