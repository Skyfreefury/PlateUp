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
        return productoDAO.findById(id);
    }

    public boolean guardarProducto(Producto producto) {
        return productoDAO.save(producto) > 0;
    }

    public boolean actualizarProducto(Producto producto) {
        if (producto.getId() == null) return false;
        return productoDAO.update(producto) > 0;
    }

    public boolean borrarProducto(Long id) {
        return productoDAO.delete(id) > 0;
    }
}
