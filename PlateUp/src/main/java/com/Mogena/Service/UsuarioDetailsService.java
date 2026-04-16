package com.Mogena.Service;

import com.Mogena.Model.Usuario;
import com.Mogena.Repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link UserDetailsService} para la autenticación con Spring Security.
 * Carga el usuario desde la base de datos a partir del nombre de usuario
 * y construye el objeto {@link UserDetails} que Spring Security necesita para verificar credenciales.
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    /**
     * Carga los datos del usuario por su nombre de usuario.
     *
     * @param username nombre de usuario introducido en el formulario de login
     * @throws UsernameNotFoundException si el usuario no existe en la base de datos
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioDAO.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.builder()
            .username(u.getUsername())
            .password(u.getPassword())
            .roles(u.getRol())
            .build();
    }
}
