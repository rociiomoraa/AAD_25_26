package com.rocio.aad.repository;

import com.rocio.aad.model.Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio encargado de gestionar la tabla "modulo"
 * utilizando JdbcTemplate. Aquí se implementan las operaciones
 * CRUD sin JPA, siguiendo los requisitos de la actividad.
 */
@Slf4j
@Repository
public class ModuleRepository {

    private final JdbcTemplate jdbc;

    public ModuleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
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

        try {
            Integer id = jdbc.queryForObject(
                    sql,
                    Integer.class,
                    module.getCode(),
                    module.getName(),
                    module.getHours()
            );

            module.setId(id);
            log.info("Inserted module: {}", module.getId());
            return module;

        } catch (Exception e) {
            log.error("Error inserting module", e);
            throw new RuntimeException("Error inserting module", e);
        }
    }

    /**
     * Recupera todos los módulos almacenados en la base de datos.
     */
    public List<Module> findAll() {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo";

        try {
            List<Module> modules = jdbc.query(sql, (rs, rowNum) ->
                    new Module(
                            rs.getInt("id_modulo"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("horas")
                    )
            );

            log.info("Fetched {} modules", modules.size());
            return modules;

        } catch (Exception e) {
            log.error("Error fetching modules", e);
            throw new RuntimeException("Error fetching modules", e);
        }
    }

    /**
     * Busca un módulo por id. Si no existe, devuelve null.
     */
    public Module findById(int id) {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE id_modulo = ?";

        try {
            return jdbc.query(
                    sql,
                    (rs, rowNum) ->
                            new Module(
                                    rs.getInt("id_modulo"),
                                    rs.getString("codigo"),
                                    rs.getString("nombre"),
                                    rs.getInt("horas")
                            ),
                    id
            ).stream().findFirst().orElse(null);

        } catch (Exception e) {
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

        try {
            jdbc.update(
                    sql,
                    module.getCode(),
                    module.getName(),
                    module.getHours(),
                    module.getId()
            );

            log.info("Updated module: {}", module.getId());
            return module;

        } catch (Exception e) {
            log.error("Error updating module {}", module.getId(), e);
            throw new RuntimeException("Error updating module", e);
        }
    }

    /**
     * Elimina un módulo por id.
     */
    public void delete(int id) {
        String sql = "DELETE FROM modulo WHERE id_modulo = ?";

        try {
            jdbc.update(sql, id);
            log.info("Deleted module: {}", id);

        } catch (Exception e) {
            log.error("Error deleting module {}", id, e);
            throw new RuntimeException("Error deleting module", e);
        }
    }

    /**
     * Busca un módulo por su código único. Si no existe, devuelve null.
     */
    public Module findByCode(String code) {
        String sql = "SELECT id_modulo, codigo, nombre, horas FROM modulo WHERE codigo = ?";

        try {
            return jdbc.query(
                    sql,
                    (rs, rowNum) ->
                            new Module(
                                    rs.getInt("id_modulo"),
                                    rs.getString("codigo"),
                                    rs.getString("nombre"),
                                    rs.getInt("horas")
                            ),
                    code
            ).stream().findFirst().orElse(null);

        } catch (Exception e) {
            log.error("Error fetching module with code {}", code, e);
            throw new RuntimeException("Error fetching module by code", e);
        }
    }

    /**
     * Comprueba si ya existe un módulo con un código determinado.
     */
    public boolean existsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM modulo WHERE codigo = ?";

        try {
            Integer count = jdbc.queryForObject(sql, Integer.class, code);
            return count != null && count > 0;

        } catch (Exception e) {
            log.error("Error checking existence of module {}", code, e);
            return false;
        }
    }
}
