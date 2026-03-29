/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Cliente;
import com.Mogena.Repository.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    public List<Cliente> obtenerTodos() {
        return clienteDAO.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteDAO.findById(id);
    }

    public boolean guardarCliente(Cliente cliente) {
        // Ejemplo de lógica de negocio: no guardar si no hay nombre
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            return false;
        }
        return clienteDAO.save(cliente) > 0;
    }

    public boolean actualizarCliente(Cliente cliente) {
        if (cliente.getId() == null) return false;
        return clienteDAO.update(cliente) > 0;
    }

    public boolean borrarCliente(Long id) {
        return clienteDAO.delete(id) > 0;
    }
}
