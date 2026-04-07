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
@RequestMapping("/api/mesas") // O la ruta que tuvieras puesta aquí antes
public class MesaController {

    @Autowired
    private MesaService mesaService;

    @GetMapping
    public List<Mesa> listar() {
        return mesaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Mesa obtenerPorId(@PathVariable Long id) {
        return mesaService.obtenerPorId(id);
    }

    @PostMapping
    public boolean crear(@RequestBody Mesa mesa) {
        // Ahora guardarMesa devuelve un boolean si tiene éxito
        return mesaService.guardarMesa(mesa);
    }

    @PutMapping("/{id}")
    public boolean actualizar(@PathVariable Long id, @RequestBody Mesa mesa) {
        mesa.setId(id);
        // Usamos guardarMesa porque ahora sirve tanto para crear como para actualizar
        return mesaService.guardarMesa(mesa);
    }

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable Long id) {
        // Como borrarMesa es 'void', ya no ponemos el 'return' delante
        mesaService.borrarMesa(id);
    }
}
