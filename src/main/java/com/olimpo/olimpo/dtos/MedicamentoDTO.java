package com.olimpo.olimpo.dtos;

import lombok.Data;

@Data
public class MedicamentoDTO {
    private String nombre;
    private Double precio;
    private String laboratorio;
    private String descripcion;

    // Get 
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;}
    public String getLaboratorio() {
        return laboratorio;
    }
    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }
    public String getDescripcion() {
        return descripcion;}
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;}

        
}
