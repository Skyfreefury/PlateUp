/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.TipoComanda;
import com.Mogena.Service.TipoComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST para la gestión de tipos de comanda (p. ej. "Mesa", "Barra", "Para llevar").
 * Expone operaciones CRUD bajo la ruta /api/tipos-comanda.
 */
@RestController
@RequestMapping("/api/tipos-comanda")
public class TipoComandaController {

    @Autowired
    private TipoComandaService tipoComandaService;

    /** Devuelve todos los tipos de comanda disponibles. */
    @GetMapping
    public List<TipoComanda> obtenerTodos() {
        return tipoComandaService.obtenerTodos();
    }

    /** Devuelve el tipo de comanda con el id indicado, o null si no existe. */
    @GetMapping("/{id}")
    public TipoComanda obtenerPorId(@PathVariable Long id) {
        return tipoComandaService.obtenerPorId(id);
    }

    /** Crea un nuevo tipo de comanda. Devuelve "Guardado" si tiene éxito. */
    @PostMapping
    public String guardar(@RequestBody TipoComanda tipo) {
        return tipoComandaService.guardarTipoComanda(tipo) ? "Guardado" : "Error";
    }

    /** Actualiza un tipo de comanda existente. Requiere que el objeto tenga id. */
    @PutMapping
    public String actualizar(@RequestBody TipoComanda tipo) {
        return tipoComandaService.actualizarTipoComanda(tipo) ? "Actualizado" : "Error";
    }

    /** Elimina el tipo de comanda con el id indicado. */
    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return tipoComandaService.borrarTipoComanda(id) ? "Borrado" : "Error";
    }
}