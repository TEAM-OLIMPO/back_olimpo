package com.olimpo.olimpo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Entity
@Table(name = "factura_items")
@Data
@EqualsAndHashCode(callSuper = true)
public class FacturaItemEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private FacturaEntity factura;

    // MEDICAMENTO | PROCEDIMIENTO | INSUMO
    private String tipo;

    private String codigo;

    private String nombre;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    private BigDecimal precioTotal;
}
