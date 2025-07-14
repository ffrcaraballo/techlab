// Clase Pedido
package com.techlab.pedidos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private Long id;
    private Long usuarioId;
    private List<LineaPedido> lineas;
    private LocalDateTime fechaCreacion;
    private String estado; // Ej: pendiente, confirmado, enviado, entregado, cancelado

    public Pedido() {
        this.lineas = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "pendiente";
    }

    public Pedido(Long id, Long usuarioId, List<LineaPedido> lineas) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.lineas = lineas;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = "pendiente";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double calcularTotal() {
        return lineas.stream()
                .mapToDouble(LineaPedido::calcularSubtotal)
                .sum();
    }
}
