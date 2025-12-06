package com.olimpo.olimpo.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.olimpo.olimpo.entities.AlertaEntity;
import com.olimpo.olimpo.entities.FacturaEntity;
import com.olimpo.olimpo.entities.FacturaItemEntity;
import com.olimpo.olimpo.dtos.AlertaDto;
import com.olimpo.olimpo.dtos.FacturaDetalleDto;
import com.olimpo.olimpo.dtos.FacturaItemDto;
import com.olimpo.olimpo.repositories.AlertaRepository;
import com.olimpo.olimpo.repositories.FacturaItemRepository;
import com.olimpo.olimpo.repositories.FacturaRepository;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final FacturaItemRepository facturaItemRepository;
    private final AlertaRepository alertaRepository;

    public FacturaService(FacturaRepository facturaRepository, FacturaItemRepository facturaItemRepository, AlertaRepository alertaRepository) {
        this.facturaRepository = facturaRepository;
        this.facturaItemRepository = facturaItemRepository;
        this.alertaRepository = alertaRepository;
    }

    // MVP: ignora la imagen y crea una factura de prueba.
    public FacturaDetalleDto procesarImagenFactura(MultipartFile file) {
        // 1. Crear factura demo
        FacturaEntity factura = new FacturaEntity();
        factura.setProveedor("IPS DEMO");
        factura.setNitProveedor("900123456-7");
        factura.setNombrePaciente("Paciente Demo");
        factura.setFechaFactura(LocalDate.now());
        factura.setTotalFactura(BigDecimal.valueOf(123_456));
        factura = facturaRepository.save(factura);

        // 2. Crear item demo
        FacturaItemEntity item = new FacturaItemEntity();
        item.setFactura(factura);
        item.setTipo("MEDICAMENTO");
        item.setCodigo("ACETA500");
        item.setNombre("Acetaminofén 500mg");
        item.setCantidad(10);
        item.setPrecioUnitario(BigDecimal.valueOf(1000));
        item.setPrecioTotal(BigDecimal.valueOf(10_000));
        facturaItemRepository.save(item);

        // 3. Crear alerta demo
        AlertaEntity alerta = new AlertaEntity();
        alerta.setFactura(factura);
        alerta.setTipo("SOBREPRECIO");
        alerta.setMensaje("El precio del Acetaminofén 500mg supera el promedio esperado.");
        alerta.setSeveridad("MEDIA");
        alertaRepository.save(alerta);

        return mapFacturaDetalle(factura);
    }

    public List<FacturaDetalleDto> listarFacturas() {
        return facturaRepository.findAll().stream().map(this::mapFacturaDetalle).toList();
    }

    public FacturaDetalleDto obtenerFactura(Long id) {
        FacturaEntity factura = facturaRepository.findById(id).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        return mapFacturaDetalle(factura);
    }

    private FacturaDetalleDto mapFacturaDetalle(FacturaEntity factura) {
        List<FacturaItemEntity> items = facturaItemRepository.findByFacturaId(factura.getId());
        List<AlertaEntity> alertas = alertaRepository.findByFacturaId(factura.getId());

        List<FacturaItemDto> itemDtos = items.stream().map(i -> new FacturaItemDto(i.getId(), i.getTipo(), i.getCodigo(), i.getNombre(), i.getCantidad(), i.getPrecioUnitario(), i.getPrecioTotal())).toList();

        List<AlertaDto> alertaDtos = alertas.stream().map(a -> new AlertaDto(a.getId(), a.getTipo(), a.getMensaje(), a.getSeveridad())).toList();

        return new FacturaDetalleDto(factura.getId(), factura.getProveedor(), factura.getNitProveedor(), factura.getNombrePaciente(), factura.getFechaFactura(), factura.getTotalFactura(), itemDtos, alertaDtos);
    }
}
