package com.olimpo.olimpo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private BigDecimal precio;

    private int cantidadDisponible;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    private LocalDateTime fechaCreacion;

    private boolean activa = true;

    @OneToMany(mappedBy = "basket")
    private List<Purchase> purchases;
}
