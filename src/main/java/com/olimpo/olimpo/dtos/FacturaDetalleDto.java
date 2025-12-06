package com.olimpo.olimpo.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record FacturaDetalleDto(Long id, String proveedor, String nitProveedor, String nombrePaciente, LocalDate fechaFactura, BigDecimal totalFactura, List<FacturaItemDto> items, List<AlertaDto> alertas) {}
