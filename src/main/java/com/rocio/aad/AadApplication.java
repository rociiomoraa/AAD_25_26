package com.rocio.aad;

import com.rocio.aad.model.Modulo;
import com.rocio.aad.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
@Slf4j
@RequiredArgsConstructor

public class AadApplication implements CommandLineRunner {

    private final StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student("12345678a", "Rocío", "García", "Computer Since");
        Modulo module1 = new Modulo("CS101", "Computer Since");
        Modulo module2 = new Modulo("CS102", "Data Structures");
        List<Modulo> modules = List.of(module1, module2);
        studentService.createStudent(student, modulo);

        if (crea != null) {
            log.info("Create: {}", create);

        }
    }
}
