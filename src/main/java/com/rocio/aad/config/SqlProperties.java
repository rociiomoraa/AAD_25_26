package com.rocio.aad.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * Aquí guardamos la lista de scripts SQL que queremos que la aplicación
 * ejecute al arrancar. Estos valores se leen desde el application.yml
 * y luego se usan en el inicializador de la base de datos.
 */
@Data
@ConfigurationProperties(prefix = "aad.sql")
public class SqlProperties {

    private List<String> scripts;
}
