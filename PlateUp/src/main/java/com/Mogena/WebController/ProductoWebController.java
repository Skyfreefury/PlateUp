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

    // =========================================================
    // 1. LISTAR PRODUCTOS (Muestra solo la tabla con los filtros)
    // =========================================================
    @GetMapping
    public String listarProductos(Model model) {
        try {
            List<Producto> lista = productoService.obtenerTodos();
            model.addAttribute("productos", lista); 
            // Ya no hace falta mandar un producto vacío aquí porque el form no está en esta página
            return "productos"; // Carga el archivo productos.html
            
        } catch (Exception e) {
            System.out.println("ERROR CRÍTICO AL LISTAR: " + e.getMessage());
            return "redirect:/?error=true";
        }
    }

    // =========================================================
    // 2. PANTALLA "AÑADIR NUEVO" (Muestra el formulario vacío)
    // =========================================================
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto()); // Le pasamos un producto en blanco
        return "producto-form"; // Carga el archivo producto-form.html
    }

    // =========================================================
    // 3. PANTALLA "EDITAR" (Muestra el formulario relleno)
    // =========================================================
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        
        if (producto != null) {
            model.addAttribute("producto", producto);
            // REUTILIZAMOS EL MISMO HTML QUE PARA EL NUEVO
            return "producto-form"; 
        }
        return "redirect:/productos?error=true";
    }

    // =========================================================
    // 4. GUARDAR / ACTUALIZAR (Método Unificado)
    // =========================================================
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        try {
            // Spring Boot sabe si es nuevo (sin ID) o editar (con ID) automáticamente
            productoService.guardarProducto(producto);
            return "redirect:/productos?exito=true";
        } catch (Exception e) {
            System.out.println("ERROR AL GUARDAR: " + e.getMessage());
            return "redirect:/productos?error=true";
        }
    }

    // =========================================================
    // 5. BORRAR PRODUCTO
    // =========================================================
    @GetMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        try {
            productoService.borrarProducto(id);
            return "redirect:/productos?exitoBorrado=true";
        } catch (Exception e) {
            System.out.println("ERROR AL BORRAR: " + e.getMessage());
            return "redirect:/productos?error=true";
        }
    }
}