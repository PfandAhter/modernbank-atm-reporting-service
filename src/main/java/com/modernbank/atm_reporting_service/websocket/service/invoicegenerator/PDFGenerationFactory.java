package com.modernbank.atm_reporting_service.websocket.service.invoicegenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.modernbank.atm_reporting_service.exceptions.NotFoundException;
import com.modernbank.atm_reporting_service.exceptions.ProcessFailedException;
import com.modernbank.atm_reporting_service.model.enums.PDFGenerationTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.INVALID_PDF_TYPE;
import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.STRATEGY_NOT_FOUND;
import static com.modernbank.atm_reporting_service.constants.ErrorCodeConstants.PDF_GENERATION_FAILED;

@Component
@Slf4j
public class PDFGenerationFactory {

    private final ApplicationContext context;

    private final Map<String, IPDFGenerationStrategy> generatorMap = new HashMap<>();

    public PDFGenerationFactory(ApplicationContext context){
        this.context = context;

        for(PDFGenerationTypes type : PDFGenerationTypes.values()){
            String beanName = type.getType();
            IPDFGenerationStrategy generator = (IPDFGenerationStrategy) context.getBean(beanName);
            generatorMap.put(type.getType(), generator);
        }
    }

    public byte[] generate(String generationType, Map<String,Object> data){
        PDFGenerationTypes invoiceType = PDFGenerationTypes.fromString(generationType)
                .orElseThrow(() -> new NotFoundException(INVALID_PDF_TYPE));

        IPDFGenerationStrategy strategy = Optional.ofNullable(generatorMap.get(invoiceType.getType()))
                .orElseThrow(() -> new IllegalArgumentException(STRATEGY_NOT_FOUND));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            strategy.generatePDF(document,data);

            log.info("Document generated, Byte length: {}", byteArrayOutputStream.size());
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            log.error("Error generating PDF for generation type {}: {}", generationType, e.getMessage());
            throw new ProcessFailedException(PDF_GENERATION_FAILED);
        }finally {
            document.close();
        }
    }

    public List<String> getAvailableStrategies(){
        return generatorMap.keySet().stream().toList();
    }
}