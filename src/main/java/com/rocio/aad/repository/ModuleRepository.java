package com.rocio.aad.repository;

import com.rocio.aad.config.PostgresqlDriver;
import com.rocio.aad.model.Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio encargado de gestionar el acceso a la tabla de módulos.
 * Aquí realizamos todas las operaciones CRUD usando JDBC puro, tal como
 * establece la práctica. No se utiliza JPA.
 */
@Slf4j
@Repository
public class ModuleRepository {

    private final PostgresqlDriver driver;

    public ModuleRepository(PostgresqlDriver driver) {
        this.driver = driver;
    }

    /**
     * Inserta un nuevo módulo y devuelve la entidad con su id generado.
     */
    public Module insert(Module module) {
        String sql = """
                INSERT INTO modulo (codigo, nombre, horas)
                VALUES (?, ?, ?)
                RETURNING id_modulo
                """;

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setString(1, module.getCode());
            ps.setString(2, module.getName());
            ps.setInt(3, module.getHours());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    module.setId(rs.getInt(1));
                }
            }

            log.info("Inserted module: {}", module.getId());
            return module;

        } catch (SQLException e) {
            log.error("Error inserting module", e);
            throw new RuntimeException("Error inserting module", e);
        }
    }

    /**
     * Recupera todos los módulos almacenados en la base de datos.
     */
    public List<Module> findAll() {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo";
        List<Module> modules = new ArrayList<>();

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql);
             var rs = ps.executeQuery()) {

            while (rs.next()) {
                modules.add(mapModule(rs));
            }

            log.info("Fetched {} modules", modules.size());
            return modules;

        } catch (SQLException e) {
            log.error("Error fetching modules", e);
            throw new RuntimeException("Error fetching modules", e);
        }
    }

    /**
     * Busca un módulo por id. Si no existe, devuelve null.
     */
    public Module findById(int id) {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE id_modulo = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapModule(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            log.error("Error fetching module {}", id, e);
            throw new RuntimeException("Error fetching module", e);
        }
    }

    /**
     * Actualiza un módulo existente.
     */
    public Module update(Module module) {
        String sql = """
                UPDATE modulo
                SET codigo = ?, nombre = ?, horas = ?
                WHERE id_modulo = ?
                """;

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setString(1, module.getCode());
            ps.setString(2, module.getName());
            ps.setInt(3, module.getHours());
            ps.setInt(4, module.getId());

            ps.executeUpdate();

            log.info("Updated module: {}", module.getId());
            return module;

        } catch (SQLException e) {
            log.error("Error updating module {}", module.getId(), e);
            throw new RuntimeException("Error updating module", e);
        }
    }

    /**
     * Elimina un módulo por id.
     */
    public void delete(int id) {
        String sql = "DELETE FROM modulo WHERE id_modulo = ?";

        try (var con = driver.getConnection();
             var ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            log.info("Deleted module: {}", id);

        } catch (SQLException e) {
            log.error("Error deleting module {}", id, e);
            throw new RuntimeException("Error deleting module", e);
        }
    }

    /**
     * Convierte una fila del ResultSet en un objeto Module.
     */
    private Module mapModule(ResultSet rs) throws SQLException {
        return new Module(
                rs.getInt("id_modulo"),
                rs.getString("codigo"),
                rs.getString("nombre"),
                rs.getInt("horas")
        );
    }
}
