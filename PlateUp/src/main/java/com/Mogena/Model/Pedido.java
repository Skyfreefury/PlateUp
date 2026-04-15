package com.Mogena.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    private Double total;
    
    private String estado; // ABIERTA, CERRADA

    @Column(name = "sesion_id")
    private Long sesionId;

    // NUEVO: El número de ticket de cara al cliente y camarero
    @Column(name = "numero_ticket")
    private Integer numeroTicket;

    public Pedido() {}

    // ==========================================
    // GETTERS Y SETTERS
    // ==========================================
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

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getSesionId() { return sesionId; }
    public void setSesionId(Long sesionId) { this.sesionId = sesionId; }

    public Integer getNumeroTicket() { return numeroTicket; }
    public void setNumeroTicket(Integer numeroTicket) { this.numeroTicket = numeroTicket; }
}