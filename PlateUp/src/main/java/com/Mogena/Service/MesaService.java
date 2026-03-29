/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Mesa;
import com.Mogena.Repository.MesaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    @Autowired
    private MesaDAO mesaDAO;

    public List<Mesa> obtenerTodas() {
        return mesaDAO.findAll();
    }

    public Mesa obtenerPorId(Long id) {
        return mesaDAO.findById(id);
    }

    public boolean guardarMesa(Mesa mesa) {
        return mesaDAO.save(mesa) > 0;
    }

    public boolean actualizarMesa(Mesa mesa) {
        if (mesa.getId() == null) return false;
        return mesaDAO.update(mesa) > 0;
    }

    public boolean borrarMesa(Long id) {
        return mesaDAO.delete(id) > 0;
    }
}