package com.olimpo.olimpo.controllers;

import com.olimpo.olimpo.dtos.MedicamentoDTO;
import com.olimpo.olimpo.services.ApiMedicamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentosController {

    @Autowired
    private ApiMedicamentosService medicamentosApiService;

    @GetMapping
    public MedicamentoDTO[] getMedicamentos() {
        return medicamentosApiService.obtenerMedicamentos();
    }
}
