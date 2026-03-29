/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.LineaProducto;
import com.Mogena.Repository.LineaProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineaProductoService {

    @Autowired
    private LineaProductoDAO lineaProductoDAO;

    public List<LineaProducto> obtenerTodas() {
        return lineaProductoDAO.findAll();
    }

    public LineaProducto obtenerPorId(Long id) {
        return lineaProductoDAO.findById(id);
    }

    public boolean guardarLinea(LineaProducto linea) {
        return lineaProductoDAO.save(linea) > 0;
    }

    public boolean actualizarLinea(LineaProducto linea) {
        if (linea.getId() == null) return false;
        return lineaProductoDAO.update(linea) > 0;
    }

    public boolean borrarLinea(Long id) {
        return lineaProductoDAO.delete(id) > 0;
    }
}
