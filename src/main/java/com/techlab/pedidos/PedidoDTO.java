package com.techlab.pedidos.dto;

import java.util.List;

public class PedidoDTO {

    private Long usuarioId;
    private List<ItemPedidoDTO> itemsPedido;

    public PedidoDTO() {}

    public PedidoDTO(Long usuarioId, List<ItemPedidoDTO> itemsPedido) {
        this.usuarioId = usuarioId;
        this.itemsPedido = itemsPedido;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<ItemPedidoDTO> getItemsPedido() {
        return itemsPedido;
    }

    public void setItemsPedido(List<ItemPedidoDTO> itemsPedido) {
        this.itemsPedido = itemsPedido;
    }
}

