package com.rocio.aad.repository;

import com.rocio.aad.config.PostgresqlDriver;
import com.rocio.aad.model.Enrollment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de gestionar las matrículas. Aquí realizamos las
 * operaciones relacionadas con la tabla "matricula", incluyendo la creación
 * de matrículas dentro de una transacción y la llamada a la función
 * count_enrollments definida en PostgreSQL.
 */
@Slf4j
@Repository
public class EnrollmentRepository {

    private final PostgresqlDriver driver;

    public EnrollmentRepository(PostgresqlDriver driver) {
        this.driver = driver;
    }

    /**
     * Crea una matrícula dentro de una transacción ya iniciada desde el
     * servicio. Aquí simplemente se insertan los datos y se devuelven.
     */
    public Enrollment createEnrollment(Enrollment enrollment) {
        String sql = """
                INSERT INTO matricula (id_alumno, id_modulo, fecha)
                VALUES (?, ?, ?)
                """;

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, enrollment.getStudentId());
            ps.setInt(2, enrollment.getModuleId());
            ps.setDate(3, Date.valueOf(enrollment.getDate()));

            ps.executeUpdate();

            log.info("Created enrollment: student={} module={}",
                    enrollment.getStudentId(), enrollment.getModuleId());

            return enrollment;

        } catch (SQLException e) {
            log.error("Error creating enrollment", e);
            throw new RuntimeException("Error creating enrollment", e);
        }
    }

    /**
     * Devuelve todas las matrículas registradas.
     */
    public List<Enrollment> findAll() {
        String sql = "SELECT id_alumno, id_modulo, fecha FROM matricula";
        List<Enrollment> list = new ArrayList<>();

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapEnrollment(rs));
            }

            log.info("Fetched {} enrollments", list.size());
            return list;

        } catch (SQLException e) {
            log.error("Error fetching enrollments", e);
            throw new RuntimeException("Error fetching enrollments", e);
        }
    }

    /**
     * Devuelve todas las matrículas de un estudiante concreto.
     */
    public List<Enrollment> findByStudent(int studentId) {
        String sql = """
                SELECT id_alumno, id_modulo, fecha
                FROM matricula
                WHERE id_alumno = ?
                """;

        List<Enrollment> list = new ArrayList<>();

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapEnrollment(rs));
                }
            }

            log.info("Fetched {} enrollments for student {}", list.size(), studentId);
            return list;

        } catch (SQLException e) {
            log.error("Error fetching enrollments for student {}", studentId, e);
            throw new RuntimeException("Error fetching enrollments", e);
        }
    }

    /**
     * Elimina una matrícula concreta (clave compuesta).
     */
    public void delete(int studentId, int moduleId) {
        String sql = """
                DELETE FROM matricula
                WHERE id_alumno = ? AND id_modulo = ?
                """;

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, moduleId);
            ps.executeUpdate();

            log.info("Deleted enrollment: student={} module={}", studentId, moduleId);

        } catch (SQLException e) {
            log.error("Error deleting enrollment student={} module={}", studentId, moduleId, e);
            throw new RuntimeException("Error deleting enrollment", e);
        }

    }

    /**
     * Llama a la función count_enrollments del servidor PostgreSQL usando
     * un CallableStatement. Devuelve el número total de matrículas del alumno.
     */
    public int countEnrollments(int studentId) {
        String call = "{ ? = call count_enrollments(?) }";

        try (var con = driver.getConnection();
             var cs = con.prepareCall(call)) {

            cs.registerOutParameter(1, Types.INTEGER);
            cs.setInt(2, studentId);

            cs.execute();
            int total = cs.getInt(1);

            log.info("Student {} has {} enrollments", studentId, total);
            return total;

        } catch (SQLException e) {
            log.error("Error calling count_enrollments for student {}", studentId, e);
            throw new RuntimeException("Error calling count_enrollments", e);
        }
    }

    /**
     * Convierte una fila del ResultSet en un objeto Enrollment.
     */
    private Enrollment mapEnrollment(ResultSet rs) throws SQLException {
        return new Enrollment(
                null,
                rs.getInt("id_alumno"),
                rs.getInt("id_modulo"),
                rs.getDate("fecha").toLocalDate()
        );
    }
}
