package com.Mogena.PlateUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Clase principal de la aplicación PlateUp.
 *
 * <p>Las anotaciones de escaneo explícito son necesarias porque los paquetes
 * de modelos, repositorios y controladores se encuentran fuera del paquete
 * raíz {@code com.Mogena.PlateUp}.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.Mogena")
@EntityScan(basePackages = "com.Mogena.Model")
@EnableJpaRepositories(basePackages = "com.Mogena.Repository")
public class PlateUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlateUpApplication.class, args);
    }
}
