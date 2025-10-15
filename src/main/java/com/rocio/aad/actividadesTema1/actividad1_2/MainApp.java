package com.rocio.aad.actividadesTema1.actividad1_2;

import java.io.IOException;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Esta es la clase principal del programa. Permite gestionar alumnos con un fichero binario
 * mediante un menú sencillo que tiene las siguientes opciones:
 * - Insertar alumnos (acceso secuencial)
 * - Consultar alumno por posición (acceso aleatorio)
 * - Modificar notas sin reescribir todo el fichero
 * - Listar todos los alumnos
 */
@SpringBootApplication
@Slf4j
public class MainApp implements CommandLineRunner {

    @Autowired
    private GestorAlumnos gestor; // Inyección automática del servicio de gestión

    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        // Menú principal
        do {
            // Usamos println para que el menú se vea bien
            System.out.println("\n===== GESTIÓN DE ALUMNOS =====");
            System.out.println("1. Insertar nuevo alumno.");
            System.out.println("2. Consultar alumno por posición.");
            System.out.println("3. Modificar nota de un alumno");
            System.out.println("4. Listar todos los alumnos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> insertarAlumno(sc);
                case 2 -> consultarAlumno(sc);
                case 3 -> modificarNota(sc);
                case 4 -> listarAlumnos();
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }

        } while (opcion != 0);

        sc.close();
    }

    // Opción 1: Insertar alumno
    private void insertarAlumno(Scanner sc) {
        try {
            System.out.print("ID: ");
            int id = leerEntero(sc);
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nota: ");
            double nota = leerDouble(sc);

            Alumno nuevo = new Alumno(id, nombre, nota);
            gestor.insertarAlumnos(nuevo);
            log.info("Alumno insertado correctamente: {}", nuevo);
        } catch (IOException e) {
            log.error("Error al insertar el alumno: {}", e.getMessage());
        }
    }

    // Opción 2: Consultar alumno por posición
    private void consultarAlumno(Scanner sc) {
        try {
            System.out.print("Introduce la posición del alumno: ");
            int pos = leerEntero(sc);
            Alumno alumno = gestor.leerAlumnoPorPosicion(pos);
            System.out.println("Datos del alumno -> " + alumno);
        } catch (IOException e) {
            log.error("Error al leer alumno: {}", e.getMessage());
        }
    }

    // Opción 3: Modificar nota
    private void modificarNota(Scanner sc) {
        try {
            System.out.print("Posición del alumno: ");
            int pos = leerEntero(sc);
            System.out.print("Nueva nota: ");
            double nuevaNota = leerDouble(sc);
            gestor.modificarNota(pos, nuevaNota);
            log.info("Nota modificada correctamente.");
        } catch (IOException e) {
            log.error("Error al modificar la nota: {}", e.getMessage());
        }
    }

    // Opción 4: Listar alumnos
    private void listarAlumnos() {
        try {
            System.out.println("\nListado de alumnos:");
            gestor.listarAlumnos();
        } catch (IOException e) {
            log.error("Error al listar alumnos: {}", e.getMessage());
        }
    }

    // Métodos auxiliares de lectura
    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Por favor, introduce un número entero: ");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine(); // limpia el salto de línea
        return num;
    }

    private static double leerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Por favor, introduce un número decimal: ");
            sc.next();
        }
        double num = sc.nextDouble();
        sc.nextLine(); // limpia el salto de línea
        return num;
    }
}
