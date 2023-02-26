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

import com.cogiteo.canvas.excel.model.canvas.HeaderCanvas;
import com.cogiteo.canvas.excel.model.canvas.Case;
import com.cogiteo.canvas.excel.model.canvas.CaseEquipe;
import com.cogiteo.canvas.excel.model.canvas.CaseIndividuel;
import com.cogiteo.canvas.excel.model.canvas.FooterCanvas;

public class ExcelCanvasHelper {

    static String[] CASEINDIVIDUELHEADERs = { "libelleCase", "aide" };
    static String CASEINDIVIDUELSHEET = "INDIVIDUEL";

    static String[] GLOBALLHEADERs = { "Langue", "HeaderTitle", "CanvasTitle", "Date", "AideIndividuel", "AideEquipe" };
    static String GLOBALSHEET = "HEADER";

    static String[] CASEHEADERs = { "langue", "numeroCase", "titre" };
    static String CASESHEET = "CASE";

    static String[] CASEEQUIPEHEADERs = { "libelleCase", "aide" };
    static String CASEEQUIPESHEET = "EQUIPE";

    static String[] FOOTERHEADERs = { "langue", "description" };
    static String FOOTERSHEET = "FOOTER";

    public static List<CaseIndividuel> excelToCaseIndividuel(InputStream is, String version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(CASEINDIVIDUELSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<CaseIndividuel> allCaseIndividuels = new ArrayList<CaseIndividuel>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                CaseIndividuel caseIndividuel = new CaseIndividuel();
                caseIndividuel.setVersion(version);

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            caseIndividuel.setLibelleCase(currentCell.getStringCellValue());
                            break;

                        case 1:
                            caseIndividuel.setAide(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                allCaseIndividuels.add(caseIndividuel);
            }

            workbook.close();

            return allCaseIndividuels;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static String extractVersion(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(GLOBALSHEET);
            Iterator<Row> rows = sheet.iterator();

            String version = "";
            if (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();

                cellsInRow.next();
                version = cellsInRow.next().getStringCellValue();

            }
            workbook.close();

            return version;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<HeaderCanvas> excelToHeader(InputStream is, String version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(GLOBALSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<HeaderCanvas> allHeader = new ArrayList<HeaderCanvas>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();

                // skip header
                if (rowNumber == 0 || rowNumber == 1) {
                    rowNumber++;
                    continue;
                }

                HeaderCanvas caseGlobal = new HeaderCanvas();
                caseGlobal.setVersion(version);
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            caseGlobal.setLangue(currentCell.getStringCellValue());
                            break;

                        case 1:
                            caseGlobal.setHeaderTitle(currentCell.getStringCellValue());
                            break;

                        case 2:
                            caseGlobal.setCanvasTitle(currentCell.getStringCellValue());
                            break;

                        case 3:
                            caseGlobal.setHelp(currentCell.getStringCellValue());
                            break;

                        case 4:
                            caseGlobal.setDate(currentCell.getStringCellValue());
                            break;

                        case 5:
                            caseGlobal.setAideIndividuel(currentCell.getStringCellValue());
                            break;

                        case 6:
                            caseGlobal.setAideEquipe(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                allHeader.add(caseGlobal);
            }

            workbook.close();

            return allHeader;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<Case> excelToCase(InputStream is, String version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(CASESHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Case> allCase = new ArrayList<Case>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Case caseItem = new Case();
                caseItem.setVersion(version);
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            caseItem.setLangue(currentCell.getStringCellValue());
                            break;

                        case 1:
                            caseItem.setNumeroCase(
                                    currentCell.getCellType().name().equals("NUMERIC")
                                            ? String.valueOf((int) currentCell.getNumericCellValue())
                                            : currentCell.getStringCellValue());
                            break;

                        case 2:
                            caseItem.setTitre(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                allCase.add(caseItem);
            }

            workbook.close();

            return allCase;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static List<CaseEquipe> excelToCaseEquipe(InputStream is, String version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(CASEEQUIPESHEET);
            Iterator<Row> rows = sheet.iterator();

            List<CaseEquipe> allCaseEquipe = new ArrayList<CaseEquipe>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                CaseEquipe caseEquipe = new CaseEquipe();
                caseEquipe.setVersion(version);

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            caseEquipe.setLibelleCase(currentCell.getStringCellValue());
                            break;

                        case 1:
                            caseEquipe.setAide(currentCell.getStringCellValue());
                            break;

                        default:
                            break;
                    }

                    cellIdx++;
                }

                allCaseEquipe.add(caseEquipe);
            }

            workbook.close();

            return allCaseEquipe;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream canvasToExcel(List<HeaderCanvas> headers, List<FooterCanvas> footers,
            List<Case> cases,
            List<CaseIndividuel> individuels,
            List<CaseEquipe> equipes) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheetHeader = workbook.createSheet(GLOBALSHEET);
            Sheet sheetFooter = workbook.createSheet(FOOTERSHEET);
            Sheet sheetCase = workbook.createSheet(CASESHEET);
            Sheet sheetIndividuel = workbook.createSheet(CASEINDIVIDUELSHEET);
            Sheet sheetEquipe = workbook.createSheet(CASEEQUIPESHEET);

            // Version
            Row versionRowHeader = sheetHeader.createRow(0);

            for (int col = 0; col < 2; col++) {
                Cell cell = versionRowHeader.createCell(col);
                cell.setCellValue(col == 0 ? "Version" : headers.get(0).getVersion());
            }

            // Header
            Row headerRowHeader = sheetHeader.createRow(1);

            for (int col = 0; col < GLOBALLHEADERs.length; col++) {
                Cell cell = headerRowHeader.createCell(col);
                cell.setCellValue(GLOBALLHEADERs[col]);
            }

            int rowIdx = 2;
            for (HeaderCanvas header : headers) {
                Row row = sheetHeader.createRow(rowIdx++);

                row.createCell(0).setCellValue(header.getLangue());
                row.createCell(1).setCellValue(header.getHeaderTitle());
                row.createCell(2).setCellValue(header.getCanvasTitle());
                row.createCell(3).setCellValue(header.getHelp());
                row.createCell(4).setCellValue(header.getDate());
                row.createCell(5).setCellValue(header.getAideIndividuel());
                row.createCell(6).setCellValue(header.getAideEquipe());
            }

            // Footer
            Row headerRowFooter = sheetFooter.createRow(0);

            for (int col = 0; col < FOOTERHEADERs.length; col++) {
                Cell cell = headerRowFooter.createCell(col);
                cell.setCellValue(FOOTERHEADERs[col]);
            }

            rowIdx = 1;
            for (FooterCanvas footer : footers) {
                Row row = sheetFooter.createRow(rowIdx++);

                row.createCell(0).setCellValue(footer.getLangue());
                row.createCell(1).setCellValue(footer.getDescription());
            }

            // Case
            Row headerRowCase = sheetCase.createRow(0);

            for (int col = 0; col < CASEHEADERs.length; col++) {
                Cell cell = headerRowCase.createCell(col);
                cell.setCellValue(CASEHEADERs[col]);
            }

            rowIdx = 1;
            for (Case caseInfo : cases) {
                Row row = sheetCase.createRow(rowIdx++);

                row.createCell(0).setCellValue(caseInfo.getLangue());
                row.createCell(1).setCellValue(caseInfo.getNumeroCase());
                row.createCell(2).setCellValue(caseInfo.getTitre());
            }

            // Individuel
            Row headerRowIndividuel = sheetIndividuel.createRow(0);

            for (int col = 0; col < CASEINDIVIDUELHEADERs.length; col++) {
                Cell cell = headerRowIndividuel.createCell(col);
                cell.setCellValue(CASEINDIVIDUELHEADERs[col]);
            }

            rowIdx = 1;
            for (CaseIndividuel individuel : individuels) {
                Row row = sheetIndividuel.createRow(rowIdx++);

                row.createCell(0).setCellValue(individuel.getLibelleCase());
                row.createCell(1).setCellValue(individuel.getAide());
            }

            // Equipe
            Row headerRowEquipe = sheetEquipe.createRow(0);

            for (int col = 0; col < CASEEQUIPEHEADERs.length; col++) {
                Cell cell = headerRowEquipe.createCell(col);
                cell.setCellValue(CASEEQUIPEHEADERs[col]);
            }

            rowIdx = 1;
            for (CaseEquipe equipe : equipes) {
                Row row = sheetEquipe.createRow(rowIdx++);

                row.createCell(0).setCellValue(equipe.getLibelleCase());
                row.createCell(1).setCellValue(equipe.getAide());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

    public static List<FooterCanvas> excelToFooter(InputStream is, String version) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(FOOTERSHEET);
            Iterator<Row> rows = sheet.iterator();

            List<FooterCanvas> allFooter = new ArrayList<FooterCanvas>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                FooterCanvas footerItem = new FooterCanvas();
                footerItem.setVersion(version);
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            footerItem.setLangue(currentCell.getStringCellValue());
                            break;
                        case 1:
                            footerItem.setDescription(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }

                allFooter.add(footerItem);
            }

            workbook.close();

            return allFooter;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
