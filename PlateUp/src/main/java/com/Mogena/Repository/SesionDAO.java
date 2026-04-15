package com.Mogena.Repository;

import com.Mogena.Model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SesionDAO extends JpaRepository<Sesion, Long> {
    // Busca la sesión que tenga estado "ABIERTA"
    Optional<Sesion> findByEstado(String estado);
}