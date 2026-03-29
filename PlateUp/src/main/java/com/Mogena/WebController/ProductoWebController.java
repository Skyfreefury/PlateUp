/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.WebController;

import com.Mogena.Model.Producto;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;

    // 1. Leer (Mostrar lista de productos en el futuro HTML)
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerTodos());
        return "productos"; // Apunta al futuro archivo productos.html
    }

    // 2. Crear (Guardar nuevo producto)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos?exito=true";
    }

    // 3. Mostrar formulario de editar
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "editar-producto"; // Apunta al futuro archivo editar-producto.html
        }
        return "redirect:/productos?error=true";
    }

    // 4. Actualizar (Guardar cambios de la edición)
    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute Producto producto) {
        producto.setId(id);
        productoService.actualizarProducto(producto);
        return "redirect:/productos?exito=true";
    }

    // 5. Borrar producto
    @GetMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
        return "redirect:/productos?exitoBorrado=true";
    }
}
