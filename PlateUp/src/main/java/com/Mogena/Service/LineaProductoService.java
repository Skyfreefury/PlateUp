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
        return lineaProductoDAO.findById(id).orElse(null);
    }

    public boolean guardarLinea(LineaProducto linea) {
        lineaProductoDAO.save(linea);
        return true;
    }

    public boolean actualizarLinea(LineaProducto linea) {
        if (linea.getId() == null) return false;
        lineaProductoDAO.save(linea);
        return true;
    }

    public boolean borrarLinea(Long id) {
        lineaProductoDAO.deleteById(id);
        return true;
    }
}
