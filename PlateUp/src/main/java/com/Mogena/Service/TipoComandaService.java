/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.TipoComanda;
import com.Mogena.Repository.TipoComandaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de tipos de comanda (clasificaciones como "Mesa", "Barra", "Para llevar").
 * Actúa como capa intermedia entre el controlador y el repositorio JPA.
 */
@Service
public class TipoComandaService {

    @Autowired
    private TipoComandaDAO tipoComandaDAO;

    /** Devuelve todos los tipos de comanda registrados. */
    public List<TipoComanda> obtenerTodos() {
        return tipoComandaDAO.findAll();
    }

    /** Busca un tipo de comanda por su id. Devuelve null si no existe. */
    public TipoComanda obtenerPorId(Long id) {
        return tipoComandaDAO.findById(id).orElse(null);
    }

    /** Persiste un nuevo tipo de comanda. Devuelve {@code false} si ocurre un error de base de datos. */
    public boolean guardarTipoComanda(TipoComanda tipoComanda) {
        try {
            tipoComandaDAO.save(tipoComanda);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Actualiza un tipo de comanda existente. Devuelve {@code false} si no tiene id o falla la BD. */
    public boolean actualizarTipoComanda(TipoComanda tipoComanda) {
        if (tipoComanda.getId() == null) return false;
        try {
            tipoComandaDAO.save(tipoComanda);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Elimina el tipo de comanda con el id indicado. Devuelve {@code false} si no existe. */
    public boolean borrarTipoComanda(Long id) {
        if (!tipoComandaDAO.existsById(id)) return false;
        tipoComandaDAO.deleteById(id);
        return true;
    }
}
