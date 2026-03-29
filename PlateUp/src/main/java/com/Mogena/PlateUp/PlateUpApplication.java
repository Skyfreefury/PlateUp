package com.Mogena.PlateUp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // Importante

@SpringBootApplication
@ComponentScan(basePackages = "com.Mogena") // 👈 ¡ESTA ES LA MAGIA!
public class PlateUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlateUpApplication.class, args);
    }
}
