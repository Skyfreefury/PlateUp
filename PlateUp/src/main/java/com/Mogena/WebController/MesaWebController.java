package com.Mogena.WebController;

import com.Mogena.Model.Cliente;
import com.Mogena.Model.Mesa;
import com.Mogena.Service.ClienteService;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador web para la gestión de mesas del restaurante.
 * Cubre el CRUD completo: listar, crear, editar, guardar y borrar.
 */
@Controller
@RequestMapping("/mesas")
public class MesaWebController {

    @Autowired
    private MesaService mesaService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarMesas(Model model) {
        List<Mesa> mesas = mesaService.obtenerTodas();
        model.addAttribute("mesas", mesas);

        Map<Long, Cliente> clientesMap = clienteService.obtenerTodos()
            .stream().collect(Collectors.toMap(Cliente::getId, c -> c));
        model.addAttribute("clientesMap", clientesMap);

        LocalDate hoy = LocalDate.now();
        Map<LocalDate, List<Mesa>> reservasPorDia = mesas.stream()
            .filter(m -> "RESERVADA".equals(m.getEstado())
                      && m.getFechaReserva() != null
                      && !m.getFechaReserva().isBefore(hoy))
            .sorted(Comparator.comparing(Mesa::getFechaReserva)
                              .thenComparing(m -> m.getHoraReserva() != null ? m.getHoraReserva() : ""))
            .collect(Collectors.groupingBy(Mesa::getFechaReserva, LinkedHashMap::new, Collectors.toList()));
        model.addAttribute("reservasPorDia", reservasPorDia);
        model.addAttribute("hoy", hoy);

        return "mesas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "mesa-form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMesa(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.obtenerPorId(id);
        if (mesa != null) {
            model.addAttribute("mesa", mesa);
            model.addAttribute("clientes", clienteService.obtenerTodos());
            return "mesa-form";
        }
        return "redirect:/mesas?error=true";
    }

    @PostMapping("/guardar")
    public String guardarMesa(@ModelAttribute("mesa") Mesa mesa, Model model) {
        try {
            mesaService.guardarMesa(mesa);
            return "redirect:/mesas?exito=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("clientes", clienteService.obtenerTodos());
            return "mesa-form";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarMesa(@PathVariable Long id) {
        mesaService.borrarMesa(id);
        return "redirect:/mesas?exitoBorrado=true";
    }
}
