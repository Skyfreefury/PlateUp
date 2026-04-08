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

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteWebController {

    @Autowired
    private ClienteService clienteService;

    // 1. LISTAR CLIENTES
    @GetMapping
    public String listarClientes(Model model) {
        try {
            List<Cliente> lista = clienteService.obtenerTodos();
            
            // Enviamos la lista para la tabla de la derecha
            model.addAttribute("clientes", lista); 
            
            // 🚨 EL ARREGLO: Enviamos un objeto vacío para que el formulario no pete
            model.addAttribute("cliente", new Cliente()); 
            
            return "clientes"; 
        } catch (Exception e) {
            System.out.println("ERROR AL LISTAR CLIENTES: " + e.getMessage());
            return "redirect:/?error=true";
        }
    }

    // 2. GUARDAR / CREAR
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes?exito=true";
    }

    // 3. MOSTRAR FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "editar-cliente"; // Ahora crearemos este con el nuevo diseño
        }
        return "redirect:/clientes?error=true";
    }

    // 4. PROCESAR EDICIÓN
    @PostMapping("/editar/{id}")
    public String procesarEdicion(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente) {
        cliente.setId(id);
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes?exito=true";
    }

    // 5. BORRAR
    @GetMapping("/borrar/{id}")
    public String borrarCliente(@PathVariable Long id) {
        clienteService.borrarCliente(id);
        return "redirect:/clientes?exitoBorrado=true";
    }
}
