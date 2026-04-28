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

    public Cliente obtenerPorEmail(String email) {
        return clienteDAO.findByEmail(email);
    }

    public boolean guardarCliente(Cliente cliente) {
        validar(cliente);
        clienteDAO.save(cliente);
        return true;
    }

    public boolean actualizarCliente(Cliente cliente) {
        if (cliente.getId() == null) return false;
        validar(cliente);
        clienteDAO.save(cliente);
        return true;
    }

    private void validar(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        if (cliente.getEmail() != null && !cliente.getEmail().isBlank()) {
            boolean duplicado = (cliente.getId() == null)
                ? clienteDAO.existsByEmail(cliente.getEmail())
                : clienteDAO.existsByEmailAndIdNot(cliente.getEmail(), cliente.getId());
            if (duplicado)
                throw new IllegalArgumentException("Ya existe un cliente con el email «" + cliente.getEmail() + "».");
        }
    }

    /** Elimina el cliente con el ID indicado. Devuelve {@code false} si no existe. */
    public boolean borrarCliente(Long id) {
        if (!clienteDAO.existsById(id)) return false;
        clienteDAO.deleteById(id);
        return true;
    }
}
