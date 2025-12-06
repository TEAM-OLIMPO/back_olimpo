package com.olimpo.olimpo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
@Data
@EqualsAndHashCode(callSuper = true)
public class FacturaEntity extends BaseEntity {

    // IPS / farmacia / proveedor
    private String proveedor;

    private String nitProveedor;

    private String nombrePaciente;

    private LocalDate fechaFactura;

    private BigDecimal totalFactura;

    private LocalDateTime creadaEn = LocalDateTime.now();
}
