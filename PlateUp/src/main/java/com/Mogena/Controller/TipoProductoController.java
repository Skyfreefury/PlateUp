/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.TipoProducto;
import com.Mogena.Service.TipoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST para la gestión de tipos de producto (categorías de la carta, p. ej. "Bebidas", "Entrantes").
 * Expone operaciones CRUD bajo la ruta /api/tipos-producto.
 */
@RestController
@RequestMapping("/api/tipos-producto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    /** Devuelve todos los tipos de producto disponibles. */
    @GetMapping
    public List<TipoProducto> obtenerTodos() {
        return tipoProductoService.obtenerTodos();
    }

    /** Devuelve el tipo de producto con el id indicado, o null si no existe. */
    @GetMapping("/{id}")
    public TipoProducto obtenerPorId(@PathVariable Long id) {
        return tipoProductoService.obtenerPorId(id);
    }

    /** Crea un nuevo tipo de producto. Devuelve "Guardado" si tiene éxito. */
    @PostMapping
    public String guardar(@RequestBody TipoProducto tipo) {
        return tipoProductoService.guardarTipoProducto(tipo) ? "Guardado" : "Error";
    }

    /** Actualiza un tipo de producto existente. Requiere que el objeto tenga id. */
    @PutMapping
    public String actualizar(@RequestBody TipoProducto tipo) {
        return tipoProductoService.actualizarTipoProducto(tipo) ? "Actualizado" : "Error";
    }

    /** Elimina el tipo de producto con el id indicado. */
    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return tipoProductoService.borrarTipoProducto(id) ? "Borrado" : "Error";
    }
}
