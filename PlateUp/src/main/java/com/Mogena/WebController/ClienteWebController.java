package com.Mogena.WebController;

import com.Mogena.Model.Cliente;
import com.Mogena.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador web para la gestión del directorio de clientes.
 * Cubre el CRUD completo: listar, crear, editar, guardar y borrar.
 */
@Controller
@RequestMapping("/clientes")
public class ClienteWebController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obtenerTodos());
        return "clientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "cliente-form";
        }
        return "redirect:/clientes?error=true";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes?exito=true";
    }

    @GetMapping("/borrar/{id}")
    public String borrarCliente(@PathVariable Long id) {
        clienteService.borrarCliente(id);
        return "redirect:/clientes?exitoBorrado=true";
    }
}
