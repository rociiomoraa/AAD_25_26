package com.rocio.aad.repository;

import com.rocio.aad.config.PostgresqlDriver;
import com.rocio.aad.model.Student;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de acceder a la tabla de alumnos usando JDBC.
 * Aquí realizamos las operaciones CRUD.
 */
@Slf4j
@Repository
@AllArgsConstructor
public class StudentRepository {

    private final PostgresqlDriver driver;

    /**
     * Inserta un nuevo alumno en la base de datos y devuelve la entidad con el id generado.
     * Si ocurre algún error, se registra en los logs.
     */
    public Student insert(Student student) {
        String sql = "INSERT INTO alumno (nif, nombre, email) VALUES (?, ?, ?) RETURNING id_alumno";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getNif());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    student.setId(rs.getInt(1));
                }
            }

            log.info("Inserted student: {}", student.getId());
            return student;

        } catch (SQLException e) {
            log.error("Error inserting student", e);
            throw new RuntimeException("Error inserting student", e);
        }
    }

    /**
     * Devuelve la lista completa de alumnos almacenados en la base de datos.
     */
    public List<Student> findAll() {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno";
        List<Student> students = new ArrayList<>();

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

            log.info("Fetched {} students", students.size());
            return students;

        } catch (SQLException e) {
            log.error("Error fetching students", e);
            throw new RuntimeException("Error fetching students", e);
        }
    }

    /**
     * Busca un estudiante por su id. Si no existe, devuelve null.
     */
    public Student findById(int id) {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno WHERE id_alumno = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            log.error("Error fetching student with id {}", id, e);
            throw new RuntimeException("Error fetching student", e);
        }
    }

    /**
     * Actualiza un estudiante ya existente con los nuevos datos.
     */
    public Student update(Student student) {
        String sql = "UPDATE alumno SET nif = ?, nombre = ?, email = ? WHERE id_alumno = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getNif());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setInt(4, student.getId());

            ps.executeUpdate();

            log.info("Updated student: {}", student.getId());
            return student;

        } catch (SQLException e) {
            log.error("Error updating student {}", student.getId(), e);
            throw new RuntimeException("Error updating student", e);
        }
    }

    /**
     * Elimina un estudiante por su id.
     */
    public void delete(int id) {
        String sql = "DELETE FROM alumno WHERE id_alumno = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            log.info("Deleted student: {}", id);

        } catch (SQLException e) {
            log.error("Error deleting student {}", id, e);
            throw new RuntimeException("Error deleting student", e);
        }
    }

    /**
     * Convierte una fila del ResultSet en un objeto Student.
     */
    private Student mapStudent(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id_alumno"),
                rs.getString("nif"),
                rs.getString("nombre"),
                rs.getString("email"),
                List.of() // modules se rellenará en EnrollmentRepository si hace falta
        );
    }

    /**
     * Busca un alumno por su NIF. Devuelve null si no existe.
     */
    public Student findByNif(String nif) {
        String sql = "SELECT id_alumno, nif, nombre, email FROM alumno WHERE nif = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setString(1, nif);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            log.error("Error fetching student with NIF {}", nif, e);
            throw new RuntimeException("Error fetching student by NIF", e);
        }
    }

    /**
     * Comprueba si ya existe un alumno con un NIF dado.
     */
    public boolean existsByNif(String nif) {
        String sql = "SELECT COUNT(*) FROM alumno WHERE nif = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setString(1, nif);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            log.error("Error checking NIF existence {}", nif, e);
        }

        return false;
    }
}
