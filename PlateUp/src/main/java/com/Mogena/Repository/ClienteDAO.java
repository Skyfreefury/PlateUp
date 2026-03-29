/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Cliente;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> findAll();          // Buscar todos los clientes
    Cliente findById(Long id);        // Buscar un cliente por su ID
    int save(Cliente cliente);        // Guardar un cliente nuevo
    int update(Cliente cliente);      // Actualizar un cliente existente
    int delete(Long id);              // Borrar un cliente por su ID
}
