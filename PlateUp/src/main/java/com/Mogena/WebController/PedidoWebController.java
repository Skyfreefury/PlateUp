package com.Mogena.WebController;

import com.Mogena.Model.Pedido;
import com.Mogena.Service.PedidoService;
import com.Mogena.Service.MesaService;
import com.Mogena.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
public class PedidoWebController {

    @Autowired
    private PedidoService pedidoService;

    // Inyectamos los otros servicios para cargar los selects
    @Autowired
    private MesaService mesaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.obtenerTodos());
        return "pedidos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoPedido(Model model) {
        model.addAttribute("pedido", new Pedido());
        // Pasamos las listas al HTML para rellenar los selectores
        model.addAttribute("mesas", mesaService.obtenerTodas());
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "pedido-form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarPedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        if (pedido != null) {
            model.addAttribute("pedido", pedido);
            // También pasamos las listas aquí por si quieren cambiar de mesa/cliente
            model.addAttribute("mesas", mesaService.obtenerTodas());
            model.addAttribute("clientes", clienteService.obtenerTodos());
            return "pedido-form";
        }
        return "redirect:/pedidos?error=true";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedido") Pedido pedido) {
        
        // Ponemos la fecha si es una cuenta nueva
        if (pedido.getFechaHora() == null) {
            pedido.setFechaHora(java.time.LocalDateTime.now());
        }
        // Si el total viene vacío (null), lo ponemos a 0.0 para que no salte el error de la BD
        if (pedido.getTotal() == null) {
            pedido.setTotal(0.0);
        }

        pedidoService.guardarPedido(pedido);
        return "redirect:/pedidos?exito=true";
    }

    @GetMapping("/borrar/{id}")
    public String borrarPedido(@PathVariable Long id) {
        pedidoService.borrarPedido(id);
        return "redirect:/pedidos?exitoBorrado=true";
    }
}