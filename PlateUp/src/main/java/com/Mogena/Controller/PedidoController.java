/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.Pedido;
import com.Mogena.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> obtenerTodos() {
        return pedidoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPorId(id);
    }

    @PostMapping
    public String guardar(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido) ? "Guardado" : "Error";
    }

    @PutMapping
    public String actualizar(@RequestBody Pedido pedido) {
        return pedidoService.actualizarPedido(pedido) ? "Actualizado" : "Error";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return pedidoService.borrarPedido(id) ? "Borrado" : "Error";
    }
}
