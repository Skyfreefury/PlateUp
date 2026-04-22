package com.Mogena.WebController;

import com.Mogena.Model.Producto;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador web para la gestión de la carta de productos del restaurante.
 * Cubre el CRUD completo: listar por categoría, crear, editar, guardar y borrar.
 */
@Controller
@RequestMapping("/productos")
public class ProductoWebController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model) {
        try {
            List<Producto> lista = productoService.obtenerTodos();
            model.addAttribute("productos", lista);
            return "productos";
        } catch (Exception e) {
            System.err.println("ERROR AL CARGAR LA CARTA: " + e.getMessage());
            return "redirect:/?error=true";
        }
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto-form";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto productoExistente = productoService.obtenerPorId(id);
        if (productoExistente != null) {
            model.addAttribute("producto", productoExistente);
            return "producto-form";
        }
        return "redirect:/productos?error=no_encontrado";
    }

    /**
     * Guarda un producto nuevo o actualiza uno existente.
     * El servicio detecta automáticamente si es una inserción o una actualización
     * basándose en la presencia del ID.
     */
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, Model model) {
        try {
            productoService.guardarProducto(producto);
            return "redirect:/productos?exito=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "producto-form";
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR PRODUCTO: " + e.getMessage());
            model.addAttribute("error", "Error inesperado al guardar el producto.");
            return "producto-form";
        }
    }

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
