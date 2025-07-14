// Clase CañaGrafito
package com.techlab.productos;

public class CañaGrafito extends Producto {

    private double pesoEnGramos;  // atributo específico

    public CañaGrafito() {
        super();
    }

    public CañaGrafito(Long id, String nombre, String descripcion, double precio, String imagenUrl, int stock, double pesoEnGramos) {
        super(id, nombre, descripcion, precio, imagenUrl, stock, "Grafito");
        this.pesoEnGramos = pesoEnGramos;
    }

    public double getPesoEnGramos() {
        return pesoEnGramos;
    }

    public void setPesoEnGramos(double pesoEnGramos) {
        this.pesoEnGramos = pesoEnGramos;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " (peso: " + pesoEnGramos + "g)";
    }
}
