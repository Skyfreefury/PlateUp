/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    // 🚨 HEMOS QUITADO @GeneratedValue para que funcione tu sistema de reciclaje manual
    private Long id;

    private Integer numero;    // El número físico de la mesa (Mesa 1, Mesa 2...)
    private Integer capacidad; // Cuánta gente cabe
    private String estado;     // LIBRE, OCUPADA, RESERVADA
    private String ubicacion;  // Interior, Terraza...

    // Constructor vacío (Obligatorio para Hibernate)
    public Mesa() {
    }

    // Constructor con campos (Opcional, útil para pruebas)
    public Mesa(Long id, Integer numero, Integer capacidad, String estado, String ubicacion) {
        this.id = id;
        this.numero = numero;
        this.capacidad = capacidad;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    // --- GETTERS Y SETTERS (Fundamentales para que el Formulario funcione) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}