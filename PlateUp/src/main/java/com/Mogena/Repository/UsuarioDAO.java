package com.Mogena.Repository;

import com.Mogena.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link com.Mogena.Model.Usuario}.
 * Spring Data genera automáticamente la implementación SQL a partir del nombre de los métodos.
 */
@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    /** Busca un usuario por su nombre de usuario. Usado en el proceso de autenticación. */
    Optional<Usuario> findByUsername(String username);
}
