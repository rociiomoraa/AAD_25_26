package com.rocio.aad.actividadesTema1.actividad1_2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GestorAlumnos {

    /**
     * La clase gestión de alumnos gestiona el acceso al fichero binario de alumnos.
     * Permite insertar, leer por posición y modificar la nota del alumno.
     */

    private String rutaFichero;

    // El constructor recibe la ruta del ficero donde se guardarán los alumnos.
    public GestorAlumnos(String rutaFichero) {
        this.rutaFichero = rutaFichero;

        java.io.File archivo = new java.io.File(rutaFichero);
        java.io.File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs(); // crea las carpetas necesarias
            System.out.println("Carpeta creada: " + carpeta.getAbsolutePath());
        }
    }


    // Este método inserta un nuevo alumno al final del fichero.
    public void insertarAlumnos(Alumno alumno) throws IOException {
        //rw = lectura y escritura
        try (RandomAccessFile raf = new RandomAccessFile(rutaFichero, "rw")) {
            raf.seek(raf.length()); // Se mueve al final del fichero
            alumno.escribir(raf); // Escribe el nuevo registro
        }
    }

    /**
     * Este método lee un alumno por su posición en el fichero (acceso aleatorio).
     * Por ejemplo: posición 0 = primer alumno, posición 1 = segundo alumno...
     */

    public Alumno leerAlumnoPorPosicion(int posicion) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaFichero, "r")) {
            long posicionBytes = (long) posicion * Alumno.tam_registro; //Calcula el desplazamiento en bytes
            if (posicionBytes >= raf.length()) {
                throw new IOException("La posición indicada no existe");
            }
            raf.seek(posicionBytes); // Se mueve justo al inicio del registro
            return Alumno.leer(raf); // Lee y devulve al alumno
        }
    }

    // Este método modifica la nota de un alumno sin reescribir todo el fichero
    public void modificarNota(int posicion, double nuevaNota) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaFichero, "rw")) {
            long posicionBytes = (long) posicion * Alumno.tam_registro;
            if (posicionBytes >= raf.length()) {
                throw new IOException("La posición indicada no existe.");
            }
            // Salta el ID (4 bytes) y el nombre (40 bytes).
            raf.seek(posicionBytes + Integer.BYTES + (Character.BYTES * Alumno.nombre_longitud));
            raf.writeDouble(nuevaNota); // Sobreescribe la nota (8 bytes)
        }
    }

    // Este método muestra todos los alumnos del fichero haciendo un recorrido secuencial
    public void listarAlumnos() throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(rutaFichero, "r")) {
            int contador = 0;
            while (raf.getFilePointer() < raf.length()) {
                Alumno alumno = Alumno.leer(raf);
                System.out.println("Posición " + contador + ": " + alumno);
                contador++;
            }
        }
    }
}
