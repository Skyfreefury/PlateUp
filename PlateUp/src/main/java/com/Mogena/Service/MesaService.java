package com.Mogena.Service;

import com.Mogena.Model.Mesa;
import com.Mogena.Repository.MesaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
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
     * <p>Validaciones aplicadas:
     * <ul>
     *   <li>Capacidad entre 1 y 20 comensales.</li>
     *   <li>Estado RESERVADA: fecha obligatoria, no anterior a hoy; si es hoy, hora no anterior a la actual.</li>
     *   <li>Estado RESERVADA con cliente asignado: el mismo cliente no puede tener otra mesa reservada para la misma fecha.</li>
     * </ul>
     *
     * @return {@code true} si se guardó correctamente, {@code false} si no hay IDs disponibles.
     */
    public boolean guardarMesa(Mesa mesa) {
        if (mesa.getCapacidad() == null || mesa.getCapacidad() < 1 || mesa.getCapacidad() > 20)
            throw new IllegalArgumentException("La capacidad debe estar entre 1 y 20 comensales.");

        if ("RESERVADA".equals(mesa.getEstado())) {
            if (mesa.getFechaReserva() == null)
                throw new IllegalArgumentException("Debes indicar la fecha de la reserva.");
            LocalDate hoy = LocalDate.now();
            if (mesa.getFechaReserva().isBefore(hoy))
                throw new IllegalArgumentException("La fecha de reserva no puede ser anterior a hoy.");
            if (mesa.getFechaReserva().isEqual(hoy) && mesa.getHoraReserva() != null && !mesa.getHoraReserva().isBlank()) {
                try {
                    if (LocalTime.parse(mesa.getHoraReserva()).isBefore(LocalTime.now()))
                        throw new IllegalArgumentException("La hora de reserva no puede ser anterior a la hora actual.");
                } catch (DateTimeParseException ignored) {}
            }
            // Un cliente solo puede tener una mesa reservada por día; excluir la propia mesa en edición.
            if (mesa.getClienteId() != null) {
                boolean conflicto = (mesa.getId() != null)
                    ? mesaDAO.existsByClienteIdAndFechaReservaAndEstadoAndIdNot(mesa.getClienteId(), mesa.getFechaReserva(), "RESERVADA", mesa.getId())
                    : mesaDAO.existsByClienteIdAndFechaReservaAndEstado(mesa.getClienteId(), mesa.getFechaReserva(), "RESERVADA");
                if (conflicto)
                    throw new IllegalArgumentException("Este cliente ya tiene una reserva para ese día en otra mesa.");
            }
        }

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
