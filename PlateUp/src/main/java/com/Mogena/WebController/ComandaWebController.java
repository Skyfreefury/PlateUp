package com.Mogena.WebController;

import com.Mogena.Model.Comanda;
import com.Mogena.Model.Pedido;
import com.Mogena.Model.Sesion;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.PedidoService;
import com.Mogena.Service.ProductoService;
import com.Mogena.Service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/comandas")
public class ComandaWebController {

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private SesionService sesionService;

    // =========================================================
    // 1. PANEL DE COCINA (Lista solo los platos de esta sesión)
    // =========================================================
    @GetMapping
    public String verComandas(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();
        
        // Si no hay caja abierta, la pantalla de cocina sale limpia
        if (activa == null) {
            model.addAttribute("comandas", java.util.List.of());
            return "comandas";
        }

        // Buscamos qué tickets (pedidos) pertenecen a esta sesión
        List<Long> idsPedidosActivos = pedidoService.obtenerPedidosPorSesion(activa.getId())
                .stream().map(Pedido::getId).toList();

        // Filtramos los platos para que solo salgan los de esos tickets activos
        List<Comanda> comandasActivas = comandaService.obtenerTodas().stream()
                .filter(c -> idsPedidosActivos.contains(c.getPedidoId()))
                .toList();

        model.addAttribute("comandas", comandasActivas);
        return "comandas";
    }

    // =========================================================
    // 2. FORMULARIO PARA MARCHAR PLATO / BEBIDA
    // =========================================================
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model,
            @RequestParam(required = false, defaultValue = "general") String from) {
        Sesion activa = sesionService.obtenerSesionActiva();

        // No dejamos pedir platos si la caja está cerrada
        if (activa == null) {
            return "redirect:/cierre";
        }

        // Filtrar productos según el origen: barra → solo bebidas; cocina → sin bebidas
        List<com.Mogena.Model.Producto> productos = productoService.obtenerTodos();
        if ("barra".equals(from)) {
            productos = productos.stream()
                .filter(p -> p.getTipoProductoId() != null && p.getTipoProductoId() == 4)
                .toList();
        } else if ("cocina".equals(from)) {
            productos = productos.stream()
                .filter(p -> p.getTipoProductoId() == null || p.getTipoProductoId() != 4)
                .toList();
        }

        model.addAttribute("comanda", new Comanda());
        model.addAttribute("pedidos", pedidoService.obtenerTodos());
        model.addAttribute("productos", productos);
        model.addAttribute("from", from);

        return "comanda-form";
    }

    // =========================================================
    // 3. GUARDAR COMANDA EN BASE DE DATOS
    // =========================================================
    @PostMapping("/guardar")
    public String guardarComanda(@ModelAttribute("comanda") Comanda comanda) {
        if (comanda.getId() == null) {
            comanda.setEstado("PENDIENTE");
            comanda.setCreadoEn(LocalDateTime.now());
        }
        comandaService.guardarComanda(comanda);

        // Recalcular el total del pedido tras añadir/editar la comanda
        recalcularTotalPedido(comanda.getPedidoId());

        // Si es una bebida (ID 4), redirigimos a la barra para no marear al camarero
        if (comanda.getTipoComandaId() != null && comanda.getTipoComandaId() == 4) {
            return "redirect:/barra";
        }

        // Si es comida, redirigimos a cocina
        return "redirect:/comandas";
    }

    // =========================================================
    // 4. MARCAR COMO LISTO (Botón verde Check)
    // =========================================================
    @GetMapping("/listo/{id}")
    public String marcarComandaLista(@PathVariable Long id) {
        Comanda comanda = comandaService.obtenerPorId(id);
        if (comanda != null) {
            comanda.setEstado("LISTO");
            comandaService.guardarComanda(comanda); 
            
            // Redirigir a barra o cocina dependiendo de quién pulsó el botón
            if (comanda.getTipoComandaId() != null && comanda.getTipoComandaId() == 4) {
                return "redirect:/barra";
            }
        }
        return "redirect:/comandas";
    }

    // =========================================================
    // 5. BORRAR COMANDA (Anulación real por error)
    // =========================================================
    @GetMapping("/borrar/{id}")
    public String borrarComanda(@PathVariable Long id) {
        Comanda comanda = comandaService.obtenerPorId(id);
        Long tipo = null;
        Long pedidoId = null;

        if (comanda != null) {
            tipo = comanda.getTipoComandaId();
            pedidoId = comanda.getPedidoId();
            comandaService.borrarComanda(id);
        }

        // Recalcular el total del pedido tras eliminar la comanda
        if (pedidoId != null) {
            recalcularTotalPedido(pedidoId);
        }

        // Redirigimos a la pantalla correcta tras anular
        if (tipo != null && tipo == 4) {
            return "redirect:/barra";
        }
        return "redirect:/comandas";
    }

    // =========================================================
    // HELPER: Recalcula y persiste el total de un pedido
    // =========================================================
    private void recalcularTotalPedido(Long pedidoId) {
        if (pedidoId == null) return;
        Pedido pedido = pedidoService.obtenerPorId(pedidoId);
        if (pedido == null) return;

        double total = comandaService.obtenerPorPedidoId(pedidoId).stream()
            .mapToDouble(c -> {
                if (c.getNombrePlato() == null || c.getCantidad() == null) return 0.0;
                var producto = productoService.obtenerPorNombre(c.getNombrePlato());
                if (producto == null || producto.getPrecio() == null) return 0.0;
                return producto.getPrecio() * c.getCantidad();
            })
            .sum();

        pedido.setTotal(total);
        pedidoService.guardarPedido(pedido);
    }
}