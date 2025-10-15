package com.rocio.aad.actividadesTema1.actividad1_2;

import org.springframework.stereotype.Service;
import java.io.*;

/**
 * Clase GestorAlumnos.
 * Se encarga de gestionar el fichero binario de alumnos
 * mediante operaciones de inserción, lectura, modificación y listado.
 */
@Service // Se marca como servicio para que Spring la reconozca y la cree automáticamente
public class GestorAlumnos {

    private final String FILE_PATH;

    // Constructor: recibe la ruta del fichero
    public GestorAlumnos() {
        // Ruta por defecto
        this.FILE_PATH = "src/main/java/com/rocio/aad/actividadesTema1/actividad1_2/alumnos.dat";
    }

    public GestorAlumnos(String ruta) {
        this.FILE_PATH = ruta;
    }

    /**
     * Inserta un nuevo alumno al final del fichero (acceso secuencial).
     */
    public void insertarAlumnos(Alumno alumno) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_PATH, "rw")) {
            file.seek(file.length()); // mueve el puntero al final
            alumno.escribir(file);
        }
    }

    /**
     * Lee un alumno por posición (acceso aleatorio).
     */
    public Alumno leerAlumnoPorPosicion(int posicion) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_PATH, "r")) {
            long pos = (long) posicion * Alumno.tam_registro;
            if (pos >= file.length() || pos < 0) {
                throw new IOException("Posición fuera del rango del fichero.");
            }
            file.seek(pos);
            return Alumno.leer(file);
        }
    }

    /**
     * Modifica la nota de un alumno sin reescribir todo el fichero.
     */
    public void modificarNota(int posicion, double nuevaNota) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_PATH, "rw")) {
            long pos = (long) posicion * Alumno.tam_registro; // coherente con leerAlumnoPorPosicion
            if (pos >= file.length() || pos < 0) {
                throw new IOException("Posición fuera del rango del fichero.");
            }
            file.seek(pos);
            Alumno alumno = Alumno.leer(file);
            alumno.setNota(nuevaNota);
            file.seek(pos);
            alumno.escribir(file);
        }
    }

    /**
     * Lista todos los alumnos guardados en el fichero.
     */
    public void listarAlumnos() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_PATH, "r")) {
            long numRegistros = file.length() / Alumno.tam_registro;
            for (int i = 0; i < numRegistros; i++) {
                Alumno alumno = Alumno.leer(file);
                System.out.println(alumno);
            }
        }
    }
}
