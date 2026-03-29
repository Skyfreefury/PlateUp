/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Pedido;
import com.Mogena.Repository.PedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoDAO pedidoDAO;

    public List<Pedido> obtenerTodos() {
        return pedidoDAO.findAll();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoDAO.findById(id);
    }

    public boolean guardarPedido(Pedido pedido) {
        return pedidoDAO.save(pedido) > 0;
    }

    public boolean actualizarPedido(Pedido pedido) {
        if (pedido.getId() == null) return false;
        return pedidoDAO.update(pedido) > 0;
    }

    public boolean borrarPedido(Long id) {
        return pedidoDAO.delete(id) > 0;
    }
}