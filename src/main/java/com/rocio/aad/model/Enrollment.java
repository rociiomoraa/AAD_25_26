package com.rocio.aad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(
        name = "enrollment",
        uniqueConstraints = {
                // Se asegura que un estudiante no pueda matricularse
                // dos veces en el mismo módulo
                @UniqueConstraint(columnNames = {"student_id", "module_id"})
        }
)
public class Enrollment {

    // Identificador único de la matrícula
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos a uno con Student.
    // Muchos registros de matrícula pueden pertenecer al mismo estudiante.
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Relación muchos a uno con Module.
    // Muchos estudiantes pueden matricularse en el mismo módulo.
    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    // Fecha en la que se realiza la matrícula.
    // Es un campo obligatorio.
    @NotNull
    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    // Nota final obtenida en el módulo.
    // Puede ser null porque el estudiante todavía puede no haber terminado el módulo.
    @Column(name = "final_grade")
    private Double finalGrade;

    // Constructor útil para crear matrículas fácilmente
    public Enrollment(Student student, Module module, LocalDate enrollmentDate) {
        this.student = student;
        this.module = module;
        this.enrollmentDate = enrollmentDate;
    }
}