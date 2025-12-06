package com.olimpo.olimpo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private BigDecimal precio; // ya es el precio reducido

    private int cantidadDisponible;

    // 📸 Foto opcional
    private String imageUrl; // URL a imagen en S3, Firebase, etc.

    // ⏰ Horario de recogida
    private LocalDate fechaRecogida;   // día para recoger
    private LocalTime horaInicioRecogida;
    private LocalTime horaFinRecogida;

    // Límite por usuario, si quieres
    private Integer maxPorUsuario;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    private LocalDateTime fechaCreacion;

    private boolean activa = true;

    @OneToMany(mappedBy = "basket")
    private List<Purchase> purchases;
}
