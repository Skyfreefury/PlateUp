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
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeWebController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String index(Model model) {
        try {
            // 1. Obtenemos todos los productos de la base de datos
            List<Producto> todosLosProductos = productoService.obtenerTodos();

            // 2. Los pasamos al modelo para el diseño general
            model.addAttribute("productos", todosLosProductos);

            // 3. (Opcional) Si quieres mantener las pestañas del index funcionando con datos reales:
            List<Producto> entrantes = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 1)
                    .collect(Collectors.toList());
            
            List<Producto> principales = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 2)
                    .collect(Collectors.toList());
            
            List<Producto> postres = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 3)
                    .collect(Collectors.toList());

            model.addAttribute("entrantes", entrantes);
            model.addAttribute("principales", principales);
            model.addAttribute("postres", postres);

            return "index"; // Carga index.html
        } catch (Exception e) {
            System.out.println("Error en Home: " + e.getMessage());
            return "index"; // Retorna el index aunque esté vacío para no dar error 500
        }
    }
}
