package com.rocio.aad.repository;

import com.rocio.aad.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // JPQL: obtiene todas las matrículas cuya nota final
    // sea mayor o igual que el valor indicado
    @Query("SELECT e FROM Enrollment e WHERE e.finalGrade >= :minGrade")
    List<Enrollment> findByMinFinalGrade(@Param("minGrade") Double minGrade);


    // JPQL: obtiene todas las matrículas de un estudiante concreto
    // usando su id
    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentId(@Param("studentId") Long studentId);


    // JPQL: cuenta cuántas matrículas tiene un estudiante
    // este método suele ser obligatorio en la práctica
    @Query("SELECT COUNT(e) FROM Enrollment e WHERE e.student.id = :studentId")
    int countByStudentId(@Param("studentId") Long studentId);


    // Llamada a una función almacenada en PostgreSQL
    // que calcula la media de notas de un módulo
    @Procedure(name = "avg_grade_by_module")
    Double avgGradeByModule(@Param("moduleId") Long moduleId);
}