/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.WebController;

import com.Mogena.Model.Mesa;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Importante
import org.springframework.web.bind.annotation.ModelAttribute; // Importante

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MesaWebController {

    @Autowired
    private MesaService mesaService;

    @GetMapping("/mesas")
    public String mostrarPaginaMesas(Model model) {
        List<Mesa> listaMesas = mesaService.obtenerTodas();
        model.addAttribute("mesas", listaMesas);
        return "mesas";
    }

    // 🔴 NUEVO MÉTODO: Atrapa el formulario y guarda la mesa
   @PostMapping("/mesas/guardar")
    public String guardarMesaWeb(@ModelAttribute Mesa mesa) {
        try {
            // Intentamos guardar la mesa
            boolean exito = mesaService.guardarMesa(mesa);
            if (exito) {
                return "redirect:/mesas?exito=true"; // Si va bien, recarga con mensaje verde
            } else {
                return "redirect:/mesas?error=true"; // Si falla la lógica, recarga con mensaje rojo
            }
        } catch (Exception e) {
            // Si la Base de Datos explota (por ejemplo, número duplicado), lo capturamos aquí
            System.out.println("Error al guardar la mesa: " + e.getMessage());
            return "redirect:/mesas?error=duplicada";
        }
    }
    // 🔴 NUEVO MÉTODO: Borrar una mesa
    @GetMapping("/mesas/borrar/{id}")
    public String borrarMesa(@PathVariable Long id) {
        try {
            mesaService.borrarMesa(id);
            return "redirect:/mesas?exitoBorrado=true";
        } catch (Exception e) {
            System.out.println("Error al borrar la mesa: " + e.getMessage());
            return "redirect:/mesas?errorBorrado=true";
        }
    }
    // 1. Mostrar el formulario con los datos de la mesa cargados
    @GetMapping("/mesas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Mesa mesa = mesaService.obtenerPorId(id);
        if (mesa != null) {
            model.addAttribute("mesa", mesa);
            return "editar-mesa"; // Cargará un HTML que crearemos para editar
        }
        return "redirect:/mesas?error=true";
    }

    // 2. Guardar los cambios cuando el usuario le da a "Actualizar"
    @PostMapping("/mesas/editar/{id}")
    public String guardarEdicionMesa(@PathVariable Long id, @ModelAttribute Mesa mesa) {
        mesa.setId(id); // Nos aseguramos de que no cambie de ID
        mesaService.actualizarMesa(mesa);
        return "redirect:/mesas?exito=true";
    }
    
}