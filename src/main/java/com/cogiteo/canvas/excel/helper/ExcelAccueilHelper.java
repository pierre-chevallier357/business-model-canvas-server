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

import com.cogiteo.canvas.excel.model.accueilObject.Accueil;

public class ExcelAccueilHelper {

    static String[] HOMEHEADERs = { "key", "valueFR", "valueEN" };
    static String HOMESHEET = "ACCUEIL";

    public static List<Accueil> excelToAccueil(InputStream is, long version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(HOMESHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Accueil> allItems = new ArrayList<Accueil>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Accueil newItem = new Accueil();
                newItem.setVersion(version);

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            newItem.setKey(currentCell.getStringCellValue());
                            break;

                        case 1:
                            newItem.setValueFR(currentCell.getStringCellValue());
                            break;

                        case 2:
                            newItem.setValueEN(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                allItems.add(newItem);
            }

            workbook.close();

            return allItems;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream accueilToExcel(List<Accueil> allAccueils) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheetAccueil = workbook.createSheet(HOMESHEET);

            // Header
            Row firstRowAccueil = sheetAccueil.createRow(0);

            for (int col = 0; col < HOMEHEADERs.length; col++) {
                Cell cell = firstRowAccueil.createCell(col);
                cell.setCellValue(HOMEHEADERs[col]);
            }

            int rowIdx = 1;
            for (Accueil item : allAccueils) {
                Row row = sheetAccueil.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " +
                    e.getMessage());
        }
    }
}
