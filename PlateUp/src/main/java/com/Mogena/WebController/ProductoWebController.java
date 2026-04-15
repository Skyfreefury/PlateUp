package com.Mogena.WebController;

import com.Mogena.Model.Producto;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión visual de la Carta (Productos).
 * Sigue el patrón de diseño de Tablas de Visualización y Formularios Dedicados.
 */
@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;

    // =========================================================
    // 1. LISTAR PRODUCTOS (Vista Principal de la Carta)
    // =========================================================
    @GetMapping
    public String listarProductos(Model model) {
        try {
            List<Producto> lista = productoService.obtenerTodos();
            model.addAttribute("productos", lista);
            // Retorna la vista de la tabla (productos.html)
            return "productos"; 
        } catch (Exception e) {
            System.err.println("ERROR AL CARGAR LA CARTA: " + e.getMessage());
            return "redirect:/?error=true";
        }
    }

    // =========================================================
    // 2. MOSTRAR FORMULARIO PARA NUEVO PLATO
    // =========================================================
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        // Pasamos un objeto Producto vacío para vincularlo al th:object
        model.addAttribute("producto", new Producto());
        // Retorna la vista del formulario (producto-form.html)
        return "producto-form";
    }

    // =========================================================
    // 3. MOSTRAR FORMULARIO PARA EDITAR PLATO EXISTENTE
    // =========================================================
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto productoExistente = productoService.obtenerPorId(id);
        
        if (productoExistente != null) {
            model.addAttribute("producto", productoExistente);
            // Reutilizamos el mismo formulario (producto-form.html)
            return "producto-form";
        }
        
        return "redirect:/productos?error=no_encontrado";
    }

    // =========================================================
    // 4. GUARDAR / ACTUALIZAR (Método Unificado)
    // =========================================================
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto) {
        try {
            /* * Spring Boot detecta automáticamente el ID:
             * - Si id es null -> JPA hace un INSERT (Nuevo)
             * - Si id tiene valor -> JPA hace un UPDATE (Edición)
             */
            productoService.guardarProducto(producto);
            return "redirect:/productos?exito=true";
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR PRODUCTO: " + e.getMessage());
            return "redirect:/productos?error=true";
        }
    }

    // =========================================================
    // 5. ELIMINAR PRODUCTO
    // =========================================================
    @GetMapping("/borrar/{id}")
    public String borrarProducto(@PathVariable Long id) {
        try {
            productoService.borrarProducto(id);
            return "redirect:/productos?exitoBorrado=true";
        } catch (Exception e) {
            System.err.println("ERROR AL ELIMINAR PRODUCTO: " + e.getMessage());
            return "redirect:/productos?error=dependencias";
        }
    }
}