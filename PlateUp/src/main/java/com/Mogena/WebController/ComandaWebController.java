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

    // =========================================================
    // 1. LISTAR COMANDAS (Panel de Cocina y Filtros)
    // =========================================================
    @GetMapping
    public String listarComandas(Model model) {
        List<Comanda> listaComandas = comandaService.obtenerTodas();
        List<Producto> listaProductos = productoService.obtenerTodos();
        
        model.addAttribute("comandas", listaComandas);
        model.addAttribute("productos", listaProductos);
        // Ya no enviamos "new Comanda()" aquí, porque el formulario está en otra pantalla
        
        return "comandas"; // Carga comandas.html
    }

    // =========================================================
    // 2. PANTALLA "MARCHAR PLATO" (Formulario vacío)
    // =========================================================
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevaComanda(Model model) {
        model.addAttribute("comanda", new Comanda());
        return "comanda-form"; // Carga comanda-form.html
    }

    // =========================================================
    // 3. PANTALLA "EDITAR COMANDA" (Formulario relleno)
    // =========================================================
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarComanda(@PathVariable Long id, Model model) {
        Comanda comanda = comandaService.obtenerPorId(id);
        if (comanda != null) {
            model.addAttribute("comanda", comanda);
            return "comanda-form";
        }
        return "redirect:/comandas?error=true";
    }

    // =========================================================
    // 4. GUARDAR / ACTUALIZAR COMANDA
    // =========================================================
    @PostMapping("/guardar")
    public String guardarComanda(@ModelAttribute("comanda") Comanda comanda) {
        // Tu lógica original de rescate (¡Muy buena práctica!)
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

    // =========================================================
    // 5. BORRAR / LISTO (Finalizar plato)
    // =========================================================
    @GetMapping("/borrar/{id}") // CAMBIADO a @GetMapping para funcionar con el <a> del HTML
    public String borrarComanda(@PathVariable Long id) {
        try {
            comandaService.borrarComanda(id);
            return "redirect:/comandas?exitoBorrado=true";
        } catch (Exception e) {
            return "redirect:/comandas?error=true";
        }
    }
}