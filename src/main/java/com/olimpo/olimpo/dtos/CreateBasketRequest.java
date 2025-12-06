package com.olimpo.olimpo.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBasketRequest {
    private String titulo;
    private String descripcion;
    private BigDecimal precio;
    private int cantidadDisponible;
}
