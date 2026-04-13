package com.Mogena.WebController;

import com.Mogena.Model.Mesa;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mesas")
public class MesaWebController {

    @Autowired
    private MesaService mesaService;

    // 1. Mostrar solo la tabla
    @GetMapping
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.obtenerTodas());
        return "mesas";
    }

    // 2. Mostrar formulario vacío (Nueva Mesa)
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaMesa(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesa-form";
    }

    // 3. Mostrar formulario relleno (Editar Mesa)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarMesa(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.obtenerPorId(id);
        if (mesa != null) {
            model.addAttribute("mesa", mesa);
            return "mesa-form";
        }
        return "redirect:/mesas?error=true";
    }

    // 4. Guardar o Actualizar
    @PostMapping("/guardar")
    public String guardarMesa(@ModelAttribute("mesa") Mesa mesa) {
        mesaService.guardarMesa(mesa);
        return "redirect:/mesas?exito=true";
    }

    // 5. Borrar Mesa
    @GetMapping("/borrar/{id}")
    public String borrarMesa(@PathVariable Long id) {
        mesaService.borrarMesa(id);
        return "redirect:/mesas?exitoBorrado=true";
    }
}