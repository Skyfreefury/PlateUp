/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.TipoProducto;
import java.util.List;

public interface TipoProductoDAO {
    List<TipoProducto> findAll();
    TipoProducto findById(Long id);
    int save(TipoProducto tipoProducto);
    int update(TipoProducto tipoProducto);
    int delete(Long id);
}
