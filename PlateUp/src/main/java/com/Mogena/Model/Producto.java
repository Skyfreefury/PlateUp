/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    // 🚨 Sin @GeneratedValue para que funcione tu lógica de reciclaje de IDs
    private Long id;

    private String nombre;
    private Double precio;
    private String descripcion;

    @Column(name = "tipo_producto_id")
    private Long tipoProductoId; // Este es el ID de la categoría (1, 2, 3 o 4)

    public Producto() {}

    public Producto(Long id, String nombre, Double precio, String descripcion, Long tipoProductoId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.tipoProductoId = tipoProductoId;
    }

    // --- Getters y Setters ---
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