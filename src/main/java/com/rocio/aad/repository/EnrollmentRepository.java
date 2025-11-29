package com.rocio.aad.repository;

import com.rocio.aad.model.Enrollment;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Repositorio que gestiona la tabla "matricula" utilizando JdbcTemplate.
 * Incluye operaciones CRUD y la llamada a la función count_enrollments
 * mediante SimpleJdbcCall.
 */
@Slf4j
@Repository
public class EnrollmentRepository {

    private final JdbcTemplate jdbc;
    private SimpleJdbcCall countEnrollmentsCall;

    public EnrollmentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Inicializa la llamada a la función almacenada count_enrollments.
     * Se configura una única vez al arrancar el repositorio.
     */
    @PostConstruct
    private void init() {
        this.countEnrollmentsCall = new SimpleJdbcCall(jdbc)
                .withFunctionName("count_enrollments");
    }

    /**
     * Inserta una matrícula. Ya no necesita transacciones manuales,
     * porque JdbcTemplate gestiona la conexión internamente.
     */
    public Enrollment createEnrollment(Enrollment enrollment) {
        String sql = """
                INSERT INTO matricula (id_alumno, id_modulo, fecha)
                VALUES (?, ?, ?)
                """;

        try {
            jdbc.update(
                    sql,
                    enrollment.getStudentId(),
                    enrollment.getModuleId(),
                    Date.valueOf(enrollment.getDate())
            );

            log.info("Created enrollment: student={} module={}",
                    enrollment.getStudentId(),
                    enrollment.getModuleId());

            return enrollment;

        } catch (Exception e) {
            log.error("Error creating enrollment", e);
            throw new RuntimeException("Error creating enrollment", e);
        }
    }

    /**
     * Devuelve todas las matrículas registradas.
     */
    public List<Enrollment> findAll() {
        String sql = "SELECT id_alumno, id_modulo, fecha FROM matricula";

        try {
            List<Enrollment> list = jdbc.query(
                    sql,
                    (rs, rowNum) -> new Enrollment(
                            null,
                            rs.getInt("id_alumno"),
                            rs.getInt("id_modulo"),
                            rs.getDate("fecha").toLocalDate()
                    )
            );

            log.info("Fetched {} enrollments", list.size());
            return list;

        } catch (Exception e) {
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

        try {
            List<Enrollment> list = jdbc.query(
                    sql,
                    (rs, row) -> new Enrollment(
                            null,
                            rs.getInt("id_alumno"),
                            rs.getInt("id_modulo"),
                            rs.getDate("fecha").toLocalDate()
                    ),
                    studentId
            );

            log.info("Fetched {} enrollments for student {}", list.size(), studentId);
            return list;

        } catch (Exception e) {
            log.error("Error fetching enrollments for student {}", studentId, e);
            throw new RuntimeException("Error fetching enrollments", e);
        }
    }

    /**
     * Elimina una matrícula concreta dada por su clave compuesta.
     */
    public void delete(int studentId, int moduleId) {
        String sql = """
                DELETE FROM matricula
                WHERE id_alumno = ? AND id_modulo = ?
                """;

        try {
            jdbc.update(sql, studentId, moduleId);
            log.info("Deleted enrollment: student={} module={}", studentId, moduleId);

        } catch (Exception e) {
            log.error("Error deleting enrollment student={} module={}", studentId, moduleId, e);
            throw new RuntimeException("Error deleting enrollment", e);
        }
    }

    /**
     * Llama a la función count_enrollments usando SimpleJdbcCall.
     */
    public int countEnrollments(int studentId) {
        try {
            Map<String, Object> result = countEnrollmentsCall.execute(studentId);

            Integer total = (Integer) result.get("returnvalue"); // nombre estándar para functions

            log.info("Student {} has {} enrollments", studentId, total);
            return total;

        } catch (Exception e) {
            log.error("Error calling count_enrollments for student {}", studentId, e);
            throw new RuntimeException("Error calling count_enrollments", e);
        }
    }

    /**
     * Comprueba si existe ya una matrícula para un alumno y un módulo concretos.
     */
    public boolean exists(int studentId, int moduleId) {
        String sql = """
                SELECT COUNT(*)
                FROM matricula
                WHERE id_alumno = ? AND id_modulo = ?
                """;

        try {
            Integer count = jdbc.queryForObject(sql, Integer.class, studentId, moduleId);
            return count != null && count > 0;

        } catch (Exception e) {
            log.error("Error checking enrollment existence for student={} module={}",
                    studentId, moduleId, e);
            return false;
        }
    }

    /**
     * Busca una matrícula concreta. Si no existe, devuelve null.
     */
    public Enrollment findEnrollment(int studentId, int moduleId) {
        String sql = """
                SELECT id_alumno, id_modulo, fecha
                FROM matricula
                WHERE id_alumno = ? AND id_modulo = ?
                """;

        try {
            return jdbc.query(
                    sql,
                    (rs, row) ->
                            new Enrollment(
                                    null,
                                    rs.getInt("id_alumno"),
                                    rs.getInt("id_modulo"),
                                    rs.getDate("fecha").toLocalDate()
                            ),
                    studentId,
                    moduleId
            ).stream().findFirst().orElse(null);

        } catch (Exception e) {
            log.error("Error fetching enrollment student={} module={}", studentId, moduleId, e);
            throw new RuntimeException("Error fetching enrollment", e);
        }
    }
}
