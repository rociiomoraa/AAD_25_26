package com.rocio.aad.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

/**
 * Esta clase contiene constantes utilizadas en la aplicacion,
 * como el nombre del fichero de log, formatos de fecha y codificaciones.
 */

public class Constants {

    //Nombre del fichero donde se almacenan los eventos del log
    public static final String LOG_FILE_NAME = "app.log";

    //Formato de fecha y hora para los eventos del log
    public static final DateTimeFormatter LOG_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    //Codificacion por defecto para leer y escribir el fichero de log
    public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    //Codificacion alternativa para leer el fichero de log
    public static final Charset ISO_ENCODING = StandardCharsets.ISO_8859_1;

    /**
     * Constructor privado para evitar la instanciacion de la clase
     */
    private Constants() {
        throw new UnsupportedOperationException("Utility class.");
    }
}
