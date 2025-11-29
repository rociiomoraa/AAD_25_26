package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa a un estudiante del sistema.
 * Esta clase se corresponde con la tabla "alumno" en la base de datos.
 * Contiene los datos básicos del alumno: identificador, NIF, nombre y correo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer id;
    private String nif;
    private String name;
    private String email;
}
