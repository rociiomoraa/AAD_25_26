package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un alumno dentro del fichero binario.
 * Cada registro tiene un tamaño fijo en bytes para permitir el acceso aleatorio
 * mediante la clase java.io.RandomAccessFile .
 * Estructura del registro (48 bytes):
 * id → 4 bytes (int)
 * nombre → 40 bytes (20 caracteres * 2 bytes)
 * nota → 4 bytes (float)
 * El nombre siempre se almacena con una longitud fija de 20 caracteres,
 * rellenando con espacios en blanco si es más corto.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /**
     * Identificador único del alumno.
     */
    private int id;

    /**
     * Nombre del alumno (longitud fija de 20 caracteres).
     */
    private String name;

    /**
     * Nota del alumno (valor decimal).
     */
    private float grade;
}
