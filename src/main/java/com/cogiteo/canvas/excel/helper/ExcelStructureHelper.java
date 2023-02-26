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

import com.cogiteo.canvas.excel.model.structure.Footer;
import com.cogiteo.canvas.excel.model.structure.HeaderStructure;

public class ExcelStructureHelper {

    static String[] HEADERs = { "key", "valueFR", "valueEN" };
    static String HEADERSHEET = "HEADER";
    static String FOOTERSHEET = "FOOTER";

    public static List<HeaderStructure> excelToHeader(InputStream is, long newVersionHeader) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(HEADERSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<HeaderStructure> allItems = new ArrayList<HeaderStructure>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                HeaderStructure newItem = new HeaderStructure();
                newItem.setVersion(newVersionHeader);

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

    public static List<Footer> excelToFooter(InputStream is, long newVersionFooter) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(FOOTERSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Footer> allItems = new ArrayList<Footer>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Footer newItem = new Footer();
                newItem.setVersion(newVersionFooter);

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

    public static ByteArrayInputStream structureToExcel(List<HeaderStructure> allHeader, List<Footer> allFooter) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheetHeader = workbook.createSheet(HEADERSHEET);
            Sheet sheetFooter = workbook.createSheet(FOOTERSHEET);

            // Header one
            Row firstRowHeader = sheetHeader.createRow(0);
            Row firstRowFooter = sheetFooter.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = firstRowHeader.createCell(col);
                cell.setCellValue(HEADERs[col]);

                cell = firstRowFooter.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (HeaderStructure item : allHeader) {
                Row row = sheetHeader.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (Footer item : allFooter) {
                Row row = sheetFooter.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

}
