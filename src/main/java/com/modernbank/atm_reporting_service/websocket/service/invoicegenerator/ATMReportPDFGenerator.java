package com.modernbank.atm_reporting_service.websocket.service.invoicegenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("ATM_REPORT_INVOICE")
@Slf4j
public class ATMReportPDFGenerator implements IPDFGenerationStrategy {

    @Override
    public void generatePDF(Document document, Map<String, Object> data) {
        try {
            addCompanyName(document);
        }
        catch (Exception e){
            log.error("Error generating PDF: {}", e.getMessage());
        }
    }

    private static void addCompanyName(Document document) throws DocumentException {
        Font companyFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, new GrayColor(0.3f));
        Paragraph companyName = new Paragraph("MODERN BANK", companyFont);
        companyName.setAlignment(Element.ALIGN_LEFT);
        document.add(companyName);

        // İnce çizgi ekle
        LineSeparator line = new LineSeparator();
        line.setLineColor(new GrayColor(0.6f));
        document.add(new Chunk(line));
        document.add(Chunk.NEWLINE);
    }
}