/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.Mesa;
import java.util.List;

public interface MesaDAO {
    List<Mesa> findAll();
    Mesa findById(Long id);
    int save(Mesa mesa);
    int update(Mesa mesa);
    int delete(Long id);
}
