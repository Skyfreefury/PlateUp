/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Model;

public class Producto {
    private Long id;
    private String nombre;
    private Double precio;
    private Long tipoProductoId; // Clave foránea a TipoProducto
    private Long tipoComandaId;  // Clave foránea a TipoComanda

    public Producto() {}

    public Producto(Long id, String nombre, Double precio, Long tipoProductoId, Long tipoComandaId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipoProductoId = tipoProductoId;
        this.tipoComandaId = tipoComandaId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Long getTipoProductoId() { return tipoProductoId; }
    public void setTipoProductoId(Long tipoProductoId) { this.tipoProductoId = tipoProductoId; }
    public Long getTipoComandaId() { return tipoComandaId; }
    public void setTipoComandaId(Long tipoComandaId) { this.tipoComandaId = tipoComandaId; }
}
