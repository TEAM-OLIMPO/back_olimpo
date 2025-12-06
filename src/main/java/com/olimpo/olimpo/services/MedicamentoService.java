package com.olimpo.olimpo.services;

import com.olimpo.olimpo.dtos.MedicamentoDTO;
import com.olimpo.olimpo.entities.Medicamento;
import com.olimpo.olimpo.repositories.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MedicamentoService {

    @Value("${medicamentos.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> sincronizarDesdeApi() {

        MedicamentoDTO[] medicamentosApi =
                restTemplate.getForObject(apiUrl, MedicamentoDTO[].class);

        List<Medicamento> medicamentos = Arrays.stream(medicamentosApi)
                .map(dto -> new Medicamento(
                        null,
                        dto.getProducto(),
                        dto.getPrincipio_activo(),
                        dto.getForma_farmaceutica(),
                        dto.getUnidad_medida(),
                        parsePrecio(dto.getValor())
                ))
                .toList();

        return medicamentoRepository.saveAll(medicamentos);
    }

    private Double parsePrecio(String valor) {
        try {
            return Double.parseDouble(valor);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public List<Medicamento> obtenerTodos() {
        return medicamentoRepository.findAll();
    }
}
