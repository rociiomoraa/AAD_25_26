package com.rocio.aad.repository;

import com.rocio.aad.model.LogEvent;
import com.rocio.aad.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Es el repositorio encargado de gestionar la lectura y escritura del fichero de logs.
 * Se encarga de crear el fichero si no existe, añadir nuevos eventos al log y
 * recuperar los registros almacenados, aplicando la codificación especificada.
 * Esta clase utiliza BufferedWritter, OutputStreamWriter, BufferedReader e InputStreamReader
 * para manejar la lectura y escritura de archivos con diferentes codificaciones (UTF-8 e ISO
 */

@Repository
@Slf4j
public class LogRepository {
    /**
     * Ruta al fichero de log
     */
    private final Path logFilePath = Paths.get(Constants.LOG_FILE_NAME);

    /**
     * Este método añade un nuevo evento al fichero de log
     * Si el fichero no existe, lo crea automaticamente
     *
     * @param event   Evento a añadir al log
     * @param charset Codificacion a utilizar (UTF-8 O ISO-8859-1)
     */
    public void append(LogEvent event, Charset charset) {
        try {
            //Crea el fichero si no existe
            if (Files.notExists(logFilePath)) {
                Files.createFile(logFilePath);
            }

            // Usa try-with-resources para cerrar automáticamente el flujo
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(logFilePath.toFile(), true), charset))) {
                DateTimeFormatter formatter = Constants.LOG_DATE_FORMAT;
                String formattedDate = event.getTimestamp().format(formatter);
                writer.write("[" + formattedDate + "] " + event.getMessage());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Método que lee todas las líneas del fichero de log y las devuelve como una lista
     *
     * @param charset Codificacion utilizada para leer el fichero.
     * @return Lista de líneas del fichero de log. Si el fichero no existe, devuelve una lista vacía.
     */
    public List<String> readAll(Charset charset) {
        List<String> lines = new ArrayList<>();

        if (Files.notExists(logFilePath)) {
            return lines; // Devuelve lista vacía si el fichero no existe
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(logFilePath.toFile()), charset))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.info("Error reading log file: " + e.getMessage());
        }
        return lines;
    }
}

