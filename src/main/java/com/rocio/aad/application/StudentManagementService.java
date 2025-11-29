package com.rocio.aad.application;

import com.rocio.aad.model.Enrollment;
import com.rocio.aad.model.Module;
import com.rocio.aad.model.Student;
import com.rocio.aad.repository.EnrollmentRepository;
import com.rocio.aad.repository.ModuleRepository;
import com.rocio.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio principal de gestión académica. Aquí se centralizan todas
 * las operaciones CRUD de estudiantes, módulos y matrículas, apoyándose
 * en los repositorios basados en JdbcTemplate.
 */
@Slf4j
@Service
public class StudentManagementService {

    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentManagementService(StudentRepository studentRepository,
                                    ModuleRepository moduleRepository,
                                    EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.moduleRepository = moduleRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // =============================================================
    //                        ESTUDIANTES
    // =============================================================

    /**
     * Crea un estudiante validando sus datos.
     * Si ya existe uno con el mismo NIF, devuelve el existente.
     */
    public Student createStudent(Student student) {
        if (student.getNif() == null ||
                student.getName() == null ||
                student.getEmail() == null) {
            throw new IllegalArgumentException("Missing required student fields");
        }

        var existing = studentRepository.findByNif(student.getNif());
        if (existing != null) {
            log.info("Student already exists: {}", existing.getId());
            return existing;
        }

        return studentRepository.insert(student);
    }

    /**
     * Recupera un estudiante por su ID.
     * Devuelve null si no existe.
     */
    public Student getStudentById(int id) {
        return studentRepository.findById(id);
    }

    /**
     * Lista completa de estudiantes.
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Actualiza un estudiante existente.
     */
    public Student updateStudent(Student student) {
        return studentRepository.update(student);
    }

    /**
     * Elimina un estudiante por ID.
     */
    public void deleteStudent(int id) {
        studentRepository.delete(id);
    }

    // =============================================================
    //                           MÓDULOS
    // =============================================================

    /**
     * Crea un módulo si no existe otro con el mismo código.
     */
    public Module createModule(Module module) {
        if (module.getCode() == null ||
                module.getName() == null ||
                module.getHours() == null) {
            throw new IllegalArgumentException("Missing required module fields");
        }

        var existing = moduleRepository.findByCode(module.getCode());
        if (existing != null) {
            log.info("Module already exists: {}", existing.getId());
            return existing;
        }

        return moduleRepository.insert(module);
    }

    /**
     * Recupera un módulo por ID.
     */
    public Module getModuleById(int id) {
        return moduleRepository.findById(id);
    }

    /**
     * Lista de todos los módulos.
     */
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    /**
     * Actualiza un módulo existente.
     */
    public Module updateModule(Module module) {
        return moduleRepository.update(module);
    }

    /**
     * Elimina un módulo por ID.
     */
    public void deleteModule(int id) {
        moduleRepository.delete(id);
    }

    // =============================================================
    //                         MATRÍCULAS
    // =============================================================

    /**
     * Matricula a un estudiante en un módulo si no existe ya la matrícula.
     */
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {

        var student = studentRepository.findById(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found: " + studentId);
        }

        var module = moduleRepository.findById(moduleId);
        if (module == null) {
            throw new IllegalArgumentException("Module not found: " + moduleId);
        }

        if (enrollmentRepository.exists(studentId, moduleId)) {
            return enrollmentRepository.findEnrollment(studentId, moduleId);
        }

        Enrollment enrollment = new Enrollment(
                null,
                studentId,
                moduleId,
                LocalDate.now()
        );

        return enrollmentRepository.createEnrollment(enrollment);
    }

    /**
     * Crear matrícula directamente desde el menú.
     */
    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.createEnrollment(enrollment);
    }

    /**
     * Lista todas las matrículas registradas.
     */
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    /**
     * Lista todas las matrículas de un estudiante concreto.
     */
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        return enrollmentRepository.findByStudent(studentId);
    }

    /**
     * Elimina una matrícula concreta por su clave compuesta.
     */
    public void deleteEnrollment(int studentId, int moduleId) {
        enrollmentRepository.delete(studentId, moduleId);
    }

    /**
     * Devuelve cuántas matrículas tiene un estudiante (función SQL).
     */
    public int countEnrollments(int studentId) {
        return enrollmentRepository.countEnrollments(studentId);
    }
}
