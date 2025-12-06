package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.dtos.InvoiceAnalysisResponse;
import com.olimpo.olimpo.dtos.InvoiceDTO;
import com.olimpo.olimpo.services.InvoiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<InvoiceAnalysisResponse> uploadInvoice(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "patientId", required = false) String patientId
    ) {
        InvoiceAnalysisResponse response = invoiceService.uploadAndAnalyze(file, patientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoice(id));
    }
}
