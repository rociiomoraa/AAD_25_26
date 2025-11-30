package com.rocio.aad.application;

import com.rocio.aad.model.Enrollment;
import com.rocio.aad.model.Module;
import com.rocio.aad.model.Student;
import com.rocio.aad.repository.EnrollmentRepository;
import com.rocio.aad.repository.ModuleRepository;
import com.rocio.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio principal de gestión académica. Esta capa coordina todas las
 * operaciones CRUD relacionadas con estudiantes, módulos y matrículas,
 * delegando el acceso a base de datos en los repositorios basados en JdbcTemplate.
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

    /**
     * Crea un nuevo estudiante si no existe otro con el mismo NIF.
     * En caso de existir, se devuelve el estudiante registrado.
     *
     * @param student objeto Student con los datos a registrar
     * @return el estudiante creado o el previamente existente
     */
    @Transactional
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
     * Recupera un estudiante por su identificador.
     *
     * @param id identificador del estudiante
     * @return el estudiante encontrado o null si no existe
     */
    public Student getStudentById(int id) {
        return studentRepository.findById(id);
    }

    /**
     * Devuelve la lista completa de estudiantes registrados.
     *
     * @return lista de estudiantes
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Actualiza los datos de un estudiante ya existente.
     *
     * @param student objeto Student con los cambios a aplicar
     * @return estudiante actualizado
     */
    @Transactional
    public Student updateStudent(Student student) {
        return studentRepository.update(student);
    }

    /**
     * Elimina un estudiante por su ID.
     *
     * @param id identificador del estudiante a eliminar
     */
    @Transactional
    public void deleteStudent(int id) {
        studentRepository.delete(id);
    }

    /**
     * Crea un nuevo módulo si no existe otro con el mismo código.
     * Si el módulo existe, se devuelve directamente.
     *
     * @param module módulo a registrar
     * @return módulo creado o ya existente
     */
    @Transactional
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
     * Recupera un módulo por su identificador.
     *
     * @param id identificador del módulo
     * @return módulo encontrado o null si no existe
     */
    public Module getModuleById(int id) {
        return moduleRepository.findById(id);
    }

    /**
     * Lista todos los módulos registrados en el sistema.
     *
     * @return lista de módulos
     */
    public List<Module> getAllModules() {
        return moduleRepository.findAll();
    }

    /**
     * Actualiza los datos de un módulo existente.
     *
     * @param module módulo con los datos ya modificados
     * @return módulo actualizado
     */
    @Transactional
    public Module updateModule(Module module) {
        return moduleRepository.update(module);
    }

    /**
     * Elimina un módulo por su ID.
     *
     * @param id identificador del módulo a eliminar
     */
    @Transactional
    public void deleteModule(int id) {
        moduleRepository.delete(id);
    }

    /**
     * Matricula a un estudiante en un módulo determinado.
     * Comprueba previamente que el estudiante y el módulo existen, y que
     * la matrícula no esté ya registrada.
     *
     * @param studentId ID del estudiante
     * @param moduleId  ID del módulo
     * @return matrícula creada o existente
     */
    @Transactional
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
     * Crea una matrícula directamente cuando se recibe un objeto Enrollment
     * desde el menú o desde otras capas.
     *
     * @param enrollment objeto matrícula a registrar
     * @return matrícula creada
     */
    @Transactional
    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.createEnrollment(enrollment);
    }

    /**
     * Devuelve todas las matrículas del sistema.
     *
     * @return lista de matrículas
     */
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    /**
     * Recupera todas las matrículas asociadas a un estudiante concreto.
     *
     * @param studentId ID del estudiante
     * @return lista de matrículas del estudiante
     */
    public List<Enrollment> getEnrollmentsByStudent(int studentId) {
        return enrollmentRepository.findByStudent(studentId);
    }

    /**
     * Elimina una matrícula concreta utilizando su clave compuesta
     * (id_alumno + id_modulo).
     *
     * @param studentId ID del estudiante
     * @param moduleId  ID del módulo
     */
    @Transactional
    public void deleteEnrollment(int studentId, int moduleId) {
        enrollmentRepository.delete(studentId, moduleId);
    }

    /**
     * Devuelve el número total de matrículas que tiene un estudiante,
     * utilizando la función almacenada count_enrollments.
     *
     * @param studentId ID del estudiante
     * @return número de matrículas del alumno
     */
    public int countEnrollments(int studentId) {
        return enrollmentRepository.countEnrollments(studentId);
    }
}
