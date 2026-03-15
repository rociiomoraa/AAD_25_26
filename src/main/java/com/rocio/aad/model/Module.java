package com.rocio.aad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "module")
public class Module {

    // Identificador único del módulo.
    // Se genera automáticamente en la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Código del módulo (por ejemplo: AAD, PROG, etc.).
    // Es obligatorio y además debe ser único en la base de datos.
    @NotBlank(message = "Code cannot be empty")
    @Column(nullable = false, unique = true)
    private String code;

    // Nombre del módulo.
    // Campo obligatorio.
    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    // Número de horas del módulo.
    // No puede ser null.
    @NotNull(message = "Hours cannot be null")
    @Column(nullable = false)
    private Integer hours;

    // Constructor útil para crear módulos fácilmente
    public Module(String code, String name, Integer hours) {
        this.code = code;
        this.name = name;
        this.hours = hours;
    }
}