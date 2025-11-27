package com.rocio.aad.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Inicializa la base de datos ejecutando los scripts SQL indicados en application.yml. * Los scripts se ejecutan como bloques completos para permitir funciones PL/pgSQL con $$.
 */
@Slf4j
@Component
public class DatabaseInitializer {
    private final PostgresqlDriver driver;
    private final SqlProperties sqlProperties;
    private final ResourceLoader resourceLoader;

    public DatabaseInitializer(PostgresqlDriver driver, SqlProperties sqlProperties, ResourceLoader resourceLoader) {
        this.driver = driver;
        this.sqlProperties = sqlProperties;
        this.resourceLoader = resourceLoader;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        log.info("Starting database initialization...");
        for (String scriptPath : sqlProperties.getScripts()) {
            try {
                var resource = resourceLoader.getResource(scriptPath);
                String sql = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
                try (var con = driver.getConnection(); var statement = con.createStatement()) {
                    statement.execute(sql);
                }
                log.info("Executed script: {}", scriptPath);
            } catch (Exception e) {
                log.error("Error executing SQL script: {}", scriptPath, e);
            }
        }
        log.info("Database initialized from SQL scripts");
    }
}