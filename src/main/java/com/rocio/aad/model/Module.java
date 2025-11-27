package com.rocio.aad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un módulo en el sistema. Aquí se guarda la información básica del módulo:
 * código, nomnbre y número de horas. Esta entidad se corresponde con la tabla "modulo"
 * de la base de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {

    private Integer id;
    private String code;
    private String name;
    private Integer hours;
}
