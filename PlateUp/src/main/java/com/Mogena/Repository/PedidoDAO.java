/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Pedido;
import java.util.List;

public interface PedidoDAO {
    List<Pedido> findAll();
    Pedido findById(Long id);
    int save(Pedido pedido);
    int update(Pedido pedido);
    int delete(Long id);
}
