/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.Cliente;
import com.Mogena.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes") // Esta será la URL en el navegador
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // GET: http://localhost:8080/api/clientes
    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteService.obtenerTodos();
    }

    // GET: http://localhost:8080/api/clientes/1
    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id);
    }

    // POST: Para crear un cliente nuevo
    @PostMapping
    public String guardar(@RequestBody Cliente cliente) {
        boolean exito = clienteService.guardarCliente(cliente);
        return exito ? "Cliente guardado correctamente" : "Error al guardar el cliente";
    }

    // PUT: Para actualizar un cliente existente
    @PutMapping
    public String actualizar(@RequestBody Cliente cliente) {
        boolean exito = clienteService.actualizarCliente(cliente);
        return exito ? "Cliente actualizado correctamente" : "Error al actualizar el cliente";
    }

    // DELETE: Para borrar un cliente
    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        boolean exito = clienteService.borrarCliente(id);
        return exito ? "Cliente borrado correctamente" : "Error al borrar el cliente";
    }
}
