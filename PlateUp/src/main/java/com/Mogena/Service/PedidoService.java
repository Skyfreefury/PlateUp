package com.Mogena.Service;

import com.Mogena.Model.Pedido;
import com.Mogena.Repository.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de pedidos (cuentas de mesa).
 * Actúa como intermediario entre el controlador y el repositorio JPA,
 * aislando la lógica de negocio del acceso a datos.
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    /** Devuelve todos los pedidos almacenados en la base de datos. */
    public List<Pedido> obtenerTodos() {
        return pedidoDAO.findAll();
    }

    /** Devuelve el pedido con el ID indicado, o {@code null} si no existe. */
    public Pedido obtenerPorId(Long id) {
        return pedidoDAO.findById(id).orElse(null);
    }

    /** Persiste un pedido nuevo o actualiza uno existente. Devuelve {@code null} si el pedido es nulo. */
    public Pedido guardarPedido(Pedido pedido) {
        if (pedido == null) return null;
        return pedidoDAO.save(pedido);
    }

    /** Elimina el pedido con el ID indicado. No hace nada si no existe. */
    public void borrarPedido(Long id) {
        if (pedidoDAO.existsById(id)) {
            pedidoDAO.deleteById(id);
        }
    }

    /** Devuelve todos los pedidos asociados a una sesión de caja concreta. */
    public List<Pedido> obtenerPedidosPorSesion(Long sesionId) {
        return pedidoDAO.findBySesionId(sesionId);
    }

    /**
     * Devuelve el pedido con el número de ticket más alto dentro de una sesión.
     * Se usa para calcular el número de ticket correlativo del siguiente pedido.
     */
    public Optional<Pedido> obtenerUltimoDeSesion(Long sesionId) {
        return pedidoDAO.findTopBySesionIdOrderByNumeroTicketDesc(sesionId);
    }
}
