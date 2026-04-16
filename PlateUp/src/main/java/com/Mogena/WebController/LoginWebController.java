package com.Mogena.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador para la página de login.
 * Spring Security gestiona el procesamiento del formulario (POST /login) de forma automática;
 * este controlador solo se encarga de renderizar la vista.
 */
@Controller
public class LoginWebController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
