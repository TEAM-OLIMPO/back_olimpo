package com.olimpo.olimpo.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateBasketRequest {
    private String titulo;
    private String descripcion;
    private BigDecimal precio;
    private int cantidadDisponible;
    private String imageUrl;

    private LocalDate fechaRecogida;
    private LocalTime horaInicioRecogida;
    private LocalTime horaFinRecogida;
    private Integer maxPorUsuario;
}
