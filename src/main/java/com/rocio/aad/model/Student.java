package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CLASE STUDENT.
 * Representa a un estudiante en el sistema. Aquí se guardan sus datos
 * básicos y también una lista con los módulos que cursa, aunque esa relación
 * se gestiona desde la clase Módulo.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private Integer id;
    private String nif;
    private String name;
    private String email;

    /**
     * Lista de módulos que cursa el estudiante.No se almacenan aquí directamente,
     * pero se incluye para facilitar el acceso a esta información cuando sea necesario.
     */
    private List<Module> modules;
}
