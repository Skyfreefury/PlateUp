package com.Mogena.Service;

import com.Mogena.Model.Producto;
import com.Mogena.Repository.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de los productos de la carta del restaurante.
 * Implementa un patrón de reciclaje de IDs para reutilizar los huecos que dejan
 * los productos eliminados, evitando que el contador de IDs crezca indefinidamente.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoDAO productoDAO;

    /** Número máximo de productos en la carta. */
    private final int LIMITE_PRODUCTOS = 500;

    /** Devuelve todos los productos de la carta. */
    public List<Producto> obtenerTodos() {
        return productoDAO.findAll();
    }

    /** Devuelve el producto con el ID indicado, o {@code null} si no existe. */
    public Producto obtenerPorId(Long id) {
        Optional<Producto> o = productoDAO.findById(id);
        return o.orElse(null);
    }

    /**
     * Persiste un producto nuevo o actualiza uno existente.
     * Si el producto ya tiene ID, se actualiza directamente.
     * Si es nuevo, se busca el primer ID libre (hueco) entre 1 y {@code LIMITE_PRODUCTOS}.
     *
     * @return {@code true} si se guardó correctamente, {@code false} si la carta está llena.
     */
    @Transactional
    public boolean guardarProducto(Producto producto) {
        if (producto.getId() != null) {
            productoDAO.saveAndFlush(producto);
            return true;
        }

        List<Long> idsOcupados = productoDAO.findAll().stream()
                                            .map(Producto::getId)
                                            .toList();

        for (long i = 1; i <= LIMITE_PRODUCTOS; i++) {
            if (!idsOcupados.contains(i)) {
                producto.setId(i);
                productoDAO.saveAndFlush(producto);
                return true;
            }
        }

        return false;
    }

    /** Elimina el producto con el ID indicado si existe. */
    @Transactional
    public void borrarProducto(Long id) {
        if (productoDAO.existsById(id)) {
            productoDAO.deleteById(id);
            productoDAO.flush();
        }
    }

    /**
     * Busca un producto por su nombre exacto.
     * Se usa para calcular el precio al recalcular el total de un pedido.
     *
     * @return el producto encontrado, o {@code null} si el nombre es nulo, vacío o no existe.
     */
    public Producto obtenerPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) return null;
        return productoDAO.findByNombre(nombre);
    }
}
