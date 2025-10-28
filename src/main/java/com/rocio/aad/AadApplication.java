package com.rocio.aad;

import com.rocio.aad.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

/**
 * Clase principal de la aplicacion de gestion de logs.
 * Esta clase sirve como punto de entrada y ejemplo de uso de los servicios creados.
 * <p>
 * Demostracion:
 * Añadir algunos eventos al log.
 * Mostrar el filtrado de logs por fecha.
 * Cambiar la codificacion del fichero
 */

@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

    /**
     * Servicio principal para la gestion de los logs
     */
    private final LogService logService;

    /**
     * Constructor con inyeccion de dependencias
     *
     * @param logService Servicio de gestion de logs
     */
    public AadApplication(LogService logService) {
        this.logService = logService;
    }

    /**
     * Punto de entrada de la aplicacion.
     *
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    /**
     * Método que se ejecuta al iniciar la aplicacion.
     * Tiene una demostracion de uso de los servicios de gestion de logs.
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Application starte - Log Manager Demo.");

        //Añadir ejemplos de logs
        logService.addEvent("User Ana logged in");
        logService.addEvent("User Carlos updated profile");
        logService.addEvent("System backup completed successfully");

        //Filtrar por fecha actual
        LocalDate today = LocalDate.now();
        logService.filterByDate(today);

        //Cambiar codificacion a ISO y registrar un evento nuevo.
        logService.useIso88591();
        logService.addEvent("Test entry written with ISO-8859-1 encoding");

        //Volver a UTF-8
        logService.useUtf8();
        logService.addEvent("Encoding switched back to UTF-8");

        log.info("Demo completed. Check 'app.log' for output.");

    }
}
