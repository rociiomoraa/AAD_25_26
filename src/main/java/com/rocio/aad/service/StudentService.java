package com.rocio.aad.service;

import com.rocio.aad.model.Student;
import com.rocio.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio que gestiona las operaciones de negocio relacionadas con los alumnos.
 * <p>
 * Coordina el acceso al repositorio y proporciona métodos de alto nivel para
 * insertar, consultar y modificar registros de alumnos.
 * <p>
 * También incluye logs informativos para evidenciar el funcionamiento correcto
 * del acceso secuencial y aleatorio.
 */
@Slf4j
@Service
public class StudentService {

    /**
     * Repositorio encargado de las operaciones sobre el fichero binario.
     */
    private final StudentRepository studentRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param studentRepository Repositorio de acceso a datos de alumnos.
     */
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Inserta un nuevo alumno al final del fichero (acceso secuencial).
     *
     * @param id    Identificador del alumno.
     * @param name  Nombre del alumno.
     * @param grade Nota del alumno.
     */
    public void addStudent(int id, String name, float grade) {
        Student student = new Student(id, name, grade);
        studentRepository.insertStudent(student);
        log.info("Student added: id={}, name={}, grade={}", id, name, grade);
    }

    /**
     * Consulta un alumno directamente por su posición en el fichero (acceso aleatorio).
     *
     * @param position Posición del registro (empezando en 0).
     * @return Alumno encontrado o vacío si no existe.
     */
    public Optional<Student> getStudentByPosition(int position) {
        Student student = studentRepository.readStudent(position);
        if (student != null) {
            log.info("Student found at position {}: {}", position, student);
            return Optional.of(student);
        } else {
            log.warn("No student found at position {}", position);
            return Optional.empty();
        }
    }

    /**
     * Modifica la nota de un alumno en una posición determinada sin reescribir todo el fichero.
     *
     * @param position Posición del alumno.
     * @param newGrade Nueva nota a asignar.
     */
    public void updateStudentGrade(int position, float newGrade) {
        studentRepository.updateGrade(position, newGrade);
        log.info("Student grade updated at position {}: new grade={}", position, newGrade);
    }
}
