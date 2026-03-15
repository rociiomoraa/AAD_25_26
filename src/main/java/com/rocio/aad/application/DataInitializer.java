package com.rocio.aad.application;

import com.rocio.aad.model.Module;
import com.rocio.aad.model.Profile;
import com.rocio.aad.model.Student;
import com.rocio.aad.service.StudentManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    // Servicio principal que gestiona estudiantes, módulos y matrículas
    private final StudentManagementService managementService;

    @Override
    public void run(String... args) {

        log.info("=== INICIALIZANDO DATOS DE PRUEBA ===");

        // =========================
        // CREACIÓN DE PERFILES
        // =========================
        Profile profile1 = new Profile();
        profile1.setAddress("Calle Mayor 1");
        profile1.setPhone("600111222");

        Profile profile2 = new Profile();
        profile2.setAddress("Avenida Sol 3");
        profile2.setPhone("600333444");

        // =========================
        // CREACIÓN DE ESTUDIANTES
        // =========================
        Student rocio = new Student();
        rocio.setNif("11111111A");
        rocio.setName("Rocío");
        rocio.setEmail("rocio@email.com");
        rocio.setCourse("DAM");
        rocio.setProfile(profile1);

        Student juan = new Student();
        juan.setNif("22222222B");
        juan.setName("Juan");
        juan.setEmail("juan@email.com");
        juan.setCourse("DAM");
        juan.setProfile(profile2);

        // Guardamos los estudiantes usando el servicio
        rocio = managementService.createStudent(rocio);
        juan = managementService.createStudent(juan);

        log.info("Alumno creado: {}", rocio.getName());
        log.info("Alumno creado: {}", juan.getName());

        // =========================
        // CREACIÓN DE MÓDULOS
        // =========================
        Module accesoDatos = new Module();
        accesoDatos.setCode("AD");
        accesoDatos.setName("Acceso a Datos");
        accesoDatos.setHours(180);

        Module programacion = new Module();
        programacion.setCode("PRO");
        programacion.setName("Programación");
        programacion.setHours(256);

        accesoDatos = managementService.createModule(accesoDatos);
        programacion = managementService.createModule(programacion);

        log.info("Módulo creado: {}", accesoDatos.getName());
        log.info("Módulo creado: {}", programacion.getName());

        // =========================
        // MATRÍCULAS
        // =========================
        managementService.enrollStudent(rocio.getId(), accesoDatos.getId());
        log.info("Matrícula realizada: {} -> {}", rocio.getName(), accesoDatos.getName());

        managementService.enrollStudent(rocio.getId(), programacion.getId());
        log.info("Matrícula realizada: {} -> {}", rocio.getName(), programacion.getName());

        managementService.enrollStudent(juan.getId(), programacion.getId());
        log.info("Matrícula realizada: {} -> {}", juan.getName(), programacion.getName());

        // =========================
        // CONSULTA DE MATRÍCULAS
        // =========================
        int total = managementService.countEnrollments(rocio.getId());
        log.info("Matrículas de {}: {}", rocio.getName(), total);

        log.info("=== FIN DE INICIALIZACIÓN ===");

        // =========================
        // PRUEBA DE ROLLBACK
        // =========================
        // Este bloque sirve para comprobar que las transacciones funcionan.
        // Si se lanza una excepción, la operación se revierte.

        /*
        Student error = new Student();
        error.setNif("ERROR");
        error.setName("Rollback");
        error.setEmail("error@email.com");
        error.setCourse("DAM");

        managementService.createStudent(error);
        throw new RuntimeException("Forzando rollback");
        */
    }
}