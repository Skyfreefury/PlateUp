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

/**
 * Controlador web para la gestión de la sesión de caja (apertura y cierre de turno).
 * Calcula los totales de ingresos por efectivo, tarjeta y el efectivo físico en caja.
 */
@Controller
@RequestMapping("/cierre")
public class CierreWebController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private SesionService sesionService;

    /**
     * Muestra el resumen de caja de la sesión activa:
     * total de ingresos, desglose efectivo/tarjeta, fondo de caja y platos vendidos.
     * Si no hay sesión abierta, muestra la pantalla de apertura.
     */
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

        // Solo contabilizamos los pedidos CERRADOS (cobrados) de esta sesión
        List<Pedido> pedidosPagados = pedidoService.obtenerTodos().stream()
                .filter(p -> p.getSesionId() != null && p.getSesionId().equals(sesionActiva.getId()))
                .filter(p -> "CERRADA".equals(p.getEstado()))
                .toList();

        double totalCaja      = pedidosPagados.stream().mapToDouble(p -> p.getTotal()       != null ? p.getTotal()       : 0.0).sum();
        double totalEfectivo  = pedidosPagados.stream().mapToDouble(p -> p.getPagoEfectivo() != null ? p.getPagoEfectivo() : 0.0).sum();
        double totalTarjeta   = pedidosPagados.stream().mapToDouble(p -> p.getPagoTarjeta()  != null ? p.getPagoTarjeta()  : 0.0).sum();
        // Efectivo en caja = fondo inicial + cobros en metálico durante el turno
        double efectivoEnCaja = (sesionActiva.getMontoInicial() != null ? sesionActiva.getMontoInicial() : 0.0) + totalEfectivo;

        // Conteo de platos vendidos para el resumen de cocina
        Map<String, Integer> platosVendidos = new HashMap<>();
        for (Pedido p : pedidosPagados) {
            List<Comanda> comandas = comandaService.obtenerPorPedidoId(p.getId());
            for (Comanda c : comandas) {
                if (c.getNombrePlato() == null || c.getCantidad() == null) continue;
                platosVendidos.put(c.getNombrePlato(),
                    platosVendidos.getOrDefault(c.getNombrePlato(), 0) + c.getCantidad());
            }
        }

        String fechaFormateada = sesionActiva.getApertura().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        model.addAttribute("mensajeEstado", "Sesión iniciada el: " + fechaFormateada);
        model.addAttribute("totalCaja", totalCaja);
        model.addAttribute("totalEfectivo", totalEfectivo);
        model.addAttribute("totalTarjeta", totalTarjeta);
        model.addAttribute("efectivoEnCaja", efectivoEnCaja);
        model.addAttribute("fondoInicial", sesionActiva.getMontoInicial() != null ? sesionActiva.getMontoInicial() : 0.0);
        model.addAttribute("totalTickets", pedidosPagados.size());
        model.addAttribute("platosVendidos", platosVendidos);
        model.addAttribute("sesionActivaId", sesionActiva.getId());

        return "cierre";
    }

    /** Cierra la sesión de caja activa registrando la hora de cierre. */
    @PostMapping("/cerrar")
    public String finalizarSesion(@RequestParam("sesionId") Long sesionId) {
        Sesion s = sesionService.obtenerSesionActiva();
        if (s != null && s.getId().equals(sesionId)) {
            sesionService.cerrarSesion(s);
            return "redirect:/cierre?exitoCierre=true";
        }
        return "redirect:/cierre?error=true";
    }

    /**
     * Abre una nueva sesión de caja con el fondo inicial indicado.
     * Si ya hay una sesión activa, redirige con un aviso de error.
     */
    @PostMapping("/abrir")
    public String iniciarSesion(@RequestParam(value = "montoInicial", defaultValue = "0.0") Double montoInicial) {
        Sesion activa = sesionService.obtenerSesionActiva();
        if (activa == null) {
            sesionService.abrirSesion(montoInicial);
            return "redirect:/pedidos?exitoApertura=true";
        }
        return "redirect:/cierre?error=ya_abierta";
    }
}
