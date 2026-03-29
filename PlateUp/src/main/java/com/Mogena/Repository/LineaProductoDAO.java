/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.LineaProducto;
import java.util.List;

public interface LineaProductoDAO {
    List<LineaProducto> findAll();
    LineaProducto findById(Long id);
    int save(LineaProducto linea);
    int update(LineaProducto linea);
    int delete(Long id);
}
