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

@RestController
@RequestMapping("/api/tipos-comanda")
public class TipoComandaController {

    @Autowired
    private TipoComandaService tipoComandaService;

    @GetMapping
    public List<TipoComanda> obtenerTodos() {
        return tipoComandaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public TipoComanda obtenerPorId(@PathVariable Long id) {
        return tipoComandaService.obtenerPorId(id);
    }

    @PostMapping
    public String guardar(@RequestBody TipoComanda tipo) {
        return tipoComandaService.guardarTipoComanda(tipo) ? "Guardado" : "Error";
    }

    @PutMapping
    public String actualizar(@RequestBody TipoComanda tipo) {
        return tipoComandaService.actualizarTipoComanda(tipo) ? "Actualizado" : "Error";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return tipoComandaService.borrarTipoComanda(id) ? "Borrado" : "Error";
    }
}