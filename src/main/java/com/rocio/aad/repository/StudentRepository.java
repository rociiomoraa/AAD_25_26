package com.rocio.aad.repository;

import com.rocio.aad.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio encargado de gestionar la tabla "alumno" utilizando JdbcTemplate.
 * Aquí se realizan las operaciones CRUD sin usar JDBC manual ni conexiones directas.
 */
@Slf4j
@Repository
public class StudentRepository {

    private final JdbcTemplate jdbc;

    public StudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Inserta un nuevo alumno en la base de datos y devuelve la entidad con el id generado.
     */
    public Student insert(Student student) {
        String sql = """
                INSERT INTO alumno (nif, nombre, email)
                VALUES (?, ?, ?)
                RETURNING id_alumno
                """;
        try {
            Integer id = jdbc.queryForObject(
                    sql,
                    Integer.class,
                    student.getNif(),
                    student.getName(),
                    student.getEmail()
            );
            student.setId(id);

            log.info("Inserted student: {}", student.getId());
            return student;
        } catch (Exception e) {
            log.error("Error inserting student: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Devuelve la lista completa de alumnos almacenados en la BD.
     */
    public List<Student> findAll() {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno";

        try {
            List<Student> students = jdbc.query(sql, (rs, rowNum) ->
                    new Student(
                            rs.getInt("id_alumno"),
                            rs.getString("nif"),
                            rs.getString("nombre"),
                            rs.getString("email")
                    )
            );

            log.info("Fetched {} students", students.size());
            return students;
        } catch (Exception e) {
            log.error("Error fetching students: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Busca un estudiante por su id. Si no existe, devuelve null.
     */
    public Student findById(int id) {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno WHERE id_alumno = ?";

        try {
            return jdbc.query(
                    sql,
                    (rs, rowNum) ->
                            new Student(
                                    rs.getInt("id_alumno"),
                                    rs.getString("nif"),
                                    rs.getString("nombre"),
                                    rs.getString("email")
                            ),
                    id
            ).stream().findFirst().orElse(null);
        } catch (Exception e) {
            log.error("Error finding student by id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * Actualiza un estudiante ya existente con los nuevos datos.
     */
    public Student update(Student student) {
        String sql = """
                UPDATE alumno
                SET nif = ?, nombre = ?, email = ?
                WHERE id_alumno = ?
                """;
        try {
            jdbc.update(
                    sql,
                    student.getNif(),
                    student.getName(),
                    student.getEmail(),
                    student.getId()
            );
            log.info("Updated student: {}", student.getId());
            return student;
        } catch (Exception e) {
            log.error("Error updating student {}: {}", student.getId(), e.getMessage());
            throw e;
        }
    }

    /**
     * Elimina un estudiante por su id.
     */
    public void delete(int id) {
        String sql = "DELETE FROM alumno WHERE id_alumno = ?";

        try {
            jdbc.update(sql, id);
            log.info("Deleted student: {}", id);
        } catch (Exception e) {
            log.error("Error deleting student {}: {}", id, e.getMessage());
            throw e;
        }
    }

    /**
     * Busca un alumno por su NIF. Devuelve null si no existe.
     */
    public Student findByNif(String nif) {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno WHERE nif = ?";

        try {
            return jdbc.query(
                    sql,
                    (rs, rowNum) ->
                            new Student(
                                    rs.getInt("id_alumno"),
                                    rs.getString("nif"),
                                    rs.getString("nombre"),
                                    rs.getString("email")
                            ),
                    nif
            ).stream().findFirst().orElse(null);

        } catch (Exception e) {
            log.error("Error fetching student with NIF {}", nif, e);
            throw new RuntimeException("Error fetching student by NIF", e);
        }
    }

    /**
     * Comprueba si ya existe un alumno con un NIF dado.
     */
    public boolean existsByNif(String nif) {
        String sql = "SELECT COUNT(*) FROM alumno WHERE nif = ?";

        try {
            Integer count = jdbc.queryForObject(sql, Integer.class, nif);
            return count != null && count > 0;

        } catch (Exception e) {
            log.error("Error checking NIF existence {}", nif, e);
            return false;
        }
    }
}
