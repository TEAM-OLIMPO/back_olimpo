package com.olimpo.olimpo.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Data
@EqualsAndHashCode(callSuper = true)
public class AlertaEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private FacturaEntity factura;

    // SOBREPRECIO, DUPLICADO, NO_ENTREGADO, etc.
    private String tipo;

    @Column(length = 1000)
    private String mensaje;

    // BAJA, MEDIA, ALTA
    private String severidad;

    private LocalDateTime creadaEn = LocalDateTime.now();
}
