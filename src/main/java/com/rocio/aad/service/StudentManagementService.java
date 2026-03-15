package com.rocio.aad.service;

import com.rocio.aad.model.Enrollment;
import com.rocio.aad.model.Module;
import com.rocio.aad.model.Student;
import com.rocio.aad.repository.EnrollmentRepository;
import com.rocio.aad.repository.ModuleRepository;
import com.rocio.aad.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentManagementService {

    // Repositorios necesarios para acceder a la base de datos
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    // =========================
    // STUDENTS
    // =========================

    // Crea un nuevo estudiante
    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // Obtiene todos los estudiantes
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Busca un estudiante por su id
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Elimina un estudiante
    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // =========================
    // MODULES
    // =========================

    // Crea un módulo nuevo
    @Transactional
    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    // Obtiene todos los módulos
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    // Busca un módulo por id
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElse(null);
    }

    // =========================
    // ENROLLMENTS
    // =========================

    // Matricula a un estudiante en un módulo
    @Transactional
    public Enrollment enrollStudent(Long studentId, Long moduleId) {

        // Se comprueba que el estudiante exista
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // Se comprueba que el módulo exista
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new IllegalArgumentException("Module not found"));

        // Se crea la matrícula
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setModule(module);
        enrollment.setEnrollmentDate(LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    // Obtiene todas las matrículas de un estudiante
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    /**
     * Cuenta cuántas matrículas tiene un estudiante usando JPQL.
     */
    public int countEnrollments(Long studentId) {
        return enrollmentRepository.countByStudentId(studentId);
    }

    /**
     * Llamada a una función almacenada en PostgreSQL
     * que calcula la media de notas de un módulo.
     */
    public Double getAverageGradeByModule(Long moduleId) {
        return enrollmentRepository.avgGradeByModule(moduleId);
    }
}