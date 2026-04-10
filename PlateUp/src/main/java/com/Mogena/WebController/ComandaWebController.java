package com.Mogena.WebController;

import com.Mogena.Model.Comanda;
import com.Mogena.Model.Producto;
import com.Mogena.Service.ComandaService;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/comandas")
public class ComandaWebController {

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private ProductoService productoService;

    // 1. LISTAR COMANDAS
    @GetMapping
    public String listarComandas(Model model) {
        List<Comanda> listaComandas = comandaService.obtenerTodas();
        List<Producto> listaProductos = productoService.obtenerTodos();
        
        model.addAttribute("comandas", listaComandas);
        model.addAttribute("productos", listaProductos);
        model.addAttribute("comanda", new Comanda()); // Objeto para el formulario
        
        return "comandas";
    }

    // 2. GUARDAR / MARCHAR COMANDA
    @PostMapping("/guardar")
    public String guardarComanda(@ModelAttribute("comanda") Comanda comanda) {
        // Rescate de categoría si falla el JS
        if (comanda.getTipoComandaId() == null || comanda.getTipoComandaId() == 0) {
            Producto p = productoService.obtenerPorNombre(comanda.getNombrePlato());
            if (p != null) {
                comanda.setTipoComandaId(p.getTipoProductoId());
            }
        }
        
        if (comanda.getEstado() == null) comanda.setEstado("EN_PREPARACION");
        if (comanda.getCreadoEn() == null) comanda.setCreadoEn(LocalDateTime.now());

        comandaService.guardarComanda(comanda);
        return "redirect:/comandas";
    }

    // 3. BORRAR / LISTO (Finalizar plato)
    @PostMapping("/borrar/{id}")
    public String borrarComanda(@PathVariable Long id) {
        try {
            comandaService.borrarComanda(id);
            return "redirect:/comandas";
        } catch (Exception e) {
            return "redirect:/comandas?error=true";
        }
    }
}