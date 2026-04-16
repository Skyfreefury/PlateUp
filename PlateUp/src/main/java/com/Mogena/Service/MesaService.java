package com.Mogena.Service;

import com.Mogena.Model.Mesa;
import com.Mogena.Repository.MesaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de las mesas del restaurante.
 * Implementa un patrón de reciclaje de IDs para reutilizar los huecos que dejan
 * las mesas eliminadas. El ID de la mesa coincide con su número visible.
 */
@Service
public class MesaService {

    @Autowired
    private MesaDAO mesaDAO;

    /** Número máximo de mesas físicas en el restaurante. */
    private final int LIMITE_MESAS = 20;

    /** Devuelve todas las mesas del restaurante. */
    public List<Mesa> obtenerTodas() {
        return mesaDAO.findAll();
    }

    /** Devuelve la mesa con el ID indicado, o {@code null} si no existe. */
    public Mesa obtenerPorId(Long id) {
        return mesaDAO.findById(id).orElse(null);
    }

    /**
     * Persiste una mesa nueva o actualiza una existente.
     * Si la mesa ya tiene ID, se actualiza directamente.
     * Si es nueva, se busca el primer ID libre (hueco) entre 1 y {@code LIMITE_MESAS}.
     * El número de mesa se establece igual al ID asignado.
     *
     * @return {@code true} si se guardó correctamente, {@code false} si no hay IDs disponibles.
     */
    public boolean guardarMesa(Mesa mesa) {
        if (mesa.getId() != null) {
            mesaDAO.saveAndFlush(mesa);
            return true;
        }

        List<Long> idsOcupados = mesaDAO.findAll().stream().map(Mesa::getId).toList();

        for (long i = 1; i <= LIMITE_MESAS; i++) {
            if (!idsOcupados.contains(i)) {
                mesa.setId(i);
                mesa.setNumero((int) i);
                // Guardado inmediato para sincronizar el ID con la base de datos
                mesaDAO.saveAndFlush(mesa);
                return true;
            }
        }
        return false;
    }

    /** Elimina la mesa con el ID indicado. No hace nada si no existe. */
    public void borrarMesa(Long id) {
        if (mesaDAO.existsById(id)) {
            mesaDAO.deleteById(id);
        }
    }
}
