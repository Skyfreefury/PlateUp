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

@RestController
@RequestMapping("/api/tipos-producto")
public class TipoProductoController {

    @Autowired
    private TipoProductoService tipoProductoService;

    @GetMapping
    public List<TipoProducto> obtenerTodos() {
        return tipoProductoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public TipoProducto obtenerPorId(@PathVariable Long id) {
        return tipoProductoService.obtenerPorId(id);
    }

    @PostMapping
    public String guardar(@RequestBody TipoProducto tipo) {
        return tipoProductoService.guardarTipoProducto(tipo) ? "Guardado" : "Error";
    }

    @PutMapping
    public String actualizar(@RequestBody TipoProducto tipo) {
        return tipoProductoService.actualizarTipoProducto(tipo) ? "Actualizado" : "Error";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return tipoProductoService.borrarTipoProducto(id) ? "Borrado" : "Error";
    }
}
