package com.Mogena.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que representa un pedido (cuenta de mesa) en el restaurante.
 * Cada pedido pertenece a una sesión de caja, está asignado a una mesa
 * y puede estar en estado {@code ABIERTA} o {@code CERRADA}.
 *
 * <p>El {@code numeroTicket} es el número correlativo visible para el personal,
 * reiniciándose en cada sesión de caja.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mesa_id", nullable = false)
    private Long mesaId;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    /** Importe total calculado sumando precio × cantidad de cada comanda asociada. */
    private Double total;

    /** Parte del total abonada en metálico al cerrar la cuenta. */
    @Column(name = "pago_efectivo")
    private Double pagoEfectivo;

    /** Parte del total abonada con tarjeta al cerrar la cuenta. */
    @Column(name = "pago_tarjeta")
    private Double pagoTarjeta;

    /** Estado del pedido: {@code ABIERTA} mientras se sirve, {@code CERRADA} tras el cobro. */
    private String estado;

    /** Clave foránea a la sesión de caja en la que se abrió este pedido. */
    @Column(name = "sesion_id")
    private Long sesionId;

    /** Número de ticket correlativo dentro de la sesión, visible para el personal de sala. */
    @Column(name = "numero_ticket")
    private Integer numeroTicket;

    /** Mesa anterior antes del último cambio. Si es null, la mesa nunca se ha cambiado. */
    @Column(name = "mesa_anterior_id")
    private Long mesaAnteriorId;

    public Pedido() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Double getPagoEfectivo() { return pagoEfectivo; }
    public void setPagoEfectivo(Double pagoEfectivo) { this.pagoEfectivo = pagoEfectivo; }

    public Double getPagoTarjeta() { return pagoTarjeta; }
    public void setPagoTarjeta(Double pagoTarjeta) { this.pagoTarjeta = pagoTarjeta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getSesionId() { return sesionId; }
    public void setSesionId(Long sesionId) { this.sesionId = sesionId; }

    public Integer getNumeroTicket() { return numeroTicket; }
    public void setNumeroTicket(Integer numeroTicket) { this.numeroTicket = numeroTicket; }

    public Long getMesaAnteriorId() { return mesaAnteriorId; }
    public void setMesaAnteriorId(Long mesaAnteriorId) { this.mesaAnteriorId = mesaAnteriorId; }
}
