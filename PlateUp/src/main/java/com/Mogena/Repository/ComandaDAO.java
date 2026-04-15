/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComandaDAO extends JpaRepository<Comanda, Long> {
    // MAGIA DE SPRING BOOT: Con solo escribir esto, Spring crea la consulta SQL por debajo
    List<Comanda> findByPedidoId(Long pedidoId);
}