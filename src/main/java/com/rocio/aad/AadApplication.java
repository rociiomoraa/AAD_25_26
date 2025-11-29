package com.rocio.aad;

import com.rocio.aad.application.ConsoleMenu;
import com.rocio.aad.application.StudentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal. En esta actividad (UD02.2) la base de datos
 * y los datos iniciales se gestionan externamente mediante scripts SQL,
 * por lo que el arranque únicamente lanza el menú interactivo que
 * permite probar las operaciones CRUD con JDBC.
 */
@SpringBootApplication
@RequiredArgsConstructor
public class AadApplication implements CommandLineRunner {

    private final StudentManagementService studentManagementService;

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ConsoleMenu menu = new ConsoleMenu(studentManagementService);
        menu.start();
    }
}
