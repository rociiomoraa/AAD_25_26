package com.rocio.aad.repository;

import com.rocio.aad.model.Student;
import com.rocio.aad.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase se encarga de leer el fichero CSV desde la carpeta resources
 * y convertir cada línea en un objeto Student.
 */
@Slf4j
@Repository
public class CsvStudentRepository {

    /**
     * Este método abre y lee el fichero CSV definido en Constants.STUDENTS_CSV.
     * Por cada línea de datos, crea un objeto Student con sus valores (id, name, grade).
     *
     * @return una lista con todos los estudiantes encontrados.
     * @throws Exception si el archivo no existe, tiene formato incorrecto o ocurre un error de lectura.
     */
    public List<Student> readAllFromClasspath() throws Exception {

        // 1. Cargamos el fichero CSV desde resources usando la constante definida.
        // ClassPathResource permite acceder a archivos dentro del proyecto.
        var resource = new ClassPathResource(Constants.STUDENTS_CSV);

        // Si el archivo no existe, mostramos un error y detenemos la ejecución.
        if (!resource.exists()) {
            log.error("The CSV file {} does not exist in the resources folder.", Constants.STUDENTS_CSV);
            throw new IllegalStateException("CSV file not found: " + Constants.STUDENTS_CSV);
        }

        // 2. Creamos una lista donde guardaremos todos los estudiantes.
        List<Student> students = new ArrayList<>();

        // 3. Abrimos el archivo y lo leemos línea a línea.
        try (var reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;  // Guarda cada línea leída.
            int lineNo = 0;  // Contador para saber en qué línea estamos.

            // Leemos línea por línea hasta el final del archivo.
            while ((line = reader.readLine()) != null) {
                lineNo++;

                // Si la línea está vacía, la saltamos.
                if (line.isBlank()) continue;

                // 4. Validamos la cabecera (solo en la primera línea).
                if (lineNo == 1) {
                    if (!Constants.EXPECTED_HEADER.equalsIgnoreCase(line.trim())) {
                        throw new IllegalArgumentException("Invalid CSV header (line " + lineNo + "): " + line);
                    }
                    continue; // Saltamos a la siguiente línea (ya validamos la cabecera).
                }

                // 5. Dividimos los valores por comas.
                String[] parts = line.split(",");

                // Si la línea no tiene exactamente 3 columnas, el formato es incorrecto.
                if (parts.length != 3) {
                    log.error("Incorrect format in line {}: {}", lineNo, line);
                    throw new IllegalArgumentException("Incorrect format in line " + lineNo + ": " + line);
                }

                // 6. Convertimos los valores a su tipo correspondiente.
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double grade = Double.parseDouble(parts[2].trim());

                // 7. Creamos un objeto Student con los datos y lo añadimos a la lista.
                students.add(new Student(id, name, grade));
            }
        }

        // 8. Mostramos un mensaje en consola con la cantidad de alumnos leídos.
        log.info("CSV file successfully read: {} students found", students.size());

        // 9. Devolvemos la lista de estudiantes.
        return students;
    }
}

