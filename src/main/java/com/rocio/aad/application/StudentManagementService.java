package com.rocio.aad.application;

import com.rocio.aad.config.PostgresqlDriver;
import com.rocio.aad.model.Enrollment;
import com.rocio.aad.model.Module;
import com.rocio.aad.model.Student;
import com.rocio.aad.repository.EnrollmentRepository;
import com.rocio.aad.repository.ModuleRepository;
import com.rocio.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Servicio principal de gestión académica. Aquí centralizamos toda la lógica
 * relacionada con estudiantes, módulos y matrículas. Esta capa coordina a los
 * repositorios y controla manualmente las transacciones cuando es necesario.
 */
@Slf4j
@Service
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PostgresqlDriver driver;

    public StudentManagementService(StudentRepository studentRepository,
                                    ModuleRepository moduleRepository,
                                    EnrollmentRepository enrollmentRepository,
                                    PostgresqlDriver driver) {
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.driver = driver;
    }

    /**
     * Crea un estudiante validando sus datos básicos. Si ya existe otro
     * estudiante con el mismo NIF, simplemente devolvemos el que está en la
     * base de datos.
     */
    public Student createStudent(Student student) {
        if (student.getNif() == null || student.getName() == null ||
                student.getEmail() == null) {
            throw new IllegalArgumentException("Missing required student fields");
        }

        // Verificamos si ya existe un estudiante con este NIF
        var all = studentRepository.findAll();
        for (Student s : all) {
            if (s.getNif().equals(student.getNif())) {
                log.info("Student already exists: {}", s.getId());
                return s;
            }
        }

        var created = studentRepository.insert(student);
        log.info("Created student: {}", created.getId());
        return created;
    }

    /**
     * Crea un nuevo módulo comprobando si ya existe otro con el mismo código.
     * Si existe, devolvemos el módulo ya registrado.
     */
    public Module createModule(Module module) {
        if (module.getCode() == null || module.getName() == null || module.getHours() == null) {
            throw new IllegalArgumentException("Missing required module fields");
        }

        // Verificamos si ya existe un módulo con este código
        var all = moduleRepository.findAll();
        for (Module m : all) {
            if (m.getCode().equals(module.getCode())) {
                log.info("Module already exists: {}", m.getId());
                return m;
            }
        }

        var created = moduleRepository.insert(module);
        log.info("Created module: {}", created.getId());
        return created;
    }

    /**
     * Matricula a un estudiante en un módulo dentro de una transacción manual.
     * Si ocurre cualquier error durante el proceso, se hace rollback.
     */
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {
        try {
            driver.beginTransaction();

            // Validar existencia del estudiante
            var student = studentRepository.findById(studentId);
            if (student == null) {
                throw new IllegalArgumentException("Student not found: " + studentId);
            }

            // Validar existencia del módulo
            var module = moduleRepository.findById(moduleId);
            if (module == null) {
                throw new IllegalArgumentException("Module not found: " + moduleId);
            }

            // Crear la matrícula
            Enrollment enrollment = new Enrollment(
                    null,
                    student.getId(),
                    module.getId(),
                    LocalDate.now()
            );

            Enrollment created = enrollmentRepository.createEnrollment(enrollment);

            driver.commit();
            log.info("Enrollment created successfully for student {}", studentId);

            return created;

        } catch (Exception e) {
            driver.rollback();
            log.error("Error enrolling student {}", studentId, e);
            throw new RuntimeException("Error enrolling student", e);
        }
    }
}
