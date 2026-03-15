package com.rocio.aad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    // Relación con la entidad Enrollment.
    // Un módulo puede tener muchas matrículas asociadas.
    // mappedBy indica que la relación está gestionada desde Enrollment.
    // FetchType.LAZY evita cargar todas las matrículas automáticamente
    // cuando se consulta un módulo.
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    // Constructor útil para crear módulos fácilmente
    public Module(String code, String name, Integer hours) {
        this.code = code;
        this.name = name;
        this.hours = hours;
    }
}