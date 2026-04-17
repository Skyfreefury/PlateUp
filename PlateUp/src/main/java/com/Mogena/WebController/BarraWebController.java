package com.Mogena.WebController;

import com.Mogena.Model.Comanda;
import com.Mogena.Model.Mesa;
import com.Mogena.Model.Pedido;
import com.Mogena.Model.Sesion;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.MesaService;
import com.Mogena.Service.PedidoService;
import com.Mogena.Service.SesionService;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controlador web para el panel de barra.
 * Muestra únicamente las bebidas (tipoComandaId = 4) de la sesión activa.
 * El marcado como "listo" se gestiona desde {@link ComandaWebController}.
 */
@Controller
@RequestMapping("/barra")
public class BarraWebController {

    @Autowired
    private SesionService sesionService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private MesaService mesaService;

    /**
     * Muestra el panel de barra con las bebidas pendientes y listas de la sesión activa.
     * Si no hay sesión abierta, el panel aparece vacío.
     */
    @GetMapping
    public String verPanelBarra(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();

        if (activa == null) {
            model.addAttribute("comandas", java.util.List.of());
            return "barra";
        }

        // Filtramos por pedidos de la sesión activa para no mezclar turnos
        List<Long> idsPedidosActivos = pedidoService.obtenerPedidosPorSesion(activa.getId())
                .stream().map(Pedido::getId).toList();

        List<Comanda> bebidasActivas = comandaService.obtenerTodas().stream()
                .filter(c -> idsPedidosActivos.contains(c.getPedidoId()))
                .toList();

        model.addAttribute("comandas", bebidasActivas);

        Map<Long, Pedido> pedidosMap = pedidoService.obtenerPedidosPorSesion(activa.getId())
            .stream().collect(Collectors.toMap(Pedido::getId, p -> p));
        model.addAttribute("pedidosMap", pedidosMap);

        Map<Long, Mesa> mesaMap = mesaService.obtenerTodas()
            .stream().collect(Collectors.toMap(Mesa::getId, m -> m));
        model.addAttribute("mesaMap", mesaMap);

        return "barra";
    }
}
