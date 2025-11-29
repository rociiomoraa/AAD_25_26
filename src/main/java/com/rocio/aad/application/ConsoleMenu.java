package com.rocio.aad.application;

import com.rocio.aad.model.Enrollment;
import com.rocio.aad.model.Module;
import com.rocio.aad.model.Student;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Menú de consola para realizar operaciones CRUD sobre estudiantes, módulos
 * y matrículas. Este menú interactúa con StudentManagementService, que a su
 * vez delega en los repositorios basados en JdbcTemplate.
 */
public class ConsoleMenu {

    private final StudentManagementService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(StudentManagementService service) {
        this.service = service;
    }

    /**
     * Punto de entrada del menú principal.
     */
    public void start() {
        boolean exit = false;

        while (!exit) {
            printMainMenu();
            int option = readInt("Seleccione una opción: ");

            switch (option) {
                case 1 -> studentMenu();
                case 2 -> moduleMenu();
                case 3 -> enrollmentMenu();
                case 0 -> exit = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    // ============================================================
    // MENÚ PRINCIPAL
    // ============================================================

    private void printMainMenu() {
        System.out.println("\n======================================");
        System.out.println("      SISTEMA DE GESTIÓN ACADÉMICA");
        System.out.println("======================================");
        System.out.println("1. Gestión de estudiantes");
        System.out.println("2. Gestión de módulos");
        System.out.println("3. Gestión de matrículas");
        System.out.println("0. Salir");
        System.out.println("======================================");
    }

    // ============================================================
    // SUBMENÚ: ESTUDIANTES
    // ============================================================

    private void studentMenu() {
        System.out.println("\n--- Gestión de estudiantes ---");
        System.out.println("1. Crear estudiante");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Buscar estudiante por ID");
        System.out.println("4. Actualizar estudiante");
        System.out.println("5. Eliminar estudiante");
        System.out.println("0. Volver");

        int option = readInt("Seleccione una opción: ");

        switch (option) {
            case 1 -> createStudent();
            case 2 -> listStudents();
            case 3 -> findStudentById();
            case 4 -> updateStudent();
            case 5 -> deleteStudent();
            case 0 -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    private void createStudent() {
        System.out.println("\n--- Crear estudiante ---");

        String nif = readString("NIF: ");
        String name = readString("Nombre: ");
        String email = readString("Email: ");

        Student s = new Student(null, nif, name, email);
        s = service.createStudent(s);

        System.out.println("Estudiante creado: " + s);
    }

    private void listStudents() {
        var list = service.getAllStudents();
        System.out.println("\n--- Lista de estudiantes ---");
        list.forEach(System.out::println);
    }

    private void findStudentById() {
        int id = readInt("ID del estudiante: ");
        var s = service.getStudentById(id);
        System.out.println(s != null ? s : "No encontrado");
    }

    private void updateStudent() {
        int id = readInt("ID del estudiante a actualizar: ");
        var s = service.getStudentById(id);

        if (s == null) {
            System.out.println("El estudiante no existe.");
            return;
        }

        String name = readString("Nuevo nombre (" + s.getName() + "): ");
        String email = readString("Nuevo email (" + s.getEmail() + "): ");

        s.setName(name);
        s.setEmail(email);

        service.updateStudent(s);

        System.out.println("Estudiante actualizado.");
    }

    private void deleteStudent() {
        int id = readInt("ID del estudiante a eliminar: ");
        service.deleteStudent(id);
        System.out.println("Estudiante eliminado.");
    }

    // ============================================================
    // SUBMENÚ: MÓDULOS
    // ============================================================

    private void moduleMenu() {
        System.out.println("\n--- Gestión de módulos ---");
        System.out.println("1. Crear módulo");
        System.out.println("2. Listar módulos");
        System.out.println("3. Buscar módulo por ID");
        System.out.println("4. Actualizar módulo");
        System.out.println("5. Eliminar módulo");
        System.out.println("0. Volver");

        int option = readInt("Seleccione una opción: ");

        switch (option) {
            case 1 -> createModule();
            case 2 -> listModules();
            case 3 -> findModuleById();
            case 4 -> updateModule();
            case 5 -> deleteModule();
            case 0 -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    private void createModule() {
        System.out.println("\n--- Crear módulo ---");

        String code = readString("Código: ");
        String name = readString("Nombre: ");
        int hours = readInt("Horas: ");

        Module m = new Module(null, code, name, hours);
        m = service.createModule(m);

        System.out.println("Módulo creado: " + m);
    }

    private void listModules() {
        var list = service.getAllModules();
        System.out.println("\n--- Lista de módulos ---");
        list.forEach(System.out::println);
    }

    private void findModuleById() {
        int id = readInt("ID del módulo: ");
        var m = service.getModuleById(id);
        System.out.println(m != null ? m : "No encontrado");
    }

    private void updateModule() {
        int id = readInt("ID del módulo a actualizar: ");
        var m = service.getModuleById(id);

        if (m == null) {
            System.out.println("El módulo no existe.");
            return;
        }

        String name = readString("Nuevo nombre (" + m.getName() + "): ");
        int hours = readInt("Nuevas horas (" + m.getHours() + "): ");

        m.setName(name);
        m.setHours(hours);

        service.updateModule(m);

        System.out.println("Módulo actualizado.");
    }

    private void deleteModule() {
        int id = readInt("ID del módulo a eliminar: ");
        service.deleteModule(id);
        System.out.println("Módulo eliminado.");
    }

    // ============================================================
    // SUBMENÚ: MATRÍCULAS
    // ============================================================

    private void enrollmentMenu() {
        System.out.println("\n--- Gestión de matrículas ---");
        System.out.println("1. Crear matrícula");
        System.out.println("2. Listar todas");
        System.out.println("3. Listar por estudiante");
        System.out.println("4. Eliminar matrícula");
        System.out.println("5. Contar matrículas de un estudiante");
        System.out.println("0. Volver");

        int option = readInt("Seleccione una opción: ");

        switch (option) {
            case 1 -> createEnrollment();
            case 2 -> listEnrollments();
            case 3 -> listEnrollmentsByStudent();
            case 4 -> deleteEnrollment();
            case 5 -> countEnrollments();
            case 0 -> {
            }
            default -> System.out.println("Opción no válida");
        }
    }

    private void createEnrollment() {
        System.out.println("\n--- Crear matrícula ---");

        int studentId = readInt("ID del estudiante: ");
        int moduleId = readInt("ID del módulo: ");

        Enrollment e = new Enrollment(null, studentId, moduleId, LocalDate.now());
        service.createEnrollment(e);

        System.out.println("Matrícula creada.");
    }

    private void listEnrollments() {
        var list = service.getAllEnrollments();
        System.out.println("\n--- Lista de matrículas ---");
        list.forEach(System.out::println);
    }

    private void listEnrollmentsByStudent() {
        int studentId = readInt("ID del estudiante: ");
        var list = service.getEnrollmentsByStudent(studentId);
        list.forEach(System.out::println);
    }

    private void deleteEnrollment() {
        int studentId = readInt("ID del estudiante: ");
        int moduleId = readInt("ID del módulo: ");

        service.deleteEnrollment(studentId, moduleId);
        System.out.println("Matrícula eliminada.");
    }

    private void countEnrollments() {
        int studentId = readInt("ID del estudiante: ");
        int total = service.countEnrollments(studentId);
        System.out.println("Total de matrículas: " + total);
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================

    private int readInt(String txt) {
        System.out.print(txt);
        return Integer.parseInt(scanner.nextLine());
    }

    private String readString(String txt) {
        System.out.print(txt);
        return scanner.nextLine();
    }
}
