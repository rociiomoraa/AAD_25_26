package com.rocio.aad.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.rocio.aad.model.Student;
import com.rocio.aad.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Esta clase se encarga de convertir la lista de alumnos en un fichero formato XML.
 */
@Slf4j
@Service
public class XmlStudentService {

    /**
     * Convierte la lista de alumnos en un fichero formato XML.
     *
     * @param students La lista de alumnos a convertir.
     */
    public void writeStudentsToXml(List<Student> students) {
        try {
            // 1. Creamos un XmlMapper de Jackson (gestiona la conversión a XML).
            XmlMapper xmlMapper = new XmlMapper();

            // 2. Activamos el "pretty print" para que el XML sea legible.
            xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(Constants.STUDENTS_XML), students);

            // 3. Mostramos un mensaje indicando que el archivo se ha creado correctamente.
            log.info("XML file successfully generated: {}", Constants.STUDENTS_XML);

        } catch (Exception e) {
            // 4. Si ocurre un error, lo mostramos en consola.
            log.error("Error while generating the XML file: {}", e.getMessage());
        }
    }
}

