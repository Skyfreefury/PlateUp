/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface MesaDAO extends JpaRepository<Mesa, Long> {

    /**
     * Comprueba si existe otra mesa (distinta a {@code excludeId}) reservada para el mismo
     * cliente y fecha. Se usa al editar una reserva para no comparar la mesa consigo misma.
     */
    boolean existsByClienteIdAndFechaReservaAndEstadoAndIdNot(Long clienteId, LocalDate fechaReserva, String estado, Long excludeId);

    /**
     * Comprueba si ya existe una mesa reservada para el mismo cliente y fecha.
     * Se usa al crear una reserva nueva (sin ID previo).
     */
    boolean existsByClienteIdAndFechaReservaAndEstado(Long clienteId, LocalDate fechaReserva, String estado);
}