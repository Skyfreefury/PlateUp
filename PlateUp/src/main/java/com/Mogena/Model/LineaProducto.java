package com.Mogena.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad que representa una línea de producto dentro de una comanda.
 * Registra la cantidad pedida y el subtotal calculado (precio × cantidad)
 * de un producto concreto asociado a una comanda.
 */
@Entity
@Table(name = "lineas_producto")
public class LineaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de la comanda es obligatorio")
    @Column(name = "comanda_id")
    private Long comandaId;

    @NotNull(message = "El ID del producto es obligatorio")
    @Column(name = "producto_id")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @Min(value = 0, message = "El subtotal no puede ser negativo")
    private Double subtotal;

    public LineaProducto() {}

    public LineaProducto(Long id, Long comandaId, Long productoId, Integer cantidad, Double subtotal) {
        this.id = id;
        this.comandaId = comandaId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getComandaId() { return comandaId; }
    public void setComandaId(Long comandaId) { this.comandaId = comandaId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
