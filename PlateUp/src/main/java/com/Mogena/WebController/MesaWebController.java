package com.Mogena.WebController;

import com.Mogena.Model.Mesa;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador web para la gestión de mesas del restaurante.
 * Cubre el CRUD completo: listar, crear, editar, guardar y borrar.
 */
@Controller
@RequestMapping("/mesas")
public class MesaWebController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.obtenerTodas());
        return "mesas";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesa-form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMesa(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.obtenerPorId(id);
        if (mesa != null) {
            model.addAttribute("mesa", mesa);
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
