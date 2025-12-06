package com.olimpo.olimpo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {

    @GetMapping("/")
    public String home() {
        return "API de facturas en salud está corriendo. " +
               "Usa POST /api/invoices/upload con un archivo 'file'.";
    }
}
