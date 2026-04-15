package com.Mogena.Service;

import com.Mogena.Model.Sesion;
import com.Mogena.Repository.SesionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SesionService {

    @Autowired
    private SesionDAO sesionDAO;

    public Sesion obtenerSesionActiva() {
        return sesionDAO.findByEstado("ABIERTA").orElse(null);
    }

    public Sesion abrirSesion(Double montoInicial) {
        if (obtenerSesionActiva() != null) return null; // Ya hay una abierta
        
        Sesion s = new Sesion();
        s.setApertura(LocalDateTime.now());
        s.setMontoInicial(montoInicial);
        s.setEstado("ABIERTA");
        return sesionDAO.save(s);
    }

    public void cerrarSesion(Sesion s) {
        s.setCierre(LocalDateTime.now());
        s.setEstado("CERRADA");
        sesionDAO.save(s);
    }
}