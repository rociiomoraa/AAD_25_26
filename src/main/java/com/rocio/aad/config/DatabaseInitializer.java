package com.rocio.aad.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Ejecuta automáticamente los scripts SQL ubicados en el directorio
 * src/main/resources/sql/ddl/ al iniciar la aplicación.
 * <p>
 * Los scripts se ejecutan como bloques completos para evitar errores
 * con funciones PL/pgSQL que contienen múltiples ";" internos.
 */
@Slf4j
@Component
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        runScript("sql/ddl/01_schema.sql");
        runScript("sql/ddl/02_procedures.sql");
        runScript("sql/ddl/03_sample_data.sql");
    }

    /**
     * Ejecuta un archivo SQL completo, sin dividir por ";".
     * Esto es necesario para que PostgreSQL pueda interpretar funciones PL/pgSQL.
     */
    private void runScript(String path) {
        try {
            var resource = new ClassPathResource(path);

            if (!resource.exists()) {
                log.warn("Script no encontrado: {}", path);
                return;
            }

            // leer contenido entero
            String sql = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            // ejecutar el script completo
            jdbcTemplate.execute(sql);

            log.info("Ejecutado: {}", path);

        } catch (Exception e) {
            log.error("Error ejecutando script {}: {}", path, e.getMessage());
        }
    }
}
