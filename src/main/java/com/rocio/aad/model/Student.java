package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    /*
    Clase modelo que representa a un alumno
    No se usan constructores, getters y setters explícitos
    porque se usan las anotaciones de Lombok (@Data, @NoArgsConstructor, @AllArgsConstructor)
     */

    //Atributos
    private int id;
    private String name;
    private double grade;
}
