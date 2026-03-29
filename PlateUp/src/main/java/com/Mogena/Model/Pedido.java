/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Model;

import java.time.LocalDateTime;

public class Pedido {
    private Long id;
    private Long clienteId; // Clave foránea a Cliente
    private Long mesaId;    // Clave foránea a Mesa
    private LocalDateTime fechaHora;
    private Double total;
    private String estado;

    public Pedido() {}

    public Pedido(Long id, Long clienteId, Long mesaId, LocalDateTime fechaHora, Double total, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.mesaId = mesaId;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getMesaId() { return mesaId; }
    public void setMesaId(Long mesaId) { this.mesaId = mesaId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
