package com.rocio.aad.repository;

import com.rocio.aad.model.Student;
import com.rocio.aad.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Repositorio responsable de gestionar el fichero binario de alumnos.
 * <p>
 * Esta clase permite insertar, consultar y modificar registros en el fichero
 * utilizando la clase RandomAccessFile, lo que facilita tanto el acceso
 * secuencial como el acceso aleatorio.
 * <p>
 * Cada registro tiene un tamaño fijo definido en Constants.RECORD_SIZE.
 */
@Slf4j
@Repository
public class StudentRepository {

    /**
     * Inserta un nuevo alumno al final del fichero (acceso secuencial).
     *
     * @param student Alumno a insertar.
     */
    public void insertStudent(Student student) {
        try (RandomAccessFile file = new RandomAccessFile(Constants.FILE_NAME, "rw")) {
            file.seek(file.length());
            writeStudent(file, student);
            log.info("Student inserted: id={}, name={}, grade={}", student.getId(), student.getName(), student.getGrade());
        } catch (IOException e) {
            log.error("Error writing student: {}", e.getMessage());
        }
    }

    /**
     * Lee un alumno por su posición (acceso aleatorio).
     *
     * @param position Posición del alumno (empezando desde 0).
     * @return El alumno leído o null si no existe.
     */
    public Student readStudent(int position) {
        try (RandomAccessFile file = new RandomAccessFile(Constants.FILE_NAME, "r")) {
            long offset = (long) position * Constants.RECORD_SIZE;
            if (offset >= file.length()) {
                log.warn("Position {} out of range. File size: {} bytes", position, file.length());
                return null;
            }
            file.seek(offset);
            Student student = readStudentData(file);
            log.info("Student read from position {}: {}", position, student);
            return student;
        } catch (IOException e) {
            log.error("Error reading student: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Modifica la nota de un alumno directamente en el fichero sin reescribirlo.
     *
     * @param position Posición del alumno (empezando desde 0).
     * @param newGrade Nueva nota a escribir.
     */
    public void updateGrade(int position, float newGrade) {
        try (RandomAccessFile file = new RandomAccessFile(Constants.FILE_NAME, "rw")) {
            long offset = (long) position * Constants.RECORD_SIZE + 44; // salto hasta el campo nota
            if (offset >= file.length()) {
                log.warn("Position {} out of range when updating grade.", position);
                return;
            }
            file.seek(offset);
            file.writeFloat(newGrade);
            log.info("Updated grade for student at position {} to {}", position, newGrade);
        } catch (IOException e) {
            log.error("Error updating grade: {}", e.getMessage());
        }
    }

    /**
     * Escribe un registro completo de alumno en el fichero binario.
     *
     * @param file    Fichero RandomAccessFile abierto.
     * @param student Alumno que se va a escribir.
     * @throws IOException Si ocurre un error de escritura.
     */
    private void writeStudent(RandomAccessFile file, Student student) throws IOException {
        file.writeInt(student.getId());
        writeFixedString(file, student.getName(), Constants.NAME_LENGTH);
        file.writeFloat(student.getGrade());
    }

    /**
     * Lee un registro completo de alumno desde el fichero.
     *
     * @param file Fichero RandomAccessFile abierto.
     * @return Alumno leído.
     * @throws IOException Si ocurre un error de lectura.
     */
    private Student readStudentData(RandomAccessFile file) throws IOException {
        int id = file.readInt();
        String name = readFixedString(file, Constants.NAME_LENGTH);
        float grade = file.readFloat();
        return new Student(id, name.trim(), grade);
    }

    /**
     * Escribe una cadena de longitud fija en el fichero, rellenando con espacios si es necesario.
     *
     * @param file   Fichero donde escribir.
     * @param text   Cadena a escribir.
     * @param length Longitud fija del campo.
     * @throws IOException Si ocurre un error de escritura.
     */
    private void writeFixedString(RandomAccessFile file, String text, int length) throws IOException {
        StringBuilder builder = new StringBuilder(text);
        if (builder.length() > length) {
            builder.setLength(length);
        } else {
            while (builder.length() < length) {
                builder.append(' ');
            }
        }
        file.writeChars(builder.toString());
    }

    /**
     * Lee una cadena de longitud fija desde el fichero.
     *
     * @param file   Fichero donde leer.
     * @param length Longitud fija esperada.
     * @return Cadena leída.
     * @throws IOException Si ocurre un error de lectura.
     */
    private String readFixedString(RandomAccessFile file, int length) throws IOException {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = file.readChar();
        }
        return new String(chars);
    }
}

