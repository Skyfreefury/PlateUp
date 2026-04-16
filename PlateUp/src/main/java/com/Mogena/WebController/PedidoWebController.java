package com.Mogena.WebController;

import com.Mogena.Model.Comanda;
import com.Mogena.Model.Pedido;
import com.Mogena.Model.Sesion;
import com.Mogena.Service.ClienteService;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.MesaService;
import com.Mogena.Service.PedidoService;
import com.Mogena.Service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pedidos")
public class PedidoWebController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private MesaService mesaService;
    
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private SesionService sesionService;

    // ==========================================
    // LISTAR SOLO PEDIDOS DE LA SESIÓN ACTUAL
    // ==========================================
    @GetMapping
    public String listarPedidos(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();
        
        if (activa != null) {
            // Si hay caja abierta, mostramos solo las cuentas de hoy
            model.addAttribute("pedidos", pedidoService.obtenerPedidosPorSesion(activa.getId()));
        } else {
            // Si la caja está cerrada, enviamos una lista vacía para que la pantalla esté limpia
            model.addAttribute("pedidos", java.util.List.of());
        }
        
        return "pedidos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        // Bloqueo: Si no hay caja abierta, no dejamos abrir mesa
        Sesion activa = sesionService.obtenerSesionActiva();
        if (activa == null) {
            return "redirect:/cierre"; // Redirigimos a abrir la caja
        }
        
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaService.obtenerTodas());
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "pedido-form";
    }

    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedido") Pedido pedido) {
        if (pedido.getId() == null) { 
            // ES UN PEDIDO NUEVO
            Sesion activa = sesionService.obtenerSesionActiva();
            if (activa == null) return "redirect:/cierre";
            
            pedido.setSesionId(activa.getId());
            
            // LÓGICA DE NUMERACIÓN DE TICKETS
            Optional<Pedido> ultimo = pedidoService.obtenerUltimoDeSesion(activa.getId());
            if (ultimo.isPresent() && ultimo.get().getNumeroTicket() != null) {
                pedido.setNumeroTicket(ultimo.get().getNumeroTicket() + 1);
            } else {
                pedido.setNumeroTicket(1);
            }
            
            pedido.setEstado("ABIERTA");
            pedido.setFechaHora(LocalDateTime.now());
            pedido.setTotal(0.0);
        }
        
        pedidoService.guardarPedido(pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        if (pedido != null) {
            model.addAttribute("pedido", pedido);
            model.addAttribute("mesas", mesaService.obtenerTodas());
            model.addAttribute("clientes", clienteService.obtenerTodos());
            return "pedido-form";
        }
        return "redirect:/pedidos";
    }

    // BORRADO SEGURO (Borra platos y luego cuenta)
    @GetMapping("/borrar/{id}")
    public String borrarPedido(@PathVariable Long id) {
        List<Comanda> comandas = comandaService.obtenerPorPedidoId(id);
        if (comandas != null) {
            for (Comanda c : comandas) {
                comandaService.borrarComanda(c.getId()); 
            }
        }
        pedidoService.borrarPedido(id);
        return "redirect:/pedidos?borrado=true";
    }

    // MOSTRAR FORMULARIO DE COBRO
    @GetMapping("/cobrar/{id}")
    public String mostrarFormularioCobro(@PathVariable Long id, Model model) {
        Pedido p = pedidoService.obtenerPorId(id);
        if (p == null || !"ABIERTA".equals(p.getEstado())) {
            return "redirect:/pedidos";
        }
        model.addAttribute("pedido", p);
        return "cobrar-form";
    }

    // PROCESAR COBRO (efectivo + tarjeta)
    @PostMapping("/cobrar")
    public String procesarCobro(@RequestParam Long pedidoId,
                                @RequestParam(defaultValue = "0") Double pagoEfectivo,
                                @RequestParam(defaultValue = "0") Double pagoTarjeta) {
        Pedido p = pedidoService.obtenerPorId(pedidoId);
        if (p != null) {
            p.setPagoEfectivo(pagoEfectivo);
            p.setPagoTarjeta(pagoTarjeta);
            p.setEstado("CERRADA");
            if (p.getSesionId() == null) {
                Sesion activa = sesionService.obtenerSesionActiva();
                if (activa != null) p.setSesionId(activa.getId());
            }
            pedidoService.guardarPedido(p);
        }
        return "redirect:/pedidos?cobrado=true";
    }

    // VER EL TICKET
    @GetMapping("/ticket/{id}")
    public String verTicket(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoService.obtenerPorId(id);
        if (pedido != null) {
            List<Comanda> comandas = comandaService.obtenerPorPedidoId(id);
            model.addAttribute("pedido", pedido);
            model.addAttribute("comandas", comandas);
            return "ticket";
        }
        return "redirect:/pedidos";
    }
}