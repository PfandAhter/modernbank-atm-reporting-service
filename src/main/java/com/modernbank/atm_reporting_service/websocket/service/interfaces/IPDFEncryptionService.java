package com.modernbank.atm_reporting_service.websocket.service.interfaces;

import com.modernbank.atm_reporting_service.websocket.service.encryption.PDFEncryptionServiceImpl;

public interface IPDFEncryptionService {
    PDFEncryptionServiceImpl.EncryptedData encryptPDF (byte[] pdfContent, String password);

    byte[] decryptPDF(byte[] encryptedData,String password, String saltString);

}
