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

import com.cogiteo.canvas.excel.model.messageObject.Message;

public class ExcelMessageHelper {
    static String[] HEADERs = { "key", "valueFR", "valueEN" };
    static String MESSAGESHEET = "MESSAGE";

    public static List<Message> excelToMessage(InputStream is, long newVersion) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(MESSAGESHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Message> allItems = new ArrayList<Message>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Message newItem = new Message();
                newItem.setVersion(newVersion);

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

    public static ByteArrayInputStream messageToExcel(List<Message> allMessage) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheetHeader = workbook.createSheet(MESSAGESHEET);

            // Header one
            Row firstRow = sheetHeader.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = firstRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (Message item : allMessage) {
                Row row = sheetHeader.createRow(rowIdx++);

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
