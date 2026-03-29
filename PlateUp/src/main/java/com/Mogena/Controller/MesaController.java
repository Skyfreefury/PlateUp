/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Controller;

import com.Mogena.Model.Mesa;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public List<Mesa> obtenerTodas() {
        return mesaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Mesa obtenerPorId(@PathVariable Long id) {
        return mesaService.obtenerPorId(id);
    }

    @PostMapping
    public String guardar(@RequestBody Mesa mesa) {
        boolean exito = mesaService.guardarMesa(mesa);
        return exito ? "Mesa guardada correctamente" : "Error al guardar la mesa";
    }

    @PutMapping
    public String actualizar(@RequestBody Mesa mesa) {
        boolean exito = mesaService.actualizarMesa(mesa);
        return exito ? "Mesa actualizada correctamente" : "Error al actualizar la mesa";
    }

    @DeleteMapping("/{id}")
    public String borrar(@PathVariable Long id) {
        boolean exito = mesaService.borrarMesa(id);
        return exito ? "Mesa borrada correctamente" : "Error al borrar la mesa";
    }
}
