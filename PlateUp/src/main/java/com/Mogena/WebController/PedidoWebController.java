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

/**
 * Controlador web para la gestión de pedidos (cuentas de mesa).
 * Cubre el ciclo completo: apertura de cuenta, edición, cobro y visualización del ticket.
 * Solo muestra las cuentas de la sesión de caja activa.
 */
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

    /**
     * Muestra la lista de cuentas abiertas y cerradas de la sesión activa.
     * Si no hay sesión abierta, la lista aparece vacía.
     */
    @GetMapping
    public String listarPedidos(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();

        if (activa != null) {
            model.addAttribute("pedidos", pedidoService.obtenerPedidosPorSesion(activa.getId()));
        } else {
            model.addAttribute("pedidos", java.util.List.of());
        }

        return "pedidos";
    }

    /**
     * Muestra el formulario para abrir una nueva cuenta.
     * Redirige a la página de caja si no hay sesión activa.
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();
        if (activa == null) {
            return "redirect:/cierre";
        }

        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaService.obtenerTodas());
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "pedido-form";
    }

    /**
     * Guarda un pedido nuevo o actualiza uno existente.
     * Al crear uno nuevo, asigna automáticamente la sesión activa, la fecha/hora
     * y el número de ticket correlativo dentro de la sesión.
     */
    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedido") Pedido pedido) {
        if (pedido.getId() == null) {
            Sesion activa = sesionService.obtenerSesionActiva();
            if (activa == null) return "redirect:/cierre";

            pedido.setSesionId(activa.getId());

            // Número de ticket correlativo dentro de la sesión
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

    /** Muestra el formulario de edición de una cuenta existente. */
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

    /**
     * Elimina un pedido junto con todas sus comandas asociadas.
     * Las comandas se borran primero para evitar violaciones de integridad referencial.
     */
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

    /** Muestra el formulario de cobro para una cuenta abierta. */
    @GetMapping("/cobrar/{id}")
    public String mostrarFormularioCobro(@PathVariable Long id, Model model) {
        Pedido p = pedidoService.obtenerPorId(id);
        if (p == null || !"ABIERTA".equals(p.getEstado())) {
            return "redirect:/pedidos";
        }
        model.addAttribute("pedido", p);
        return "cobrar-form";
    }

    /**
     * Procesa el cobro de una cuenta registrando los importes por efectivo y tarjeta.
     * Marca la cuenta como CERRADA.
     */
    @PostMapping("/cobrar")
    public String procesarCobro(@RequestParam Long pedidoId,
                                @RequestParam(defaultValue = "0") Double pagoEfectivo,
                                @RequestParam(defaultValue = "0") Double pagoTarjeta) {
        Pedido p = pedidoService.obtenerPorId(pedidoId);
        if (p != null) {
            p.setPagoEfectivo(pagoEfectivo);
            p.setPagoTarjeta(pagoTarjeta);
            p.setEstado("CERRADA");
            // Asegurar que la cuenta queda ligada a la sesión activa si no lo estaba
            if (p.getSesionId() == null) {
                Sesion activa = sesionService.obtenerSesionActiva();
                if (activa != null) p.setSesionId(activa.getId());
            }
            pedidoService.guardarPedido(p);
        }
        return "redirect:/pedidos?cobrado=true";
    }

    /** Muestra el ticket imprimible de una cuenta. */
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
