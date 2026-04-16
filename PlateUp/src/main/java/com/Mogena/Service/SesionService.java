package com.Mogena.Service;

import com.Mogena.Model.Sesion;
import com.Mogena.Repository.SesionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Servicio para la gestión de sesiones de caja (turnos de servicio).
 * Solo puede existir una sesión con estado {@code ABIERTA} al mismo tiempo.
 */
@Service
public class SesionService {

    @Autowired
    private SesionDAO sesionDAO;

    /** Devuelve la sesión actualmente abierta, o {@code null} si la caja está cerrada. */
    public Sesion obtenerSesionActiva() {
        return sesionDAO.findByEstado("ABIERTA").orElse(null);
    }

    /**
     * Abre una nueva sesión de caja con el fondo inicial indicado.
     * Devuelve {@code null} si ya hay una sesión abierta o si el monto inicial es negativo o nulo.
     */
    public Sesion abrirSesion(Double montoInicial) {
        if (montoInicial == null || montoInicial < 0) return null;
        if (obtenerSesionActiva() != null) return null;

        Sesion s = new Sesion();
        s.setApertura(LocalDateTime.now());
        s.setMontoInicial(montoInicial);
        s.setEstado("ABIERTA");
        return sesionDAO.save(s);
    }

    /** Cierra la sesión indicada registrando la hora de cierre. */
    public void cerrarSesion(Sesion s) {
        s.setCierre(LocalDateTime.now());
        s.setEstado("CERRADA");
        sesionDAO.save(s);
    }
}
