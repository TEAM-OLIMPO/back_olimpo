package com.olimpo.olimpo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Olimpo API: backend corriendo. Usa /api/facturas para trabajar con facturas.";
    }

    @GetMapping("/api/health")
    public String health() {
        return "OK";
    }
}
