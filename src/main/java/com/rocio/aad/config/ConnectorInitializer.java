package com.rocio.aad.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración que activa las propiedades SQL para que la
 * aplicación pueda cargar la lista de scripts definida en el application.yml.
 */
@Configuration
@EnableConfigurationProperties(SqlProperties.class)
public class ConnectorInitializer {
}
