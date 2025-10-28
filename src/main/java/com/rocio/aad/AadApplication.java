package com.rocio.aad;

import com.rocio.aad.model.Student;
import com.rocio.aad.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal de la aplicación de gestión de alumnos con acceso binario.
 * <p>
 * Permite realizar las siguientes operaciones:
 * 1. Insertar alumnos (acceso secuencial)
 * 2. Consultar alumno por posición (acceso aleatorio)
 * 3. Modificar la nota de un alumno sin reescribir el fichero
 * 4. Listar todos los alumnos
 * <p>
 * Usa la clase StudentService para coordinar las operaciones y Lombok para los logs.
 */
@SpringBootApplication
@Slf4j
public class AadApplication implements CommandLineRunner {

    @Autowired
    private StudentService studentService; // Inyección automática del servicio

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    // Métodos auxiliares de lectura para validar la entrada
    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter an integer: ");
            sc.next();
        }
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }

    private static float readFloat(Scanner sc) {
        while (!sc.hasNextFloat()) {
            System.out.print("Please enter a valid decimal number: ");
            sc.next();
        }
        float num = sc.nextFloat();
        sc.nextLine();
        return num;
    }

    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            log.info("\n===== STUDENT MANAGEMENT MENU =====");
            log.info("1. Insert new student");
            log.info("2. Read student by position");
            log.info("3. Update student grade");
            log.info("4. List all students");
            log.info("0. Exit");
            System.out.print("Choose an option: ");

            option = readInt(sc);

            switch (option) {
                case 1 -> insertStudent(sc);
                case 2 -> readStudent(sc);
                case 3 -> updateGrade(sc);
                case 4 -> listStudents();
                case 0 -> log.info("Exiting program...");
                default -> log.warn("Invalid option, please try again.");
            }

        } while (option != 0);

        sc.close();
    }

    /**
     * Inserta un nuevo alumno al final del fichero.
     */
    private void insertStudent(Scanner sc) {
        System.out.print("ID: ");
        int id = readInt(sc);
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Grade: ");
        float grade = readFloat(sc);

        studentService.addStudent(id, name, grade);
        log.info("Student inserted successfully: id={}, name={}, grade={}", id, name, grade);
    }

    /**
     * Lee un alumno por su posición (acceso aleatorio).
     */
    private void readStudent(Scanner sc) {
        System.out.print("Enter position: ");
        int position = readInt(sc);

        Optional<Student> student = studentService.getStudentByPosition(position);
        student.ifPresentOrElse(
                s -> log.info("Student found -> {}", s),
                () -> log.warn("No student found at that position.")
        );
    }

    /**
     * Modifica la nota de un alumno.
     */
    private void updateGrade(Scanner sc) {
        System.out.print("Enter position: ");
        int position = readInt(sc);
        System.out.print("New grade: ");
        float newGrade = readFloat(sc);

        studentService.updateStudentGrade(position, newGrade);
        log.info("Grade updated for position {} to {}", position, newGrade);
    }

    /**
     * Lista todos los alumnos guardados en el fichero.
     */
    private void listStudents() {
        int index = 0;
        Optional<Student> student;
        log.info("\n===== STUDENT LIST =====");

        // Leer secuencialmente hasta que no haya más registros
        while ((student = studentService.getStudentByPosition(index)).isPresent()) {
            log.info("{} -> {}", index, student.get());
            index++;
        }

        if (index == 0) {
            log.warn("No students registered yet.");
        }
    }
}



