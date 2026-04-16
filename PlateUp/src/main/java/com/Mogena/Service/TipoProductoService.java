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

    /** Persiste un nuevo tipo de producto. Siempre devuelve true. */
    public boolean guardarTipoProducto(TipoProducto tipoProducto) {
        tipoProductoDAO.save(tipoProducto);
        return true;
    }

    /** Actualiza un tipo de producto existente. Devuelve false si no tiene id asignado. */
    public boolean actualizarTipoProducto(TipoProducto tipoProducto) {
        if (tipoProducto.getId() == null) return false;
        tipoProductoDAO.save(tipoProducto);
        return true;
    }

    /** Elimina el tipo de producto con el id indicado. Siempre devuelve true. */
    public boolean borrarTipoProducto(Long id) {
        tipoProductoDAO.deleteById(id);
        return true;
    }
}