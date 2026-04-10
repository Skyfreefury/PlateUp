/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Table(name = "comandas")
public class Comanda {

    @Id
    // Nota: Dejo el ID sin @GeneratedValue asumiendo que controlas la inserción manualmente
    // como hiciste en Producto. Si la BD lo auto-genera, puedes añadir @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id")
    private Long pedidoId;

    private String estado;
    
    private String comentarios;
    
    @Column(name = "nombre_plato")
    private String nombrePlato; 
    
    private Integer cantidad;

    // ✨ CAMPO CLAVE PARA EL PANEL DE COCINA (Filtra por Entrante, Principal, Postre)
    @Column(name = "tipo_comanda_id")
    private Long tipoComandaId;

    // ✨ CAMPO CLAVE PARA LOS TEMPORIZADORES
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    // Constructor vacío obligatorio para JPA
    public Comanda() {}

    // TRUCO MÁGICO: Este método se ejecuta automáticamente justo antes de guardar en MySQL
    @PrePersist
    public void preGuardar() {
        if (this.estado == null || this.estado.isEmpty()) {
            this.estado = "EN_PREPARACION";
        }
        if (this.creadoEn == null) {
            this.creadoEn = LocalDateTime.now();
        }
    }

    // ==========================================
    //          GETTERS Y SETTERS
    // ==========================================

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public Long getPedidoId() { 
        return pedidoId; 
    }
    
    public void setPedidoId(Long pedidoId) { 
        this.pedidoId = pedidoId; 
    }

    public String getEstado() { 
        return estado; 
    }
    
    public void setEstado(String estado) { 
        this.estado = estado; 
    }

    public String getComentarios() { 
        return comentarios; 
    }
    
    public void setComentarios(String comentarios) { 
        this.comentarios = comentarios; 
    }

    public String getNombrePlato() { 
        return nombrePlato; 
    }
    
    public void setNombrePlato(String nombrePlato) { 
        this.nombrePlato = nombrePlato; 
    }

    public Integer getCantidad() { 
        return cantidad; 
    }
    
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; 
    }

    public Long getTipoComandaId() { 
        return tipoComandaId; 
    }
    
    public void setTipoComandaId(Long tipoComandaId) { 
        this.tipoComandaId = tipoComandaId; 
    }

    public LocalDateTime getCreadoEn() { 
        return creadoEn; 
    }
    
    public void setCreadoEn(LocalDateTime creadoEn) { 
        this.creadoEn = creadoEn; 
    }
}
