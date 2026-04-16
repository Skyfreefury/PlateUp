/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.LineaProducto;
import com.Mogena.Repository.LineaProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de líneas de producto (ítems individuales dentro de una comanda).
 * Actúa como capa intermedia entre el controlador y el repositorio JPA.
 */
@Service
public class LineaProductoService {

    @Autowired
    private LineaProductoDAO lineaProductoDAO;

    /** Devuelve todas las líneas de producto de la base de datos. */
    public List<LineaProducto> obtenerTodas() {
        return lineaProductoDAO.findAll();
    }

    /** Busca una línea de producto por su id. Devuelve null si no existe. */
    public LineaProducto obtenerPorId(Long id) {
        return lineaProductoDAO.findById(id).orElse(null);
    }

    /** Persiste una nueva línea de producto. Siempre devuelve true. */
    public boolean guardarLinea(LineaProducto linea) {
        lineaProductoDAO.save(linea);
        return true;
    }

    /** Actualiza una línea de producto existente. Devuelve false si no tiene id asignado. */
    public boolean actualizarLinea(LineaProducto linea) {
        if (linea.getId() == null) return false;
        lineaProductoDAO.save(linea);
        return true;
    }

    /** Elimina la línea de producto con el id indicado. Siempre devuelve true. */
    public boolean borrarLinea(Long id) {
        lineaProductoDAO.deleteById(id);
        return true;
    }
}
