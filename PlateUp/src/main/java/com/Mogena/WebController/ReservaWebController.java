package com.Mogena.WebController;

import com.Mogena.Model.Cliente;
import com.Mogena.Model.Mesa;
import com.Mogena.Service.ClienteService;
import com.Mogena.Service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para el formulario de reservas del index público.
 * Crea el cliente si es nuevo, busca la mesa libre más ajustada
 * en capacidad y la pone en estado RESERVADA.
 */
@RestController
@RequestMapping("/reservas")
public class ReservaWebController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MesaService mesaService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearReserva(
            @RequestParam String nombre,
            @RequestParam(required = false, defaultValue = "") String telefono,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(defaultValue = "2") int comensales,
            @RequestParam String fecha,
            @RequestParam String hora) {

        try {
            // Buscar mesa libre con capacidad suficiente (la más pequeña que encaje)
            Optional<Mesa> mesaOpt = mesaService.obtenerTodas().stream()
                .filter(m -> "LIBRE".equals(m.getEstado())
                          && m.getCapacidad() != null
                          && m.getCapacidad() >= comensales)
                .min(Comparator.comparingInt(Mesa::getCapacidad));

            if (mesaOpt.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                    "exito", false,
                    "mensaje", "No hay mesas disponibles para " + comensales + " personas. Llámenos al +34 91 000 00 00."
                ));
            }

            // Reutilizar cliente existente por email o crear uno nuevo
            Cliente cliente = (!email.isBlank()) ? clienteService.obtenerPorEmail(email) : null;
            if (cliente == null) {
                cliente = new Cliente();
                cliente.setNombre(nombre);
                if (!telefono.isBlank()) cliente.setTelefono(telefono);
                if (!email.isBlank())    cliente.setEmail(email);
                clienteService.guardarCliente(cliente);
            }

            // Asignar mesa y guardar reserva
            Mesa mesa = mesaOpt.get();
            mesa.setClienteId(cliente.getId());
            mesa.setEstado("RESERVADA");
            mesa.setFechaReserva(LocalDate.parse(fecha));
            mesa.setHoraReserva(hora);
            mesaService.guardarMesa(mesa);

            return ResponseEntity.ok(Map.of(
                "exito",  true,
                "mesa",   mesa.getNumero(),
                "mensaje", "Reserva confirmada — " + fecha + " a las " + hora
            ));

        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "exito",   false,
                "mensaje", "Error al procesar la reserva: " + e.getMessage()
            ));
        }
    }
}
