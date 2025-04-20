package org.sid.renaultvisiteursbackend.Export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sid.renaultvisiteursbackend.Entity.Visiteur;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelExporter {

    private final List<Visiteur> visiteurs;

    public ExcelExporter(List<Visiteur> visiteurs) {
        this.visiteurs = visiteurs;
    }

    public byte[] export() throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Visiteurs");

            // Styles d’en-tête
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // ✅ En-tête
            Row header = sheet.createRow(0);
            String[] titres = { "Nom", "Prénom", "CIN", "Genre", "Destination", "Date d’entrée" };
            for (int i = 0; i < titres.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(titres[i]);
                cell.setCellStyle(headerStyle);
            }

            // ✅ Contenu
            int rowIdx = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Visiteur v : visiteurs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(v.getNom());
                row.createCell(1).setCellValue(v.getPrenom());
                row.createCell(2).setCellValue(v.getCin());
                row.createCell(3).setCellValue(v.getGenre());
                row.createCell(4).setCellValue(v.getDestination());
                row.createCell(5).setCellValue(v.getDateEntree() != null ? formatter.format(v.getDateEntree()) : "");
            }

            // ✅ Auto-size colonnes
            for (int i = 0; i < titres.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
