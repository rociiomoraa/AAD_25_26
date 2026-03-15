package com.rocio.aad.repository;

import com.rocio.aad.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    // Método derivado de Spring Data JPA.
    // Permite buscar un módulo a partir de su código.
    // Spring genera automáticamente la consulta SQL
    // basándose en el nombre del método.
    Optional<Module> findByCode(String code);
}