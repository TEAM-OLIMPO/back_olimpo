package com.olimpo.olimpo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    private int cantidad;

    private BigDecimal total;

    private LocalDateTime fechaCompra;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
}
