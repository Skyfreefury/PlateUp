package com.Mogena.Repository;

import com.Mogena.Model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Long> {
    
    // Busca todos los pedidos de una sesión concreta
    List<Pedido> findBySesionId(Long sesionId);
    
    // Busca el último ticket generado en una sesión específica
    Optional<Pedido> findTopBySesionIdOrderByNumeroTicketDesc(Long sesionId);
}