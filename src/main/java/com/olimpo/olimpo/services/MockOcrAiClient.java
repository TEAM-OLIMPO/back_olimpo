package com.olimpo.olimpo.services;

import com.olimpo.olimpo.entities.ItemType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class MockOcrAiClient implements OcrAiClient {

    @Override
    public InvoiceOcrResult process(MultipartFile file) {
        // Aquí irá la integración real con Google Document AI, Textract, etc.
        // Esto es solo un ejemplo fijo para probar la app.

        InvoiceOcrResult result = new InvoiceOcrResult();
        result.patientId = "patient-123";
        result.invoiceDate = LocalDate.now();
        result.providerName = "IPS Ejemplo S.A.";
        result.providerNit = "900123456-7";
        result.totalAmount = new BigDecimal("150000");

        InvoiceOcrResult.Item item1 = new InvoiceOcrResult.Item();
        item1.code = "AMOX500";
        item1.description = "Amoxicilina 500mg";
        item1.quantity = 10;
        item1.unitPrice = new BigDecimal("5000"); // sobreprecio
        item1.totalPrice = item1.unitPrice.multiply(BigDecimal.valueOf(item1.quantity));
        item1.type = ItemType.MEDICINE;

        InvoiceOcrResult.Item item2 = new InvoiceOcrResult.Item();
        item2.code = "CONSULTA_EXT";
        item2.description = "Consulta medicina general";
        item2.quantity = 1;
        item2.unitPrice = new BigDecimal("30000");
        item2.totalPrice = item2.unitPrice;
        item2.type = ItemType.CONSULTATION;

        InvoiceOcrResult.Item item3 = new InvoiceOcrResult.Item();
        item3.code = "AMOX500";
        item3.description = "Amoxicilina 500mg (duplicada)";
        item3.quantity = 5;
        item3.unitPrice = new BigDecimal("5000");
        item3.totalPrice = item3.unitPrice.multiply(BigDecimal.valueOf(item3.quantity));
        item3.type = ItemType.MEDICINE;

        result.items.add(item1);
        result.items.add(item2);
        result.items.add(item3);

        return result;
    }
}
