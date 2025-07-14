// Controlador de pedidos
package com.techlab.pedidos;

import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.productos.Producto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final Map<Long, Pedido> pedidos = new HashMap<>();
    private final Map<Long, Producto> baseDeProductos = new HashMap<>(); // Simula productos existentes
    private final AtomicLong idGenerador = new AtomicLong(1);

    // Simular historial por usuario
    private final Map<Long, List<Pedido>> historialPorUsuario = new HashMap<>();

    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        List<LineaPedido> lineas = new ArrayList<>();

        // Validar stock
        for (ItemPedidoDTO item : pedidoDTO.getItemsPedido()) {
            Producto prod = baseDeProductos.get(item.getProductoId());

            if (prod == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Producto con ID " + item.getProductoId() + " no encontrado.");
            }

            if (prod.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para producto: " + prod.getNombre());
            }

            lineas.add(new LineaPedido(prod, item.getCantidad()));
        }

        // Descontar stock
        for (ItemPedidoDTO item : pedidoDTO.getItemsPedido()) {
            Producto prod = baseDeProductos.get(item.getProductoId());
            prod.setStock(prod.getStock() - item.getCantidad());
        }

        // Crear pedido
        Pedido nuevo = new Pedido();
        nuevo.setId(idGenerador.getAndIncrement());
        nuevo.setUsuarioId(pedidoDTO.getUsuarioId());
        nuevo.setLineas(lineas);

        pedidos.put(nuevo.getId(), nuevo);

        historialPorUsuario.computeIfAbsent(nuevo.getUsuarioId(), k -> new ArrayList<>()).add(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarPedidosPorUsuario(@PathVariable Long usuarioId) {
        List<Pedido> historial = historialPorUsuario.get(usuarioId);
        if (historial == null || historial.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron pedidos para el usuario " + usuarioId);
        }
        return ResponseEntity.ok(historial);
    }

    // Método para simular carga de productos (puede eliminarse si se usa una base real)
    @PostMapping("/simular-catalogo")
    public ResponseEntity<?> cargarProductosSimulados(@RequestBody List<Producto> productos) {
        for (Producto p : productos) {
            baseDeProductos.put(p.getId(), p);
        }
        return ResponseEntity.ok("Productos simulados cargados.");
    }

    // Manejo de error personalizado
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<String> manejarStockInsuficiente(StockInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("⚠️ " + ex.getMessage());
    }
}
