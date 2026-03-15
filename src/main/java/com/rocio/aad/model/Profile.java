package com.rocio.aad.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    // Identificador único del perfil.
    // Se genera automáticamente en la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dirección del estudiante.
    // Campo obligatorio.
    @NotBlank
    @Column(nullable = false)
    private String address;

    // Teléfono del estudiante.
    // También es obligatorio.
    @NotBlank
    @Column(nullable = false)
    private String phone;

    // Relación inversa con la entidad Student.
    // mappedBy indica que la relación está gestionada desde Student
    // (en el atributo "profile" de esa clase).
    // Esto evita que JPA cree una segunda clave foránea innecesaria.
    // FetchType.LAZY evita cargar automáticamente el estudiante
    // cuando se consulta el perfil.
    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
    private Student student;

}