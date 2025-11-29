package com.rocio.aad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Configuración para habilitar el uso de JdbcTemplate.
 * Spring Boot crea el DataSouce automáticamente a partir de las propiedades definidas en application.yml.
 */
@Configuration
public class JdbcConfig {

    /**
     * @Bean indica que este método crea un objeto que se debe gestionar
     * dentro del contenedor. En este caso, el JdbcTemplate, que se usará
     * para ejecutar las consultas SQL.
     *
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
