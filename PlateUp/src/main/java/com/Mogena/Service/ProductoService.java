/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Producto;
import com.Mogena.Repository.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoDAO productoDAO;

    // Límite de productos en la carta (puedes ampliarlo si el restaurante crece)
    private final int LIMITE_PRODUCTOS = 500;

    // 1. OBTENER TODOS LOS PRODUCTOS
    public List<Producto> obtenerTodos() {
        return productoDAO.findAll();
    }

    // 2. OBTENER PRODUCTO POR ID
    public Producto obtenerPorId(Long id) {
        Optional<Producto> o = productoDAO.findById(id);
        return o.orElse(null);
    }

    // 3. GUARDAR O ACTUALIZAR PRODUCTO (CON RECICLAJE DE IDS)
    @Transactional
    public boolean guardarProducto(Producto producto) {
        // A) Si el producto YA TIENE ID (viene de un Editar), actualizamos directamente
        if (producto.getId() != null) {
            productoDAO.saveAndFlush(producto);
            return true;
        }

        // B) Si es un PRODUCTO NUEVO, buscamos el primer ID libre (hueco)
        List<Long> idsOcupados = productoDAO.findAll().stream()
                                            .map(Producto::getId)
                                            .toList();
        
        for (long i = 1; i <= LIMITE_PRODUCTOS; i++) {
            if (!idsOcupados.contains(i)) {
                // ¡Hueco encontrado! Asignamos el ID manualmente
                producto.setId(i);
                
                // Forzamos el guardado inmediato para evitar el error de IdentifierGeneration
                productoDAO.saveAndFlush(producto);
                return true;
            }
        }

        // Si llegamos aquí, es que la carta está llena
        return false;
    }

    // 4. BORRAR PRODUCTO
    @Transactional
    public void borrarProducto(Long id) {
        if (productoDAO.existsById(id)) {
            productoDAO.deleteById(id);
            // Opcional: flush para asegurar que el ID queda libre al instante
            productoDAO.flush();
        }
    }
    public Producto obtenerPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;
        return productoDAO.findByNombre(nombre);
    }
}