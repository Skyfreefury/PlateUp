package com.Mogena.PlateUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.Mogena") // Busca los @Service y @Controller
@EntityScan(basePackages = "com.Mogena.Model") // Busca los @Entity (las tablas)
@EnableJpaRepositories(basePackages = "com.Mogena.Repository") // Busca los DAO de JPA
public class PlateUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlateUpApplication.class, args);
    }

}
