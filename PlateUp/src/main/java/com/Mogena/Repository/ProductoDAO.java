package com.Mogena.Repository;

import com.Mogena.Model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link com.Mogena.Model.Producto}.
 * Spring Data genera automáticamente la implementación SQL a partir del nombre de los métodos.
 */
@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long> {

    Producto findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);
}
