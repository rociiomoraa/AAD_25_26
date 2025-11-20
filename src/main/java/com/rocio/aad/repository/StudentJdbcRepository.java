package com.rocio.aad.repository;

import com.rocio.aad.config.PostgresqlDriver;
import com.rocio.aad.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class StudentJdbcRepository implements CrudRepository<Student> {

    private static final String SQL_INSERT = """
            INSERT INTO student (first_name, last_name, birth_date, average_grade)
            VALUES (?, ?, ?, ?)
            """;

    private static final String SQL_SELECT_BY_ID = """
            SELECT id, first_name, last_name, birth_date, average_grade
            FROM student
            WHERE id = ?
            """;

    private static final String SQL_UPDATE = """
            UPDATE student
            SET first_name = ?, last_name = ?, birth_date = ?, average_grade = ?
            WHERE id = ?
            """;

    private static final String SQL_DELETE = """
            DELETE FROM student
            WHERE id = ?
            """;

    private final PostgresqlDriver dataSource;

    @Override
    public Student create(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setDate(3, entity.getBirthDate() != null ? Date.valueOf(entity.getBirthDate()) : null);
            ps.setObject(4, entity.getAverageGrade(), Types.NUMERIC);

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                }
            }

            log.info("Estudiante creado correctamente: {}", entity);
            return entity;

        } catch (SQLException e) {
            log.error("Error al crear estudiante ({} {}): {}", entity.getFirstName(), entity.getLastName(), e.getMessage(), e);
            throw new RuntimeException("Error creando estudiante", e);
        }
    }

    @Override
    public Student read(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, entity.getId());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = mapRow(rs);
                    log.info("Estudiante encontrado: {}", s);
                    return s;
                } else {
                    log.warn("No se encontró ningún estudiante con id={}", entity.getId());
                    return null;
                }
            }

        } catch (SQLException e) {
            log.error("Error al leer estudiante con id={}: {}", entity.getId(), e.getMessage(), e);
            throw new RuntimeException("Error leyendo estudiante", e);
        }
    }

    @Override
    public Student update(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setDate(3, entity.getBirthDate() != null ? Date.valueOf(entity.getBirthDate()) : null);
            ps.setObject(4, entity.getAverageGrade(), Types.NUMERIC);
            ps.setInt(5, entity.getId());

            int updated = ps.executeUpdate();

            if (updated > 0) {
                log.info("Estudiante actualizado correctamente: {}", entity);
            } else {
                log.warn("No se actualizó ningún registro con id={}", entity.getId());
            }

            return entity;

        } catch (SQLException e) {
            log.error("Error al actualizar estudiante con id={}: {}", entity.getId(), e.getMessage(), e);
            throw new RuntimeException("Error actualizando estudiante", e);
        }
    }

    @Override
    public boolean delete(Student entity) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, entity.getId());
            int deleted = ps.executeUpdate();

            if (deleted > 0) {
                log.info("Estudiante eliminado correctamente (id={})", entity.getId());
            } else {
                log.warn("No se encontró estudiante para eliminar con id={}", entity.getId());
            }

            return deleted > 0;

        } catch (SQLException e) {
            log.error("Error al eliminar estudiante con id={}: {}", entity.getId(), e.getMessage(), e);
            throw new RuntimeException("Error eliminando estudiante", e);
        }
    }

    /**
     * Método adicional para probar control de transacciones manuales (rollback)
     */
    public void insertMultipleWithTransaction(Student s1, Student s2) {
        Objects.requireNonNull(s1);
        Objects.requireNonNull(s2);

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

                // Primer insert correcto
                ps.setString(1, s1.getFirstName());
                ps.setString(2, s1.getLastName());
                ps.setDate(3, s1.getBirthDate() != null ? Date.valueOf(s1.getBirthDate()) : null);
                ps.setObject(4, s1.getAverageGrade(), Types.NUMERIC);
                ps.executeUpdate();
                log.info("Primer insert correcto: {}", s1);

                // Segundo insert que provocará error (por ejemplo, id duplicado)
                ps.setString(1, s2.getFirstName());
                ps.setString(2, s2.getLastName());
                ps.setDate(3, s2.getBirthDate() != null ? Date.valueOf(s2.getBirthDate()) : null);
                ps.setObject(4, s2.getAverageGrade(), Types.NUMERIC);
                ps.executeUpdate();

                conn.commit();
                log.info("Transacción completada correctamente.");

            } catch (SQLException e) {
                conn.rollback();
                log.error("Error en transacción, realizando rollback: {}", e.getMessage(), e);
            }

        } catch (SQLException e) {
            log.error("Error general de conexión o rollback: {}", e.getMessage(), e);
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        Date bd = rs.getDate("birth_date");
        s.setBirthDate(bd != null ? bd.toLocalDate() : null);
        s.setAverageGrade(
                rs.getBigDecimal("average_grade") != null
                        ? rs.getBigDecimal("average_grade").doubleValue()
                        : 0.0
        );
        return s;
    }
}
