/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Producto;
import com.Mogena.Repository.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoDAO productoDAO;

    public List<Producto> obtenerTodos() {
        return productoDAO.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoDAO.findById(id).orElse(null);
    }

    public boolean guardarProducto(Producto producto) {
        productoDAO.save(producto);
        return true;
    }

    public boolean actualizarProducto(Producto producto) {
        if (producto.getId() == null) return false;
        productoDAO.save(producto);
        return true;
    }

    public boolean borrarProducto(Long id) {
        productoDAO.deleteById(id);
        return true;
    }
}
