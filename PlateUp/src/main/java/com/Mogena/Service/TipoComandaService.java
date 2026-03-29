/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.TipoComanda;
import com.Mogena.Repository.TipoComandaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoComandaService {

    @Autowired
    private TipoComandaDAO tipoComandaDAO;

    public List<TipoComanda> obtenerTodos() {
        return tipoComandaDAO.findAll();
    }

    public TipoComanda obtenerPorId(Long id) {
        return tipoComandaDAO.findById(id);
    }

    public boolean guardarTipoComanda(TipoComanda tipoComanda) {
        return tipoComandaDAO.save(tipoComanda) > 0;
    }

    public boolean actualizarTipoComanda(TipoComanda tipoComanda) {
        if (tipoComanda.getId() == null) return false;
        return tipoComandaDAO.update(tipoComanda) > 0;
    }

    public boolean borrarTipoComanda(Long id) {
        return tipoComandaDAO.delete(id) > 0;
    }
}
