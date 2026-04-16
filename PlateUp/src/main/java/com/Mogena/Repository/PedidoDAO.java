package com.Mogena.Repository;

import com.Mogena.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link com.Mogena.Model.Pedido}.
 * Spring Data genera automáticamente la implementación SQL a partir del nombre de los métodos.
 */
@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Long> {

    /** Devuelve todos los pedidos pertenecientes a una sesión de caja. */
    List<Pedido> findBySesionId(Long sesionId);

    /**
     * Devuelve el pedido con el número de ticket más alto dentro de una sesión.
     * Se usa para calcular el número correlativo del siguiente ticket.
     */
    Optional<Pedido> findTopBySesionIdOrderByNumeroTicketDesc(Long sesionId);
}
