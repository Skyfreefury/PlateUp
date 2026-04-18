package com.Mogena.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 * Entidad que representa una mesa física del restaurante.
 *
 * <p>El ID se asigna manualmente mediante reciclaje de huecos en {@link com.Mogena.Service.MesaService},
 * por lo que no usa {@code @GeneratedValue}. El ID coincide con el número de mesa.
 */
@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    private Long id;

    /** Número visible de la mesa (Mesa 1, Mesa 2…). Coincide con el ID. */
    private Integer numero;

    /** Número máximo de comensales que admite la mesa. */
    private Integer capacidad;

    /** Estado actual de la mesa: {@code LIBRE}, {@code OCUPADA} o {@code RESERVADA}. */
    private String estado;

    /** Zona del restaurante donde se ubica la mesa (ej. Interior, Terraza). */
    private String ubicacion;

    /** Cliente asignado a esta mesa. Se limpia automáticamente al cobrar la cuenta. */
    @Column(name = "cliente_id")
    private Long clienteId;

    /** Fecha de la reserva activa en esta mesa. */
    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    /** Hora de la reserva activa (ej. "21:00"). */
    @Column(name = "hora_reserva")
    private String horaReserva;

    public Mesa() {}

    public Mesa(Long id, Integer numero, Integer capacidad, String estado, String ubicacion) {
        this.id = id;
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getHoraReserva() { return horaReserva; }
    public void setHoraReserva(String horaReserva) { this.horaReserva = horaReserva; }
}
