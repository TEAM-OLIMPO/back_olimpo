package com.olimpo.olimpo.services;

import com.olimpo.olimpo.dtos.MedicamentoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiMedicamentosService {

    @Value("${medicamentos.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public MedicamentoDTO[] obtenerMedicamentos() {
        return restTemplate.getForObject(apiUrl, MedicamentoDTO[].class);
    }
}
