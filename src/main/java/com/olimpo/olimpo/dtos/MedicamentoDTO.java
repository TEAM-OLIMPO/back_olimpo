package com.olimpo.olimpo.dtos;

public class MedicamentoDTO {
    private String producto;
    private String principio_activo;
    private String forma_farmaceutica;
    private String unidad_medida;
    private String valor;
    
    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }
    public String getPrincipio_activo() {
        return principio_activo;}

    public void setPrincipio_activo(String principio_activo) {
        this.principio_activo = principio_activo;
    }
    public String getForma_farmaceutica() {
        return forma_farmaceutica;
    }
    public void setForma_farmaceutica(String forma_farmaceutica) {
        this.forma_farmaceutica = forma_farmaceutica;
    }
    public String getUnidad_medida() {
        return unidad_medida;}
    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;}
        public String getValor() {
            return valor;}
    public void setValor(String valor) {
        this.valor = valor;}   
}
