/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.TipoProducto;
import com.Mogena.Repository.TipoProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoProductoService {

    @Autowired
    private TipoProductoDAO tipoProductoDAO;

    public List<TipoProducto> obtenerTodos() {
        return tipoProductoDAO.findAll();
    }

    public TipoProducto obtenerPorId(Long id) {
        return tipoProductoDAO.findById(id);
    }

    public boolean guardarTipoProducto(TipoProducto tipoProducto) {
        return tipoProductoDAO.save(tipoProducto) > 0;
    }

    public boolean actualizarTipoProducto(TipoProducto tipoProducto) {
        if (tipoProducto.getId() == null) return false;
        return tipoProductoDAO.update(tipoProducto) > 0;
    }

    public boolean borrarTipoProducto(Long id) {
        return tipoProductoDAO.delete(id) > 0;
    }
}