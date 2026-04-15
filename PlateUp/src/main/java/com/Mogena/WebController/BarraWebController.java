package com.Mogena.WebController;

import com.Mogena.Model.Comanda;
import com.Mogena.Model.Pedido;
import com.Mogena.Model.Sesion;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.PedidoService;
import com.Mogena.Service.SesionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/barra")
public class BarraWebController {

    @Autowired
    private SesionService sesionService;
    
    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private ComandaService comandaService;

    // ==========================================
    // LISTAR SOLO BEBIDAS DE LA SESIÓN ACTUAL
    // ==========================================
    @GetMapping
    public String verPanelBarra(Model model) {
        Sesion activa = sesionService.obtenerSesionActiva();
        
        if (activa == null) {
            model.addAttribute("comandas", java.util.List.of());
            return "barra";
        }

        // Filtramos para obtener solo bebidas de esta caja
        List<Long> idsPedidosActivos = pedidoService.obtenerPedidosPorSesion(activa.getId())
                .stream().map(Pedido::getId).toList();

        List<Comanda> bebidasActivas = comandaService.obtenerTodas().stream()
                .filter(c -> idsPedidosActivos.contains(c.getPedidoId()))
                .toList();

        model.addAttribute("comandas", bebidasActivas);
        return "barra";
    }
}