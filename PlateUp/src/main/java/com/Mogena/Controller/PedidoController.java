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
        // Guardamos el pedido y comprobamos si el resultado NO es nulo
        Pedido guardado = pedidoService.guardarPedido(pedido);
        return (guardado != null) ? "Guardado exitosamente" : "Error al guardar";
    }

    @PutMapping
    public String actualizar(@RequestBody Pedido pedido) {
        // En Spring Data JPA, "save" (guardarPedido) se usa tanto para crear como para actualizar.
        // Si el objeto ya tiene un ID, lo actualiza automáticamente.
        Pedido actualizado = pedidoService.guardarPedido(pedido);
        return (actualizado != null) ? "Actualizado exitosamente" : "Error al actualizar";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        // Como borrarPedido es "void" (no devuelve nada), primero comprobamos que exista
        Pedido existente = pedidoService.obtenerPorId(id);
        
        if (existente != null) {
            pedidoService.borrarPedido(id);
            return "Borrado exitosamente";
        }
        
        return "Error: El pedido no existe o ya fue borrado";
    }
}