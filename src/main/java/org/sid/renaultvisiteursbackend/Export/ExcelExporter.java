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

            // Style d’en-tête
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            // ✅ En-têtes
            Row header = sheet.createRow(0);
            String[] titres = {
                    "Nom", "Prénom", "CIN", "Genre", "Téléphone", "Destination",
                    "Type de visiteur", "Matricule", "Date d'entrée", "Date de sortie"
            };
            for (int i = 0; i < titres.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(titres[i]);
                cell.setCellStyle(headerStyle);
            }

            // ✅ Remplissage des données
            int rowIdx = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Visiteur v : visiteurs) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(v.getNom() != null ? v.getNom() : "");
                row.createCell(1).setCellValue(v.getPrenom() != null ? v.getPrenom() : "");
                row.createCell(2).setCellValue(v.getCin() != null ? v.getCin() : "");
                row.createCell(3).setCellValue(v.getGenre() != null ? v.getGenre() : "");
                row.createCell(4).setCellValue(v.getTelephone() != null ? v.getTelephone() : "");
                row.createCell(5).setCellValue(v.getDestination() != null ? v.getDestination() : "");
                row.createCell(6).setCellValue(v.getTypeVisiteur() != null ? v.getTypeVisiteur() : "");
                row.createCell(7).setCellValue(v.getMatricule() != null ? v.getMatricule() : "");
                row.createCell(8).setCellValue(v.getDateEntree() != null ? formatter.format(v.getDateEntree()) : "");
                row.createCell(9).setCellValue(v.getDateSortie() != null ? formatter.format(v.getDateSortie()) : "");
            }

            // ✅ Auto-size sur toutes les colonnes
            for (int i = 0; i < titres.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }
}
