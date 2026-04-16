/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.TipoProducto;
import com.Mogena.Repository.TipoProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de tipos de producto (categorías de la carta como "Bebidas", "Entrantes").
 * Actúa como capa intermedia entre el controlador y el repositorio JPA.
 */
@Service
public class TipoProductoService {

    @Autowired
    private TipoProductoDAO tipoProductoDAO;

    /** Devuelve todos los tipos de producto registrados. */
    public List<TipoProducto> obtenerTodos() {
        return tipoProductoDAO.findAll();
    }

    /** Busca un tipo de producto por su id. Devuelve null si no existe. */
    public TipoProducto obtenerPorId(Long id) {
        return tipoProductoDAO.findById(id).orElse(null);
    }

    /** Persiste un nuevo tipo de producto. Devuelve {@code false} si ocurre un error de base de datos. */
    public boolean guardarTipoProducto(TipoProducto tipoProducto) {
        try {
            tipoProductoDAO.save(tipoProducto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Actualiza un tipo de producto existente. Devuelve {@code false} si no tiene id o falla la BD. */
    public boolean actualizarTipoProducto(TipoProducto tipoProducto) {
        if (tipoProducto.getId() == null) return false;
        try {
            tipoProductoDAO.save(tipoProducto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Elimina el tipo de producto con el id indicado. Devuelve {@code false} si no existe. */
    public boolean borrarTipoProducto(Long id) {
        if (!tipoProductoDAO.existsById(id)) return false;
        tipoProductoDAO.deleteById(id);
        return true;
    }
}