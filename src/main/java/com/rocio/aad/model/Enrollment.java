package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Representa una matrícula, es decir, es la unión entre un estudiante y un módulo.
 * Esta clase refleja la tabla "matrícula" de la base de datos, donde cada registro
 * indica que un estudiante está inscrito en un módulo específico y en qué fecha.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    // Aunque la tabla usa una clave primaria compuesta (student_id, module_id),
    // aquí se incluye un campo 'id' para facilitar la gestión de las entidades en el código.
    private Integer id;

    private Integer studentId;
    private Integer moduleId;
    private LocalDate date;
}
