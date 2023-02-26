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

import com.cogiteo.canvas.excel.model.utilisateur.Connexion;
import com.cogiteo.canvas.excel.model.utilisateur.Inscription;
import com.cogiteo.canvas.excel.model.utilisateur.MdpOublie;
import com.cogiteo.canvas.excel.model.utilisateur.Profil;

public class ExcelUtilisateurHelper {

    static String[] HEADERs = { "key", "valueFR", "valueEN" };
    static String CONNEXIONSHEET = "CONNEXION";
    static String INSCRIPTIONSHEET = "INSCRIPTION";
    static String MDPOUBLIESHEET = "MDPOUBLIE";
    static String PROFILSHEET = "PROFIL";

    public static List<Connexion> excelToConnexion(InputStream is, long newVersionConnexion) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(CONNEXIONSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Connexion> allItems = new ArrayList<Connexion>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Connexion newItem = new Connexion();
                newItem.setVersion(newVersionConnexion);

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

    public static List<Inscription> excelToInscription(InputStream is, long newVersionInscription) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(INSCRIPTIONSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Inscription> allItems = new ArrayList<Inscription>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Inscription newItem = new Inscription();
                newItem.setVersion(newVersionInscription);

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

    public static List<Profil> excelToProfil(InputStream is, long newVersionProfil) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(PROFILSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Profil> allItems = new ArrayList<Profil>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Profil newItem = new Profil();
                newItem.setVersion(newVersionProfil);

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

    public static List<MdpOublie> excelToMdpOublie(InputStream is, long newVersionMdpOublie) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(MDPOUBLIESHEET);
            Iterator<Row> rows = sheet.iterator();

            List<MdpOublie> allItems = new ArrayList<MdpOublie>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                MdpOublie newItem = new MdpOublie();
                newItem.setVersion(newVersionMdpOublie);

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

    public static ByteArrayInputStream utilisateurToExcel(List<Connexion> allConnexion,
            List<Inscription> allInscription, List<MdpOublie> allMdpOublie, List<Profil> allProfil) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheetConnexion = workbook.createSheet(CONNEXIONSHEET);
            Sheet sheetInscription = workbook.createSheet(INSCRIPTIONSHEET);
            Sheet sheetMdpOublie = workbook.createSheet(MDPOUBLIESHEET);
            Sheet sheetProfil = workbook.createSheet(PROFILSHEET);

            // Header one
            Row firstRowConnexion = sheetConnexion.createRow(0);
            Row firstRowInscription = sheetInscription.createRow(0);
            Row firstRowMdpOublie = sheetMdpOublie.createRow(0);
            Row firstRowProfil = sheetProfil.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = firstRowConnexion.createCell(col);
                cell.setCellValue(HEADERs[col]);

                cell = firstRowInscription.createCell(col);
                cell.setCellValue(HEADERs[col]);

                cell = firstRowMdpOublie.createCell(col);
                cell.setCellValue(HEADERs[col]);

                cell = firstRowProfil.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Connexion item : allConnexion) {
                Row row = sheetConnexion.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (Inscription item : allInscription) {
                Row row = sheetInscription.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (MdpOublie item : allMdpOublie) {
                Row row = sheetMdpOublie.createRow(rowIdx++);

                row.createCell(0).setCellValue(item.getKey());
                row.createCell(1).setCellValue(item.getValueFR());
                row.createCell(2).setCellValue(item.getValueEN());
            }

            rowIdx = 1;
            for (Profil item : allProfil) {
                Row row = sheetProfil.createRow(rowIdx++);

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
