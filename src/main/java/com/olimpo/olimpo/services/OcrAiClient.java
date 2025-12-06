package com.olimpo.olimpo.services;

import org.springframework.web.multipart.MultipartFile;

public interface OcrAiClient {

    InvoiceOcrResult process(MultipartFile file);
}
