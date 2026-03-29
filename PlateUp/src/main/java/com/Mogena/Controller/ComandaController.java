/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.Comanda;
import com.Mogena.Service.ComandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comandas")
public class ComandaController {

    @Autowired
    private ComandaService comandaService;

    @GetMapping
    public List<Comanda> obtenerTodas() {
        return comandaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Comanda obtenerPorId(@PathVariable Long id) {
        return comandaService.obtenerPorId(id);
    }

    @PostMapping
    public String guardar(@RequestBody Comanda comanda) {
        return comandaService.guardarComanda(comanda) ? "Guardado" : "Error";
    }

    @PutMapping
    public String actualizar(@RequestBody Comanda comanda) {
        return comandaService.actualizarComanda(comanda) ? "Actualizado" : "Error";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        return comandaService.borrarComanda(id) ? "Borrado" : "Error";
    }
}