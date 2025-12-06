package com.olimpo.olimpo.services;

import com.olimpo.olimpo.dtos.*;
import com.olimpo.olimpo.entities.Invoice;
import com.olimpo.olimpo.entities.InvoiceItem;
import com.olimpo.olimpo.entities.ItemType;
import com.olimpo.olimpo.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final OcrAiClient ocrAiClient;
    private final InvoiceRepository invoiceRepository;
    private final FraudAnalysisService fraudAnalysisService;
    private final ClaimService claimService;
    private final RiskAggregationService riskAggregationService;

    public InvoiceService(OcrAiClient ocrAiClient,
                          InvoiceRepository invoiceRepository,
                          FraudAnalysisService fraudAnalysisService,
                          ClaimService claimService,
                          RiskAggregationService riskAggregationService) {
        this.ocrAiClient = ocrAiClient;
        this.invoiceRepository = invoiceRepository;
        this.fraudAnalysisService = fraudAnalysisService;
        this.claimService = claimService;
        this.riskAggregationService = riskAggregationService;
    }

    @Transactional
    public InvoiceAnalysisResponse uploadAndAnalyze(MultipartFile file, String patientId) {
        // 1) Pasar por IA/OCR
        InvoiceOcrResult ocrResult = ocrAiClient.process(file);

        if (patientId != null && !patientId.isBlank()) {
            ocrResult.patientId = patientId;
        }

        // 2) Mapear a entidad y guardar
        Invoice invoice = mapToEntity(ocrResult);
        invoice = invoiceRepository.save(invoice);

        // 3) Analizar fraude / sobreprecios / duplicados
        var anomalies = fraudAnalysisService.analyze(invoice);

        // 4) Generar borrador de reclamación
        var claimDraft = claimService.generateClaim(invoice, anomalies);

        // 5) Enviar versión anonimizada al cerebro de riesgo
        riskAggregationService.sendAnonymized(invoice);

        // 6) Construir respuesta
        InvoiceAnalysisResponse response = new InvoiceAnalysisResponse();
        response.setInvoice(mapToDto(invoice));
        response.setAnomalies(anomalies);
        response.setClaimDraft(claimDraft);

        return response;
    }

    @Transactional(readOnly = true)
    public InvoiceDTO getInvoice(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Factura no encontrada"));
        return mapToDto(invoice);
    }

    private Invoice mapToEntity(InvoiceOcrResult result) {
        Invoice invoice = new Invoice();
        invoice.setPatientId(result.patientId);
        invoice.setInvoiceDate(result.invoiceDate);
        invoice.setProviderName(result.providerName);
        invoice.setProviderNit(result.providerNit);
        invoice.setTotalAmount(result.totalAmount);

        for (InvoiceOcrResult.Item itemResult : result.items) {
            InvoiceItem item = new InvoiceItem();
            item.setCode(itemResult.code);
            item.setDescription(itemResult.description);
            item.setQuantity(itemResult.quantity);
            item.setUnitPrice(itemResult.unitPrice);
            item.setTotalPrice(itemResult.totalPrice);
            item.setType(itemResult.type != null ? itemResult.type : ItemType.OTHER);
            invoice.addItem(item);
        }

        return invoice;
    }

    private InvoiceDTO mapToDto(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        dto.setId(invoice.getId());
        dto.setPatientId(invoice.getPatientId());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setProviderName(invoice.getProviderName());
        dto.setProviderNit(invoice.getProviderNit());
        dto.setTotalAmount(invoice.getTotalAmount());

        List<InvoiceItemDTO> itemDTOs = invoice.getItems().stream()
                .map(item -> {
                    InvoiceItemDTO i = new InvoiceItemDTO();
                    i.setId(item.getId());
                    i.setCode(item.getCode());
                    i.setDescription(item.getDescription());
                    i.setQuantity(item.getQuantity());
                    i.setUnitPrice(item.getUnitPrice());
                    i.setTotalPrice(item.getTotalPrice());
                    i.setType(item.getType() != null ? item.getType().name() : null);
                    return i;
                })
                .collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }
}
