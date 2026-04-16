/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.TipoComanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link TipoComanda}.
 * Spring Data genera automáticamente las operaciones CRUD básicas (findAll, findById, save, deleteById, etc.).
 */
@Repository
public interface TipoComandaDAO extends JpaRepository<TipoComanda, Long> {
}
