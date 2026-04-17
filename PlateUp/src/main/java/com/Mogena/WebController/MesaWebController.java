package com.Mogena.WebController;

import com.Mogena.Model.Cliente;
import com.Mogena.Model.Mesa;
import com.Mogena.Service.ClienteService;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
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
        model.addAttribute("mesas", mesaService.obtenerTodas());
        Map<Long, Cliente> clientesMap = clienteService.obtenerTodos()
            .stream().collect(Collectors.toMap(Cliente::getId, c -> c));
        model.addAttribute("clientesMap", clientesMap);
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
    public String guardarMesa(@ModelAttribute("mesa") Mesa mesa) {
        mesaService.guardarMesa(mesa);
        return "redirect:/mesas?exito=true";
    }

    @GetMapping("/borrar/{id}")
    public String borrarMesa(@PathVariable Long id) {
        mesaService.borrarMesa(id);
        return "redirect:/mesas?exitoBorrado=true";
    }
}
