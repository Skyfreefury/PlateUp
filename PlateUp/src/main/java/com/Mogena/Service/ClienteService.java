package com.Mogena.Service;

import com.Mogena.Model.Cliente;
import com.Mogena.Repository.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Servicio para la gestión del directorio de clientes del restaurante.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteDAO clienteDAO;

    /** Devuelve todos los clientes registrados. */
    public List<Cliente> obtenerTodos() {
        return clienteDAO.findAll();
    }

    /** Devuelve el cliente con el ID indicado, o {@code null} si no existe. */
    public Cliente obtenerPorId(Long id) {
        return clienteDAO.findById(id).orElse(null);
    }

    /** Persiste un cliente nuevo. */
    public boolean guardarCliente(Cliente cliente) {
        clienteDAO.save(cliente);
        return true;
    }

    /** Actualiza un cliente existente. Devuelve {@code false} si no tiene ID asignado. */
    public boolean actualizarCliente(Cliente cliente) {
        if (cliente.getId() == null) return false;
        clienteDAO.save(cliente);
        return true;
    }

    /** Elimina el cliente con el ID indicado. */
    public boolean borrarCliente(Long id) {
        clienteDAO.deleteById(id);
        return true;
    }
}
