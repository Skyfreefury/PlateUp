package com.Mogena.Service;

import com.Mogena.Model.Pedido;
import com.Mogena.Repository.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    public List<Pedido> obtenerTodos() {
        return pedidoDAO.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoDAO.findById(id).orElse(null);
    }

    public Pedido guardarPedido(Pedido pedido) {
        return pedidoDAO.save(pedido);
    }

    public void borrarPedido(Long id) {
        pedidoDAO.deleteById(id);
    }

    public List<Pedido> obtenerPedidosPorSesion(Long sesionId) {
        return pedidoDAO.findBySesionId(sesionId);
    }

    // NUEVO: Método para el contador de tickets
    public Optional<Pedido> obtenerUltimoDeSesion(Long sesionId) {
        return pedidoDAO.findTopBySesionIdOrderByNumeroTicketDesc(sesionId);
    }
}