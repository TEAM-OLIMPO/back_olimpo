package com.olimpo.olimpo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    // 🗺️ Info para mapa y perfil (para vendedores)
    private String direccion;     // Ej: "Cra 7 # 72-41, Bogotá"
    private Double latitud;       // Ej: 4.6482837
    private Double longitud;      // Ej: -74.2478935
    private String descripcionNegocio; // "Panadería artesanal con productos del día"

    @OneToMany(mappedBy = "vendor")
    private List<Basket> baskets;

    @OneToMany(mappedBy = "buyer")
    private List<Purchase> purchases;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "vendor")
    private List<Rating> ratingsRecibidas;

    @OneToMany(mappedBy = "buyer")
    private List<Rating> ratingsHechas;
}
