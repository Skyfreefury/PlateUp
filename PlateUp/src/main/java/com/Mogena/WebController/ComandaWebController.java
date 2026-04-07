/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.WebController;

import com.Mogena.Model.Comanda;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comandas")
public class ComandaWebController {

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private ProductoService productoService; // 🚨 Añadimos la Carta aquí

    // 1. LISTAR
    @GetMapping
    public String listarComandas(Model model) {
        try {
            model.addAttribute("comandas", comandaService.obtenerTodas());
            model.addAttribute("comanda", new Comanda());
            
            // 🚨 Pasamos la lista de platos para el menú desplegable
            model.addAttribute("productos", productoService.obtenerTodos()); 
            
            return "comandas"; 
        } catch (Exception e) {
            return "redirect:/?error=true";
        }
    }

    // 2. GUARDAR
    @PostMapping("/guardar")
    public String guardarComanda(@ModelAttribute("comanda") Comanda comanda) {
        comandaService.guardarComanda(comanda);
        return "redirect:/comandas?exito=true";
    }

    // 3. MOSTRAR FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Comanda comanda = comandaService.obtenerPorId(id);
        if (comanda != null) {
            model.addAttribute("comanda", comanda);
            // 🚨 También necesitamos la carta en el formulario de editar
            model.addAttribute("productos", productoService.obtenerTodos()); 
            return "editar-comanda"; 
        }
        return "redirect:/comandas?error=true";
    }

    // 4. PROCESAR EDICIÓN
    @PostMapping("/editar/{id}")
    public String procesarEdicion(@PathVariable Long id, @ModelAttribute("comanda") Comanda comanda) {
        comanda.setId(id);
        comandaService.guardarComanda(comanda);
        return "redirect:/comandas?exito=true";
    }

    // 5. BORRAR
    @GetMapping("/borrar/{id}")
    public String borrarComanda(@PathVariable Long id) {
        comandaService.borrarComanda(id);
        return "redirect:/comandas?exitoBorrado=true";
    }
}