package com.Mogena.Model;

import jakarta.persistence.*;

/**
 * Entidad que representa un producto de la carta del restaurante.
 *
 * <p>El ID se asigna manualmente mediante reciclaje de huecos en {@link com.Mogena.Service.ProductoService},
 * por lo que no usa {@code @GeneratedValue}.
 */
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    private Long id;

    private String nombre;
    private Double precio;
    private String descripcion;

    /**
     * Categoría del producto: 1 = Entrante, 2 = Principal, 3 = Postre, 4 = Bebida.
     * Determina en qué sección de la carta aparece y a qué estación se marcha.
     */
    @Column(name = "tipo_producto_id")
    private Long tipoProductoId;

    public Producto() {}

    public Producto(Long id, String nombre, Double precio, String descripcion, Long tipoProductoId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.tipoProductoId = tipoProductoId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Long getTipoProductoId() { return tipoProductoId; }
    public void setTipoProductoId(Long tipoProductoId) { this.tipoProductoId = tipoProductoId; }
}
