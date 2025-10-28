package com.rocio.aad.util;

/**
 * Contiene las constantes globales utilizadas en la aplicación de gestión
 * de alumnos mediante acceso binario.
 * <p>
 * Se centralizan aquí los valores relacionados con el tamaño de los registros,
 * el nombre del fichero y otros parámetros que pueden reutilizarse en distintas clases.
 */
public final class Constants {

    /**
     * Nombre del fichero binario donde se guardan los alumnos.
     */
    public static final String FILE_NAME = "students.dat";

    /**
     * Longitud fija del nombre de cada alumno (en caracteres).
     */
    public static final int NAME_LENGTH = 20;

    /**
     * Tamaño de cada registro en bytes: id (4) + nombre (40) + nota (4).
     */
    public static final int RECORD_SIZE = 48;

    /**
     * Constructor privado para evitar instanciación.
     */
    private Constants() {
        throw new UnsupportedOperationException("Utility class");
    }
}
