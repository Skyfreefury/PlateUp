/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Producto;
import java.util.List;

public interface ProductoDAO {
    List<Producto> findAll();
    Producto findById(Long id);
    int save(Producto producto);
    int update(Producto producto);
    int delete(Long id);
}