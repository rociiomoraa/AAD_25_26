package com.rocio.aad;

import com.rocio.aad.model.Enrollment;
import com.rocio.aad.model.Module;
import com.rocio.aad.model.Student;
import com.rocio.aad.repository.EnrollmentRepository;
import com.rocio.aad.repository.ModuleRepository;
import com.rocio.aad.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

/**
 * Clase principal de la aplicación. Aquí realizamos una pequeña prueba creando
 * un alumno, un módulo y una matrícula para comprobar que los repositorios
 * funcionan con JDBC y que la base de datos está bien configurada.
 */
@Slf4j
@SpringBootApplication
public class AadApplication implements CommandLineRunner {

    private final StudentRepository studentRepo;
    private final ModuleRepository moduleRepo;
    private final EnrollmentRepository enrollmentRepo;

    /**
     * Inyectamos los repositorios necesarios para realizar las operaciones
     * de prueba al iniciar la aplicación.
     */
    public AadApplication(StudentRepository studentRepo,
                          ModuleRepository moduleRepo,
                          EnrollmentRepository enrollmentRepo) {
        this.studentRepo = studentRepo;
        this.moduleRepo = moduleRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    /**
     * Este método se ejecuta al arrancar la aplicación. Aquí simplemente
     * creamos un alumno, un módulo y realizamos una matrícula para verificar
     * que toda la cadena JDBC funciona correctamente.
     */
    @Override
    public void run(String... args) throws Exception {

        // Crear alumno de ejemplo
        Student student = new Student(
                null,
                "50299841Z",
                "María Gómez",
                "maria.gomez@example.com",
                null
        );
        student = studentRepo.insert(student);
        log.info("Student created: {}", student);

        // Crear módulo de ejemplo
        Module module = new Module(
                null,
                "PSP",
                "Programación de Servicios y Procesos",
                180
        );
        module = moduleRepo.insert(module);
        log.info("Module created: {}", module);

        // Matricular alumno en el módulo
        Enrollment enrollment = new Enrollment(
                null,
                student.getId(),
                module.getId(),
                LocalDate.now()
        );
        enrollmentRepo.createEnrollment(enrollment);
        log.info("Student enrolled successfully");

        // Borrar alumno (comentado para no eliminarlo realmente)
        // studentRepo.delete(student.getId());
        // log.info("Student deleted");

        log.info("Sample test completed successfully");
    }
}
