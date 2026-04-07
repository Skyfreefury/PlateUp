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

    // Límite máximo de mesas físicas en el restaurante
    private final int LIMITE_MESAS = 20;

    public List<Mesa> obtenerTodas() {
        return mesaDAO.findAll();
    }

    public Mesa obtenerPorId(Long id) {
        return mesaDAO.findById(id).orElse(null);
    }

    public boolean guardarMesa(Mesa mesa) {
    if (mesa.getId() != null) {
        mesaDAO.saveAndFlush(mesa); // Usamos saveAndFlush para sincronizar ya
        return true;
    }

    List<Long> idsOcupados = mesaDAO.findAll().stream().map(Mesa::getId).toList();
    
    for (long i = 1; i <= LIMITE_MESAS; i++) {
        if (!idsOcupados.contains(i)) {
            mesa.setId(i);
            mesa.setNumero((int) i);
            
            // 🚨 GUARDADO FORZADO Y LIMPIO
            mesaDAO.saveAndFlush(mesa); 
            return true; 
        }
    }
    return false; 
}

    public void borrarMesa(Long id) {
        mesaDAO.deleteById(id);
    }
}