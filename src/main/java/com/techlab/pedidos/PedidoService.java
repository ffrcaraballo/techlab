package com.techlab.pedidos;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.dto.ItemPedidoDTO;
import com.techlab.pedidos.dto.PedidoDTO;
import com.techlab.productos.Producto;
import com.techlab.repository.ProductoRepository;
import com.techlab.pedidos.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        List<LineaPedido> lineas = new ArrayList<>();
        double total = 0.0;

        for (ItemPedidoDTO itemDTO : pedidoDTO.getItemsPedido()) {
            Producto producto = productoRepository.findById(itemDTO.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado ID: " + itemDTO.getProductoId()));

            if (producto.getStock() < itemDTO.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - itemDTO.getCantidad());
            productoRepository.save(producto);

            LineaPedido linea = new LineaPedido();
            linea.setProducto(producto);
            linea.setCantidad(itemDTO.getCantidad());
            lineas.add(linea);

            total += producto.getPrecio() * itemDTO.getCantidad();
        }

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(pedidoDTO.getUsuarioId());
        pedido.setLineas(lineas);
        pedido.setCostoTotal(total);
        pedido.setEstado("pendiente");

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }
}
