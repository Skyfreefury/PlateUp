package com.Mogena.Model;

import jakarta.persistence.*;

/**
 * Entidad que representa un usuario del panel de administración.
 * Las contraseñas se almacenan cifradas con BCrypt.
 * Spring Security utiliza {@code username} y {@code password} para la autenticación.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    /** Nombre real del empleado, usado para mostrarlo en la interfaz. */
    private String nombre;

    /** Rol del usuario en el sistema: {@code ADMIN} o {@code EMPLEADO}. */
    private String rol;

    public Usuario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
