/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Mogena.Repository;

import com.Mogena.Model.TipoComanda;
import java.util.List;

public interface TipoComandaDAO {
    List<TipoComanda> findAll();
    TipoComanda findById(Long id);
    int save(TipoComanda tipoComanda);
    int update(TipoComanda tipoComanda);
    int delete(Long id);
}
