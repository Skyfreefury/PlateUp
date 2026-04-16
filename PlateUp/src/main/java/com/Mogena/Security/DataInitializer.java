package com.Mogena.Security;

import com.Mogena.Model.Usuario;
import com.Mogena.Repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Si no hay ningún usuario, crea el admin por defecto
        if (usuarioDAO.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Administrador");
            admin.setRol("ADMIN");
            usuarioDAO.save(admin);
            System.out.println(">>> Usuario por defecto creado: admin / admin123");
        }
    }
}
