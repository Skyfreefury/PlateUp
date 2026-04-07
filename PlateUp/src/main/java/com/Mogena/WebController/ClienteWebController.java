/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.WebController;

import com.Mogena.Model.Cliente;
import com.Mogena.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteWebController {

    @Autowired
    private ClienteService clienteService;

    // Listar todos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "clientes";
    }

    // Guardar (Nuevo o Editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes?exito=true";
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente c = clienteService.obtenerPorId(id);
        if (c != null) {
            model.addAttribute("cliente", c);
            return "editar-cliente"; 
        }
        return "redirect:/clientes";
    }

    // Borrar
    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        clienteService.borrarCliente(id);
        return "redirect:/clientes?borrado=true";
    }
}
