/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Mogena.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad que representa un tipo de comanda (p. ej. "Mesa", "Barra", "Para llevar").
 * Se usa para clasificar las comandas y determinar cómo se gestionan en sala.
 */
@Entity
@Table(name = "tipos_comanda")
public class TipoComanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre descriptivo del tipo de comanda. No puede estar vacío. */
    @NotBlank(message = "El nombre del tipo de comanda no puede estar vacío")
    private String nombre;

    public TipoComanda() {}

    public TipoComanda(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
