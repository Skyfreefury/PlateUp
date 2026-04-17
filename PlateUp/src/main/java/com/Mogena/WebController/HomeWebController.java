package com.Mogena.WebController;

import com.Mogena.Model.Producto;
import com.Mogena.Service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de la página de inicio pública del restaurante.
 * Carga los productos de la carta y los pasa al template {@code index.html}
 * separados por categoría para mostrar las pestañas de la carta al cliente.
 */
@Controller
public class HomeWebController {

    @Autowired
    private ProductoService productoService;

    /**
     * Renderiza la página de inicio con la carta del restaurante.
     * Si la base de datos no está disponible, renderiza el index igualmente
     * con listas vacías para evitar un error 500.
     */
    @GetMapping("/")
    public String index(Model model) {
        try {
            List<Producto> todosLosProductos = productoService.obtenerTodos();
            model.addAttribute("productos", todosLosProductos);

            // Filtrado por categoría para las pestañas de la carta en el index
            List<Producto> entrantes = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 1)
                    .collect(Collectors.toList());

            List<Producto> principales = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 2)
                    .collect(Collectors.toList());

            List<Producto> postres = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 3)
                    .collect(Collectors.toList());

            List<Producto> bebidas = todosLosProductos.stream()
                    .filter(p -> p.getTipoProductoId() == 4)
                    .collect(Collectors.toList());

            model.addAttribute("entrantes", entrantes);
            model.addAttribute("principales", principales);
            model.addAttribute("postres", postres);
            model.addAttribute("bebidas", bebidas);

            return "index";
        } catch (Exception e) {
            System.out.println("Error al cargar la carta en el inicio: " + e.getMessage());
            return "index";
        }
    }
}
