package com.olimpo.olimpo.dtos;

import java.math.BigDecimal;

public record FacturaItemDto(
        Long id,
        String tipo,
        String codigo,
        String nombre,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal precioTotal
) {}
