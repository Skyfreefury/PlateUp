package com.Mogena.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

/**
 * Entidad que representa una línea de comanda: un plato o bebida marchado a cocina o barra.
 * Cada comanda pertenece a un pedido y tiene un tipo que indica su destino de preparación
 * (Entrante, Principal, Postre o Bebida).
 *
 * <p>El ID se asigna manualmente mediante reciclaje de huecos en {@link com.Mogena.Service.ComandaService},
 * por lo que no usa {@code @GeneratedValue}.
 */
@Entity
@Table(name = "comandas")
public class Comanda {

    @Id
    private Long id;

    /** Clave foránea al pedido al que pertenece esta comanda. */
    @Column(name = "pedido_id")
    private Long pedidoId;

    /** Estado de preparación: {@code PENDIENTE}, {@code EN_PREPARACION} o {@code LISTO}. */
    private String estado;

    private String comentarios;

    @Column(name = "nombre_plato")
    private String nombrePlato;

    private Integer cantidad;

    /**
     * Tipo de comanda que determina el destino de preparación:
     * 1 = Entrante (Cocina), 2 = Principal (Cocina), 3 = Postre (Cocina), 4 = Bebida (Barra).
     */
    @Column(name = "tipo_comanda_id")
    private Long tipoComandaId;

    /** Marca temporal de cuándo se marchó la comanda, usada para temporizadores en cocina. */
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    public Comanda() {}

    /**
     * Se ejecuta automáticamente antes de persistir la entidad en la base de datos.
     * Establece el estado inicial y la marca temporal si no han sido asignados.
     */
    @PrePersist
    public void preGuardar() {
        if (this.estado == null || this.estado.isEmpty()) {
            this.estado = "EN_PREPARACION";
        }
        if (this.creadoEn == null) {
            this.creadoEn = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }

    public String getNombrePlato() { return nombrePlato; }
    public void setNombrePlato(String nombrePlato) { this.nombrePlato = nombrePlato; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Long getTipoComandaId() { return tipoComandaId; }
    public void setTipoComandaId(Long tipoComandaId) { this.tipoComandaId = tipoComandaId; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
