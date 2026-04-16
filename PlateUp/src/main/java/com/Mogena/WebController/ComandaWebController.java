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

/**
 * Controlador web para la gestión de comandas (platos y bebidas pedidos por las mesas).
 * Sirve tanto al panel de cocina como al de barra, filtrando los productos según el origen.
 */
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

    /**
     * Muestra el panel de cocina con los platos de la sesión activa.
     * Si no hay sesión abierta, el panel aparece vacío.
     */
    @GetMapping
    public String verComandas(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();

        if (activa == null) {
            model.addAttribute("comandas", java.util.List.of());
            return "comandas";
        }

        // Filtramos por pedidos de la sesión activa para no mezclar turnos
        List<Long> idsPedidosActivos = pedidoService.obtenerPedidosPorSesion(activa.getId())
                .stream().map(Pedido::getId).toList();

        List<Comanda> comandasActivas = comandaService.obtenerTodas().stream()
                .filter(c -> idsPedidosActivos.contains(c.getPedidoId()))
                .toList();

        model.addAttribute("comandas", comandasActivas);
        return "comandas";
    }

    /**
     * Muestra el formulario para marchar un nuevo plato o bebida.
     * El parámetro {@code from} controla qué productos se ofrecen:
     * <ul>
     *   <li>{@code barra} — solo bebidas (tipoProductoId = 4)</li>
     *   <li>{@code cocina} — platos sin bebidas</li>
     *   <li>cualquier otro — todos los productos</li>
     * </ul>
     */
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model,
            @RequestParam(required = false, defaultValue = "general") String from) {
        Sesion activa = sesionService.obtenerSesionActiva();
        if (activa == null) {
            return "redirect:/cierre";
        }

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

    /** Persiste la comanda y recalcula el total del pedido al que pertenece. */
    @PostMapping("/guardar")
    public String guardarComanda(@ModelAttribute("comanda") Comanda comanda) {
        if (comanda.getId() == null) {
            comanda.setEstado("PENDIENTE");
            comanda.setCreadoEn(LocalDateTime.now());
        }
        comandaService.guardarComanda(comanda);
        recalcularTotalPedido(comanda.getPedidoId());

        // Redirigir a la pantalla correspondiente según el destino de preparación
        if (comanda.getTipoComandaId() != null && comanda.getTipoComandaId() == 4) {
            return "redirect:/barra";
        }
        return "redirect:/comandas";
    }

    /** Marca una comanda como LISTO y redirige al panel correspondiente. */
    @GetMapping("/listo/{id}")
    public String marcarComandaLista(@PathVariable Long id) {
        Comanda comanda = comandaService.obtenerPorId(id);
        if (comanda != null) {
            comanda.setEstado("LISTO");
            comandaService.guardarComanda(comanda);

            if (comanda.getTipoComandaId() != null && comanda.getTipoComandaId() == 4) {
                return "redirect:/barra";
            }
        }
        return "redirect:/comandas";
    }

    /**
     * Anula (elimina) una comanda y recalcula el total del pedido afectado.
     * Redirige al panel de barra si era una bebida, o al de cocina en caso contrario.
     */
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

        if (pedidoId != null) {
            recalcularTotalPedido(pedidoId);
        }

        if (tipo != null && tipo == 4) {
            return "redirect:/barra";
        }
        return "redirect:/comandas";
    }

    /**
     * Recalcula el importe total de un pedido sumando precio × cantidad de cada comanda.
     * Persiste el nuevo total directamente en el pedido para mantenerlo actualizado.
     *
     * @param pedidoId ID del pedido cuyo total se debe recalcular
     */
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
