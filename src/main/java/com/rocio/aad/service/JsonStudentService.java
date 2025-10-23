package com.rocio.aad.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rocio.aad.model.Student;
import com.rocio.aad.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Esta clase se encarga de convertir la lista de alumnos en un fichero formato JSON.
 */
@Slf4j
@Service
public class JsonStudentService {

    /**
     * Convierte la lista de alumnos en un fichero formato JSON.
     *
     * @param students La lista de alumnos a convertir.
     */
    public void writeStudentsToJson(List<Student> students) {
        try {
            // 1. Creamos un ObjectMapper de Jackson (gestiona la conversión a JSON).
            ObjectMapper mapper = new ObjectMapper();

            // 2. Creamos un ObjectWriter con formato indentado para que el JSON sea legible.
            ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

            // 3. Creamos un archivo con el nombre definido en las constantes.
            File outputFile = new File(Constants.STUDENTS_JSON);

            // 4. Escribimos la lista de estudiantes en el archivo JSON.
            writer.writeValue(outputFile, students);

            // 5. Mostramos un mensaje indicando que el archivo se ha creado correctamente.
            log.info("JSON file created successfully: {}", outputFile.getAbsolutePath());

        } catch (Exception e) {
            // 6. En caso de error, mostramos un mensaje de error.
            log.error("Error creating JSON file: {}", e.getMessage());
        }
    }
}

