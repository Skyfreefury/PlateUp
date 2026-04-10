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

import java.util.List;

@Controller
@RequestMapping("/productos") 
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;

    // 1. LISTAR PRODUCTOS
    @GetMapping
    public String listarProductos(Model model) {
        try {
            List<Producto> lista = productoService.obtenerTodos();
            model.addAttribute("productos", lista); 
            model.addAttribute("producto", new Producto()); 
            return "Productos"; 
            
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO AL LISTAR: " + e.getMessage());
            return "redirect:/mesas?error=true";
        }
    }

    // 2. GUARDAR (NUEVO)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos?exito=true";
    }

    // 3. MOSTRAR FORMULARIO DE EDITAR (Envía la página al navegador)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "editar-producto"; 
        }
        return "redirect:/productos?error=true";
    }

    // =========================================================
    // ✨ 4. ACTUALIZAR PRODUCTO (ESTO ES LO QUE FALTABA) ✨
    // Recibe los datos del formulario HTML y los guarda en la BD
    // =========================================================
    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute("producto") Producto producto) {
        try {
            producto.setId(id); // Blindaje extra por si el HTML pierde el ID
            productoService.guardarProducto(producto);
            return "redirect:/productos?exito=true";
        } catch (Exception e) {
            System.out.println("ERROR AL EDITAR: " + e.getMessage());
            return "redirect:/productos?error=true";
        }
    }

    // 5. BORRAR PRODUCTO
    @GetMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
        return "redirect:/productos?exitoBorrado=true";
    }
}