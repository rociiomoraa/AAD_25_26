package com.rocio.aad.repository;

import com.rocio.aad.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Método derivado de Spring Data JPA.
    // Permite buscar un estudiante por su NIF.
    // Spring genera automáticamente la consulta SQL
    // a partir del nombre del método.
    Optional<Student> findByNif(String nif);
}