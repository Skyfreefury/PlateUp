/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Comanda;
import com.Mogena.Repository.ComandaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComandaService {

    @Autowired
    private ComandaDAO comandaDAO;

    public List<Comanda> obtenerTodas() {
        return comandaDAO.findAll();
    }

    public Comanda obtenerPorId(Long id) {
        return comandaDAO.findById(id);
    }

    public boolean guardarComanda(Comanda comanda) {
        return comandaDAO.save(comanda) > 0;
    }

    public boolean actualizarComanda(Comanda comanda) {
        if (comanda.getId() == null) return false;
        return comandaDAO.update(comanda) > 0;
    }

    public boolean borrarComanda(Long id) {
        return comandaDAO.delete(id) > 0;
    }
}
