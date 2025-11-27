package com.rocio.aad.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Esta clase se encarga de ejecutar los scripts SQL definidos en application.yml
 * al arrancar la app. De esta forma la base de datos se inicializa automáticamente.
 */
@Slf4j
@Component
public class DatabaseInitializer {

    private final PostgresqlDriver driver;
    private final SqlProperties sqlProperties;
    private final ResourceLoader resourceLoader;

    public DatabaseInitializer(PostgresqlDriver driver,
                               SqlProperties sqlProperties,
                               ResourceLoader resourceLoader) {
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

                try (var con = driver.getConnection();
                     var statement = con.createStatement()) {

                    for (String block : sql.split(";")) {
                        var trimmed = block.trim();
                        if (!trimmed.isBlank()) {
                            statement.execute(trimmed);
                        }
                    }
                }

                log.info("Executed script: {}", scriptPath);

            } catch (Exception e) {
                log.error("Error executing SQL script: {}", scriptPath, e);
            }
        }

        log.info("Database initialized from SQL scripts");
    }
}
