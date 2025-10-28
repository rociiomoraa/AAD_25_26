package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Esta clase representa un evento registrado en el log de la aplicacion.
 * Cada evento contiene la fecha y hora del evento y un mensaje descriptivo.
 * <p>
 * Esta clase utiliza las anotaciones de Lombok para generar automaticamente
 * los metodos getters, setters, constructores y otros metodos utilitarios.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent {

    // Fecha y hora del evento
    private LocalDateTime timestamp;

    //Mensaje del evento
    private String message;
}
