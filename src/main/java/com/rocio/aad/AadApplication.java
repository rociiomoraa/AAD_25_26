package com.rocio.aad;

import com.rocio.aad.model.Student;
import com.rocio.aad.repository.CsvStudentRepository;
import com.rocio.aad.service.JsonStudentService;
import com.rocio.aad.service.XmlStudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

/**
 * Clase principal de la aplicación.
 * Su función es ejecutar el proceso completo:
 * 1. Leer el fichero CSV con los alumnos.
 * 2. Convertirlo a JSON.
 * 3. Convertirlo a XML.
 */
@SpringBootApplication
@Slf4j
public class AadApplication {

    public static void main(String[] args) {
        SpringApplication.run(AadApplication.class, args);

        // Creamos los objetos necesarios para ejecutar el proceso.
        // Se hace manualmente, pero podría hacerse con @Autowired (no lo hemos dado aún).
        CsvStudentRepository csvStudentRepository = new CsvStudentRepository();
        JsonStudentService jsonService = new JsonStudentService();
        XmlStudentService xmlService = new XmlStudentService();

        try {
            // 1️⃣ Leemos los estudiantes desde el CSV.
            List<Student> students = csvStudentRepository.readAllFromClasspath();
            log.info("Students read from CSV: {}", students.size());

            // 2️⃣ Generamos el fichero JSON.
            jsonService.writeStudentsToJson(students);
            log.info("JSON file 'alumnos.json' successfully created.");

            // 3️⃣ Generamos el fichero XML.
            xmlService.writeStudentsToXml(students);
            log.info("XML file 'alumnos.xml' successfully created.");

            // 4️⃣ Mensaje final indicando éxito.
            log.info("\nProcess completed successfully.");

        } catch (Exception e) {
            // 5️⃣ En caso de error, lo mostramos por consola.
            log.error("Error during the process: {}", e.getMessage());
        }
    }
}

