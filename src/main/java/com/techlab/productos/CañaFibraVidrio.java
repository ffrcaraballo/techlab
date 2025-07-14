// Clase CañaFibraVidrio
package com.techlab.productos;

public class CañaFibraVidrio extends Producto {

    private boolean flexible;  // atributo específico

    public CañaFibraVidrio() {
        super();
    }

    public CañaFibraVidrio(Long id, String nombre, String descripcion, double precio, String imagenUrl, int stock, boolean flexible) {
        super(id, nombre, descripcion, precio, imagenUrl, stock, "Fibra de Vidrio");
        this.flexible = flexible;
    }

    public boolean isFlexible() {
        return flexible;
    }

    public void setFlexible(boolean flexible) {
        this.flexible = flexible;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + (flexible ? " (alta flexibilidad)" : " (poca flexibilidad)");
    }
}
