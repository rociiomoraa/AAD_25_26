package com.rocio.aad.service;

import com.rocio.aad.util.Constants;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;

/**
 * Esta clase proporciona el servicio encargado de gestionar la codificacion
 * utilizado al leer y escribir ficheros de log en la aplicacion.
 * <p>
 * Por defecto, se utiliza la codificacion UTF-8, pero tambien se soporta
 * la codificacion ISO-8859-1 para compatibilidad con ficheros antiguos.
 * <p>
 * Este servicio centraliza la configuración de codificación para que
 * el resto de componentes (como el repositorio) puedan consultarla sin duplicar lógica.
 */

@Service
public class EncodingService {

    /**
     * Codificación actual utilizada por la aplicación.
     * Inicialmente se establece en UTF-8.
     */
    private Charset currentEncoding = Constants.DEFAULT_ENCODING;

    /**
     * Cambia la codificacion actual a UTF-8
     */
    public void setUtf8Encoding() {
        this.currentEncoding = Constants.DEFAULT_ENCODING;
    }

    /**
     * Cambia la codificacion actual a ISO-8859-1
     */

    public void setIsoEncoding() {
        this.currentEncoding = Constants.ISO_ENCODING;
    }

    /**
     * Devuelve la codificacion actual utilizada por la aplicacion.
     *
     * @return La codificacion actual (UTF-8 o ISO-8859-1)
     */
    public Charset getCurrentEncoding() {
        return currentEncoding;
    }
}
