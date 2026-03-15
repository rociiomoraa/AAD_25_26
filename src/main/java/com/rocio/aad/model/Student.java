package com.rocio.aad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    // Identificador único del estudiante.
    // Se genera automáticamente en la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // NIF del estudiante.
    // No puede estar vacío y debe ser único en la base de datos.
    @NotBlank(message = "NIF cannot be empty")
    @Column(nullable = false, unique = true)
    private String nif;

    // Nombre del estudiante.
    // Campo obligatorio.
    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    // Email del estudiante.
    // Se valida que tenga formato de email.
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    @Column(nullable = false)
    private String email;

    // Curso al que pertenece el estudiante.
    // También es obligatorio.
    @NotBlank
    @Column(nullable = false)
    private String course;

    // Relación uno a uno con la entidad Profile.
    // Cada estudiante tiene un único perfil asociado.
    // Cascade.ALL permite que las operaciones (guardar, borrar, etc.)
    // se propaguen automáticamente al perfil.
    // orphanRemoval elimina el perfil si se elimina la relación.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    // Constructor útil para crear estudiantes fácilmente
    public Student(String nif, String name, String email, String course) {
        this.nif = nif;
        this.name = name;
        this.email = email;
        this.course = course;
    }
}