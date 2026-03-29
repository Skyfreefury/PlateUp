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

@RestController
@RequestMapping("/api/lineas-producto")
public class LineaProductoController {

    @Autowired
    private LineaProductoService lineaProductoService;

    @GetMapping
    public List<LineaProducto> obtenerTodas() {
        return lineaProductoService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public LineaProducto obtenerPorId(@PathVariable Long id) {
        return lineaProductoService.obtenerPorId(id);
    }

    @PostMapping
    public String guardar(@RequestBody LineaProducto linea) {
        return lineaProductoService.guardarLinea(linea) ? "Guardada" : "Error";
    }

    @PutMapping
    public String actualizar(@RequestBody LineaProducto linea) {
        return lineaProductoService.actualizarLinea(linea) ? "Actualizada" : "Error";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return lineaProductoService.borrarLinea(id) ? "Borrada" : "Error";
    }
}
