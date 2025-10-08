package com.rocio.aad.actividadesTema1.actividad1_2;

import java.io.IOException;
import java.util.Scanner;

public class MainApp {
    /**
     * Esta es la clase principal del programa. Permite gestionar alumnos con un fichero binario
     * meidante un menú sencillo que tiene las siguientes opciones:
     * - Insertar alumnos (acceso secuencial)
     * - Consultar alumno por posición (acceso aleatorio)
     * - Modificar notas sin reescribir todo el fichero
     * - Listar todos los alumnos
     */
    public static void main(String[] args) {

        // Inicialización
        Scanner sc = new Scanner(System.in);
        // Se crea el fichero donde se guardarán los registros
        GestorAlumnos gestor = null;
        try {
            gestor = new GestorAlumnos("src/main/java/com/rocio/aad/actividadesTema1/actividad1_2/alumnos.dat");
        } catch (Exception e) {
            System.out.println("Error al crear el gestor: " + e.getMessage());
            return;
        }

        int opcion; // Opcion del menú

        // Crearmos el menu principal
        do {
            System.out.println("\n===== GESTIÓN DE ALUMNOS =====");
            System.out.println("1. Insertar nuevo alumno.");
            System.out.println("2. Consultar alumno por posición.");
            System.out.println("3. Modificar nota de un alumno");
            System.out.println("4. Listar todos los alumnos");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero(sc);

            switch (opcion) {
                case 1 -> insertarAlumno(sc, gestor);
                case 2 -> consultarAlumno(sc, gestor);
                case 3 -> modificarNota(sc, gestor);
                case 4 -> listarAlumnos(gestor);
                case 0 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        sc.close();
    }

    // Creamos los métodos auxiliares para cada opción del menú.

    private static void insertarAlumno(Scanner sc, GestorAlumnos gestor) {
        try {
            System.out.print("ID: ");
            int id = leerEntero(sc);
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Nota: ");
            double nota = leerDouble(sc);

            Alumno nuevo = new Alumno(id, nombre, nota);
            gestor.insertarAlumnos(nuevo);
            System.out.println("Alumno insertado correctamente");
        } catch (IOException e) {
            System.out.println("Error al insertar el alumno: " + e.getMessage());
        }
    }

    private static void consultarAlumno(Scanner sc, GestorAlumnos gestor) {
        try {
            System.out.print("Introduce la posición del alumno: ");
            int pos = leerEntero(sc);
            Alumno alumno = gestor.leerAlumnoPorPosicion(pos);
            System.out.println("Datos del alumno -> " + alumno);
        } catch (IOException e) {
            System.out.println("Error al leer alumno: " + e.getMessage());
        }
    }

    private static void modificarNota(Scanner sc, GestorAlumnos gestor) {
        try {
            System.out.print("Posición del alumno: ");
            int pos = leerEntero(sc);
            System.out.print("Nueva nota: ");
            double nuevaNota = leerDouble(sc);
            gestor.modificarNota(pos, nuevaNota);
            System.out.println("Nota modificada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al modificar la nota: " + e.getMessage());
        }
    }

    private static void listarAlumnos(GestorAlumnos gestor) {
        try {
            System.out.println("\nListado de alumnos:");
            gestor.listarAlumnos();
        } catch (IOException e) {
            System.out.println("Error al listar alumnos: " + e.getMessage());
        }
    }

    // Métodos de lectura segura de numeros enteros y decimaales
    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Por favor, introduce un número entero: ");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine(); // Limpia el salto de línea
        return num;
    }

    private static double leerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Por favor, introduce un número decimal: ");
            sc.next();
        }
        double num = sc.nextDouble();
        sc.nextLine(); // Limpia el salto de línea
        return num;
    }

}
