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

import com.cogiteo.canvas.excel.model.administratif.Conditions;
import com.cogiteo.canvas.excel.model.administratif.Cookies;
import com.cogiteo.canvas.excel.model.administratif.Mentions;
import com.cogiteo.canvas.excel.model.administratif.Policy;

public class ExcelAdministratifHelper {

    static String[] ADMINISTRATIFHEADERs = { "key", "valueFR", "valueEN" };
    static String CONDITIONSSHEET = "CONDITIONS";
    static String COOKIESHEET = "COOKIES";
    static String MENTIONSSHEET = "MENTIONS";
    static String POLICYSHEET = "POLICY";

    public static List<Conditions> excelToConditions(InputStream is, long version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(CONDITIONSSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Conditions> allItems = new ArrayList<Conditions>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Conditions newItem = new Conditions();
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

    public static List<Cookies> excelToCookies(InputStream is, long version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(COOKIESHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Cookies> allItems = new ArrayList<Cookies>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Cookies newItem = new Cookies();
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

    public static List<Mentions> excelToMentions(InputStream is, long version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(MENTIONSSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Mentions> allItems = new ArrayList<Mentions>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Mentions newItem = new Mentions();
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

    public static List<Policy> excelToPolicy(InputStream is, long version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(POLICYSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Policy> allItems = new ArrayList<Policy>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Policy newItem = new Policy();
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

    public static ByteArrayInputStream administratifToExcel(List<Conditions> allConditions, List<Cookies> allCookies,
            List<Mentions> allMentions, List<Policy> allPolicy) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheetConditions = workbook.createSheet(CONDITIONSSHEET);
            Sheet sheetCookies = workbook.createSheet(COOKIESHEET);
            Sheet sheetMentions = workbook.createSheet(MENTIONSSHEET);
            Sheet sheetPolicy = workbook.createSheet(POLICYSHEET);

            // Header one
            Row firstRowConditions = sheetConditions.createRow(0);
            Row firstRowCookies = sheetCookies.createRow(0);
            Row firstRowMentions = sheetMentions.createRow(0);
            Row firstRowPolicy = sheetPolicy.createRow(0);

            for (int col = 0; col < ADMINISTRATIFHEADERs.length; col++) {
                Cell cell = firstRowConditions.createCell(col);
                cell.setCellValue(ADMINISTRATIFHEADERs[col]);

                cell = firstRowCookies.createCell(col);
                cell.setCellValue(ADMINISTRATIFHEADERs[col]);

                cell = firstRowMentions.createCell(col);
                cell.setCellValue(ADMINISTRATIFHEADERs[col]);

                cell = firstRowPolicy.createCell(col);
                cell.setCellValue(ADMINISTRATIFHEADERs[col]);
            }

            int rowIdx = 1;
            for (Conditions item : allConditions) {
                Row row = sheetConditions.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (Cookies item : allCookies) {
                Row row = sheetCookies.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (Mentions item : allMentions) {
                Row row = sheetMentions.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (Policy item : allPolicy) {
                Row row = sheetPolicy.createRow(rowIdx++);

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
