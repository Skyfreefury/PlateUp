/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Service;

import com.Mogena.Model.Comanda;
import com.Mogena.Repository.ComandaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {

    @Autowired
    private ComandaDAO comandaDAO;

    private final int LIMITE_COMANDAS = 1000; // Límite alto porque se crean muchas

    // OBTENER TODAS
    public List<Comanda> obtenerTodas() {
        return comandaDAO.findAll();
    }

    // OBTENER POR ID
    public Comanda obtenerPorId(Long id) {
        Optional<Comanda> o = comandaDAO.findById(id);
        return o.orElse(null);
    }

    // GUARDAR O ACTUALIZAR (CON RECICLAJE DE ID)
    @Transactional
    public boolean guardarComanda(Comanda comanda) {
        // Si ya tiene ID (es una edición)
        if (comanda.getId() != null) {
            comandaDAO.saveAndFlush(comanda);
            return true;
        }

        // Si es nueva, buscamos hueco libre
        List<Long> idsOcupados = comandaDAO.findAll().stream()
                                           .map(Comanda::getId)
                                           .toList();
        
        for (long i = 1; i <= LIMITE_COMANDAS; i++) {
            if (!idsOcupados.contains(i)) {
                comanda.setId(i);
                comandaDAO.saveAndFlush(comanda);
                return true;
            }
        }
        return false;
    }

    // BORRAR
    @Transactional
    public void borrarComanda(Long id) {
        if (comandaDAO.existsById(id)) {
            comandaDAO.deleteById(id);
            comandaDAO.flush();
        }
    }
}