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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mesas")
public class MesaWebController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public String listar(Model model) {
        // "listaMesas" debe coincidir con el th:each del HTML
        model.addAttribute("listaMesas", mesaService.obtenerTodas());
        // "mesa" debe coincidir con el th:object del formulario
        model.addAttribute("mesa", new Mesa()); 
        return "mesas";
    }

    // 🚨 CORRECCIÓN: Quitamos el "/mesas" repetido porque ya está en el RequestMapping de arriba
    @PostMapping("/guardar")
    public String guardarMesa(@ModelAttribute("mesa") Mesa mesa) {
        System.out.println("Enviando al Service la mesa con capacidad: " + mesa.getCapacidad());
        
        // El Service se encargará de ponerle el ID y el Número libre
        mesaService.guardarMesa(mesa); 
        
        return "redirect:/mesas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Mesa m = mesaService.obtenerPorId(id);
        if (m != null) {
            model.addAttribute("mesa", m);
            return "editar-mesa"; 
        }
        return "redirect:/mesas";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        mesaService.borrarMesa(id);
        return "redirect:/mesas?borrado=true";
    }
}