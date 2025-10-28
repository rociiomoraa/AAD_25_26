package com.rocio.aad.service;

import com.rocio.aad.model.LogEvent;
import com.rocio.aad.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase se encarga de gestionar los eventos del log de la aplicacion.
 * Este servicio coordina las operaciones entre el repositorio de logs y la configuracion de la codificacion,
 * permitiendo añadir nuevos eventos y filtrar los existentes por fecha.
 * <p>
 * La clase se encarga de:
 * -Añadir un evento al fichero de log con fecha y hora actual.
 * -Filtrar los eventos por una fecha concreta.
 * -Utilizar la codificaciona actual del EncodingService para leer y escribir en el fichero de log.
 */

@Service
@Slf4j
public class LogService {

    /**
     * Repostorio para acceder al fichero de logs
     */
    private final LogRepository logRepository;

    /**
     * Servicio para gestionar la codificacion actual
     */
    private final EncodingService encodingService;

    /**
     * Constructor con inyeccion de dependencias
     *
     * @param logRepository   Repositorio de logs
     * @param encodingService Servicio de codificacion
     */
    public LogService(LogRepository logRepository, EncodingService encodingService) {
        this.logRepository = logRepository;
        this.encodingService = encodingService;
    }

    /**
     * Añade un nuevo evento al fichero de log
     *
     * @param message Mensaje del evento a registrar.
     */
    public void addEvent(String message) {
        try {
            LocalDateTime now = LocalDateTime.now();
            LogEvent event = new LogEvent(now, message);

            Charset charset = encodingService.getCurrentEncoding();
            logRepository.append(event, charset);

            log.info("Log event added successfully: {}", message);

        } catch (Exception e) {
            log.error("Error adding log event: {}", e.getMessage());
        }
    }

    /**
     * Filtra los eventos del fichero por una fecha concreta
     *
     * @param date Fecha para filtrar los eventos
     * @return Lista de eventos ocurridos en la fecha especificada
     */
    public List<String> filterByDate(LocalDate date) {
        try {
            Charset charset = encodingService.getCurrentEncoding();
            List<String> allLogs = logRepository.readAll(charset);

            String target = "[" + date.toString();
            List<String> filtered = allLogs.stream()
                    .filter(line -> line.startsWith(target))
                    .collect(Collectors.toList());

            if (filtered.isEmpty()) {
                log.info("No log entries found for date: {}", date);
            } else {
                log.info("Log entries found for {}: {}", date, filtered.size());
                filtered.forEach(line -> log.info("→ {}", line));
            }

            return filtered;

        } catch (Exception e) {
            log.error("Error filtering logs by date: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Cambia la codificacion actual a UTF-8
     */
    public void useUtf8() {
        encodingService.setUtf8Encoding();
        log.info("Encoding changed to UTF-8.");
    }

    /**
     * Cambia la codificacion actual a ISO-8859-1
     */
    public void useIso88591() {
        encodingService.setIsoEncoding();
        log.info("Encoding changed to ISO-8859-1.");
    }
}
