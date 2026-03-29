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
        // findById en JPA devuelve un Optional
        return clienteDAO.findById(id).orElse(null);
    }

    public boolean guardarCliente(Cliente cliente) {
        // En JPA save() hace el insert
        clienteDAO.save(cliente);
        return true;
    }

    public boolean actualizarCliente(Cliente cliente) {
        if (cliente.getId() == null) return false;
        // En JPA save() también sirve para hacer el update si el ID ya existe
        clienteDAO.save(cliente);
        return true;
    }

    public boolean borrarCliente(Long id) {
        clienteDAO.deleteById(id);
        return true;
    }
}