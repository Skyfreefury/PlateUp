/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.LineaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineaProductoDAO extends JpaRepository<LineaProducto, Long> {
    // ¡Vacío! Spring Data JPA se encarga de todo.
}
