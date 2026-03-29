/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package com.Mogena.Controller;
//
//import com.Mogena.Model.Producto;
//import com.Mogena.Service.ProductoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/productos")
//public class ProductoController {
//
//    @Autowired
//    private ProductoService productoService;
//
//    @GetMapping
//    public List<Producto> obtenerTodos() {
//        return productoService.obtenerTodos();
//    }
//
//    @GetMapping("/{id}")
//    public Producto obtenerPorId(@PathVariable Long id) {
//        return productoService.obtenerPorId(id);
//    }
//
//    @PostMapping
//    public String guardar(@RequestBody Producto producto) {
//        return productoService.guardarProducto(producto) ? "Guardado" : "Error";
//    }
//
//    @PutMapping
//    public String actualizar(@RequestBody Producto producto) {
//        return productoService.actualizarProducto(producto) ? "Actualizado" : "Error";
//    }
//
//    @DeleteMapping("/{id}")
//    public String borrar(@PathVariable Long id) {
//        return productoService.borrarProducto(id) ? "Borrado" : "Error";
//    }
//}
