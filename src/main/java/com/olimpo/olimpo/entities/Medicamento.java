package com.olimpo.olimpo.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medicamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;

    private String laboratorio;

    private String descripcion;
}
