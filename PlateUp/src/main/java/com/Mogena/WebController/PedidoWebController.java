/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.WebController;

import com.Mogena.Model.Pedido;
import com.Mogena.Model.Comanda;
import com.Mogena.Model.Producto;
import com.Mogena.Service.PedidoService;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoWebController {

    @Autowired
    private PedidoService pedidoService;

    // 🚨 Inyectamos los otros servicios para que el Pedido pueda "cotillear" los precios
    @Autowired
    private ComandaService comandaService;

    @Autowired
    private ProductoService productoService;

    // 1. LISTAR PEDIDOS Y CALCULAR TOTALES AUTOMÁTICAMENTE
    @GetMapping
    public String listarPedidos(Model model) {
        try {
            List<Pedido> pedidos = pedidoService.obtenerTodos();
            List<Comanda> comandas = comandaService.obtenerTodas();
            List<Producto> carta = productoService.obtenerTodos();

            // 🧮 LÓGICA MÁGICA: Calcular el total de la cuenta de cada mesa
            for (Pedido pedido : pedidos) {
                double totalCalculado = 0.0;

                for (Comanda c : comandas) {
                    // Si la comanda pertenece a esta cuenta...
                    if (c.getPedidoId() != null && c.getPedidoId().equals(pedido.getId())) {
                        
                        // Buscamos el precio del plato en nuestra Carta
                        for (Producto p : carta) {
                            if (p.getNombre() != null && p.getNombre().equals(c.getNombrePlato())) {
                                // Multiplicamos Precio x Cantidad
                                int cantidad = (c.getCantidad() != null) ? c.getCantidad() : 1;
                                totalCalculado += (p.getPrecio() * cantidad);
                                break; // Plato encontrado, pasamos a la siguiente comanda
                            }
                        }
                    }
                }
                
                // Le ponemos el nuevo total al pedido y lo guardamos en la Base de Datos
                pedido.setTotal(totalCalculado);
                pedidoService.guardarPedido(pedido);
            }

            // Mandamos los datos actualizados al HTML
            model.addAttribute("pedidos", pedidos);
            model.addAttribute("pedido", new Pedido()); 
            
            return "pedidos"; 
        } catch (Exception e) {
            System.out.println("ERROR AL CALCULAR TOTALES: " + e.getMessage());
            return "redirect:/?error=true";
        }
    }

    // 2. GUARDAR / CREAR
    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedido") Pedido pedido) {
        // Si es una mesa nueva, la cuenta empieza a 0€
        if(pedido.getTotal() == null) {
            pedido.setTotal(0.0);
        }
        pedidoService.guardarPedido(pedido);
        return "redirect:/pedidos?exito=true";
    }

    // 3. MOSTRAR FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        if (pedido != null) {
            model.addAttribute("pedido", pedido);
            return "editar-pedido"; 
        }
        return "redirect:/pedidos?error=true";
    }

    // 4. PROCESAR EDICIÓN
    @PostMapping("/editar/{id}")
    public String procesarEdicion(@PathVariable Long id, @ModelAttribute("pedido") Pedido pedido) {
        pedido.setId(id);
        
        // 🚨 Truco vital: Recuperar el total anterior para que no se borre al editar la mesa
        Pedido pedidoExistente = pedidoService.obtenerPorId(id);
        if(pedidoExistente != null && pedidoExistente.getTotal() != null) {
            pedido.setTotal(pedidoExistente.getTotal());
        } else {
            pedido.setTotal(0.0);
        }
        
        pedidoService.guardarPedido(pedido);
        return "redirect:/pedidos?exito=true";
    }

    // 5. BORRAR
    @GetMapping("/borrar/{id}")
    public String borrarPedido(@PathVariable Long id) {
        pedidoService.borrarPedido(id);
        return "redirect:/pedidos?exitoBorrado=true";
    }
}