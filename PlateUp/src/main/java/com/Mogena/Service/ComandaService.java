package com.Mogena.Service;

import com.Mogena.Model.Comanda;
import com.Mogena.Repository.ComandaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de comandas (platos y bebidas marchados a cocina o barra).
 * Implementa un patrón de reciclaje de IDs para reutilizar los huecos que dejan
 * las comandas eliminadas, evitando que el contador de IDs crezca indefinidamente.
 */
@Service
public class ComandaService {

    @Autowired
    private ComandaDAO comandaDAO;

    /** Número máximo de comandas activas simultáneas en el sistema. */
    private final int LIMITE_COMANDAS = 1000;

    /** Devuelve todas las comandas almacenadas en la base de datos. */
    public List<Comanda> obtenerTodas() {
        return comandaDAO.findAll();
    }

    /** Devuelve la comanda con el ID indicado, o {@code null} si no existe. */
    public Comanda obtenerPorId(Long id) {
        Optional<Comanda> o = comandaDAO.findById(id);
        return o.orElse(null);
    }

    /**
     * Persiste una comanda nueva o actualiza una existente.
     * Si la comanda ya tiene ID, se actualiza directamente.
     * Si es nueva, se busca el primer ID libre (hueco) entre 1 y {@code LIMITE_COMANDAS}.
     *
     * @return {@code true} si se guardó correctamente, {@code false} si no hay IDs disponibles.
     */
    @Transactional
    public boolean guardarComanda(Comanda comanda) {
        if (comanda.getId() != null) {
            comandaDAO.saveAndFlush(comanda);
            return true;
        }

        List<Long> idsOcupados = comandaDAO.findAll().stream()
                                           .map(Comanda::getId)
                                           .toList();

        for (long i = 1; i <= LIMITE_COMANDAS; i++) {
            if (!idsOcupados.contains(i)) {
                comanda.setId(i);
                comandaDAO.saveAndFlush(comanda);
                return true;
            }
        }
        return false;
    }

    /** Elimina la comanda con el ID indicado si existe. */
    @Transactional
    public void borrarComanda(Long id) {
        if (comandaDAO.existsById(id)) {
            comandaDAO.deleteById(id);
            comandaDAO.flush();
        }
    }

    /** Devuelve todas las comandas asociadas a un pedido concreto. */
    public List<Comanda> obtenerPorPedidoId(Long pedidoId) {
        return comandaDAO.findByPedidoId(pedidoId);
    }
}
