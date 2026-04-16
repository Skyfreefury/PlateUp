package com.Mogena.Repository;

import com.Mogena.Model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio JPA para la entidad {@link com.Mogena.Model.Comanda}.
 * Spring Data genera automáticamente la implementación SQL a partir del nombre de los métodos.
 */
@Repository
public interface ComandaDAO extends JpaRepository<Comanda, Long> {

    /** Devuelve todas las comandas asociadas a un pedido concreto. */
    List<Comanda> findByPedidoId(Long pedidoId);
}
