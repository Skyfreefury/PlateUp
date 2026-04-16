package com.Mogena.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa una sesión de caja (turno de servicio).
 * Agrupa todos los pedidos realizados entre la apertura y el cierre de caja.
 * El {@code montoInicial} es el fondo de caja con el que se empieza el turno.
 */
@Entity
@Table(name = "sesiones")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime apertura;
    private LocalDateTime cierre;

    /** Dinero en caja al inicio del turno (fondo de caja). */
    @Column(name = "monto_inicial")
    private Double montoInicial;

    /** Estado de la sesión: {@code ABIERTA} durante el servicio, {@code CERRADA} al hacer el cierre. */
    private String estado;

    public Sesion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getApertura() { return apertura; }
    public void setApertura(LocalDateTime apertura) { this.apertura = apertura; }

    public LocalDateTime getCierre() { return cierre; }
    public void setCierre(LocalDateTime cierre) { this.cierre = cierre; }

    public Double getMontoInicial() { return montoInicial; }
    public void setMontoInicial(Double montoInicial) { this.montoInicial = montoInicial; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
