package com.olimpo.olimpo.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.olimpo.olimpo.dtos.FacturaDetalleDto;
import com.olimpo.olimpo.services.FacturaService;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FacturaDetalleDto subirFactura(@RequestParam("file") MultipartFile file) {
        return facturaService.procesarImagenFactura(file);
    }

    @GetMapping
    public List<FacturaDetalleDto> listarFacturas() {
        return facturaService.listarFacturas();
    }

    @GetMapping("/{id}")
    public FacturaDetalleDto obtenerFactura(@PathVariable Long id) {
        return facturaService.obtenerFactura(id);
    }
}
