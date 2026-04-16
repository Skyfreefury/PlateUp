/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.LineaProducto;
import com.Mogena.Service.LineaProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API REST para la gestión de líneas de producto (ítems individuales de una comanda).
 * Expone operaciones CRUD bajo la ruta /api/lineas-producto.
 */
@RestController
@RequestMapping("/api/lineas-producto")
public class LineaProductoController {

    @Autowired
    private LineaProductoService lineaProductoService;

    /** Devuelve todas las líneas de producto registradas. */
    @GetMapping
    public List<LineaProducto> obtenerTodas() {
        return lineaProductoService.obtenerTodas();
    }

    /** Devuelve la línea de producto con el id indicado, o null si no existe. */
    @GetMapping("/{id}")
    public LineaProducto obtenerPorId(@PathVariable Long id) {
        return lineaProductoService.obtenerPorId(id);
    }

    /** Crea una nueva línea de producto. Devuelve "Guardada" si tiene éxito. */
    @PostMapping
    public String guardar(@RequestBody LineaProducto linea) {
        return lineaProductoService.guardarLinea(linea) ? "Guardada" : "Error";
    }

    /** Actualiza una línea de producto existente. Requiere que el objeto tenga id. */
    @PutMapping
    public String actualizar(@RequestBody LineaProducto linea) {
        return lineaProductoService.actualizarLinea(linea) ? "Actualizada" : "Error";
    }

    /** Elimina la línea de producto con el id indicado. */
    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return lineaProductoService.borrarLinea(id) ? "Borrada" : "Error";
    }
}
