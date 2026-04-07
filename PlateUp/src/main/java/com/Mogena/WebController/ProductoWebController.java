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
@RequestMapping("/productos") // Ruta en el navegador: localhost:8080/productos
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;

    // 1. LISTAR PRODUCTOS
    @GetMapping
    public String listarProductos(Model model) {
        try {
            List<Producto> lista = productoService.obtenerTodos();
            
            // 🚨 IMPORTANTE: En tu HTML usas ${productos}, así que el nombre debe ser este:
            model.addAttribute("productos", lista); 
            
            // Objeto vacío para el formulario de "Nuevo Producto"
            model.addAttribute("producto", new Producto()); 
            
            // 🚨 CORRECCIÓN FINAL: Debe coincidir con tu archivo "Productos.html"
            return "Productos"; 
            
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO AL LISTAR: " + e.getMessage());
            return "redirect:/mesas?error=true";
        }
    }

    // 2. GUARDAR (NUEVO O EDITADO)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        // El Service se encarga de reciclar el ID si es nuevo
        productoService.guardarProducto(producto);
        return "redirect:/productos?exito=true";
    }

    // 3. MOSTRAR FORMULARIO DE EDITAR
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto p = productoService.obtenerPorId(id);
        if (p != null) {
            model.addAttribute("producto", p);
            // 🚨 Según tu captura, este se llama editar-producto.html (en minúsculas)
            return "editar-producto"; 
        }
        return "redirect:/productos?error=true";
    }

    // 4. BORRAR PRODUCTO
    @GetMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        productoService.borrarProducto(id);
        return "redirect:/productos?exitoBorrado=true";
    }
}