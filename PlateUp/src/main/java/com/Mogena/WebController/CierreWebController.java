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
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cierre")
public class CierreWebController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private SesionService sesionService;

    // 1. VER EL RESUMEN DE LA SESIÓN ACTUAL
    @GetMapping
    public String verCierreDeCaja(Model model) {
        Sesion sesionActiva = sesionService.obtenerSesionActiva();
        
        if (sesionActiva == null) {
            model.addAttribute("mensajeEstado", "No hay ninguna sesión de caja abierta.");
            model.addAttribute("totalCaja", 0.0);
            model.addAttribute("totalTickets", 0);
            model.addAttribute("platosVendidos", new HashMap<>());
            model.addAttribute("sesionActivaId", null);
            return "cierre";
        }

        // Buscamos solo los pedidos CERRADOS que pertenecen a esta sesión
        List<Pedido> pedidosPagados = pedidoService.obtenerTodos().stream()
                .filter(p -> p.getSesionId() != null && p.getSesionId().equals(sesionActiva.getId()))
                .filter(p -> "CERRADA".equals(p.getEstado()))
                .toList();

        double totalCaja = pedidosPagados.stream().mapToDouble(Pedido::getTotal).sum();

        // Conteo de platos vendidos en esta sesión
        Map<String, Integer> platosVendidos = new HashMap<>();
        for (Pedido p : pedidosPagados) {
            List<Comanda> comandas = comandaService.obtenerPorPedidoId(p.getId());
            for (Comanda c : comandas) {
                platosVendidos.put(c.getNombrePlato(), 
                    platosVendidos.getOrDefault(c.getNombrePlato(), 0) + c.getCantidad());
            }
        }

        String fechaFormateada = sesionActiva.getApertura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        model.addAttribute("mensajeEstado", "Sesión iniciada el: " + fechaFormateada);
        model.addAttribute("totalCaja", totalCaja);
        model.addAttribute("totalTickets", pedidosPagados.size());
        model.addAttribute("platosVendidos", platosVendidos);
        model.addAttribute("sesionActivaId", sesionActiva.getId());

        return "cierre";
    }

    // 2. ACCIÓN DE CERRAR LA SESIÓN
    @PostMapping("/cerrar")
    public String finalizarSesion(@RequestParam("sesionId") Long sesionId) {
        Sesion s = sesionService.obtenerSesionActiva();
        if (s != null && s.getId().equals(sesionId)) {
            sesionService.cerrarSesion(s);
            return "redirect:/cierre?exitoCierre=true";
        }
        return "redirect:/cierre?error=true";
    }
    // ==========================================
    // 3. ACCIÓN DE ABRIR NUEVA SESIÓN
    // ==========================================
    @PostMapping("/abrir")
    public String iniciarSesion(@RequestParam(value = "montoInicial", defaultValue = "0.0") Double montoInicial) {
        
        // Comprobamos si ya hay una activa por seguridad
        Sesion activa = sesionService.obtenerSesionActiva();
        
        if (activa == null) {
            sesionService.abrirSesion(montoInicial);
            return "redirect:/pedidos?exitoApertura=true";
        }
        
        return "redirect:/cierre?error=ya_abierta";
    }
}