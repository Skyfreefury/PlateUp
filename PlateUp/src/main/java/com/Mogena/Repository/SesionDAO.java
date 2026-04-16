package com.Mogena.Repository;

import com.Mogena.Model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link com.Mogena.Model.Sesion}.
 * Spring Data genera automáticamente la implementación SQL a partir del nombre de los métodos.
 */
@Repository
public interface SesionDAO extends JpaRepository<Sesion, Long> {

    /** Busca la sesión con el estado indicado. Se usa para encontrar la sesión activa ({@code ABIERTA}). */
    Optional<Sesion> findByEstado(String estado);
}
