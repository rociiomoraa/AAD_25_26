package com.rocio.aad.actividadesTema1.actividad1_2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Alumno {

    /**
     * La clase alumno representa a un alumno con un id, un nombre de longitud fija y una nota.
     * Cada alumno se va a guardar con un registro binario de un tamaño fijo (52 bytes)
     * para poder acceder de forma aleatoria a cualquier registro.
     */

    // Creamos las constantes
    public static final int nombre_longitud = 20; // número fijo de caracteres para el nombre
    public static final int tam_registro = Integer.BYTES + (Character.BYTES * nombre_longitud) + Double.BYTES;
    // Esto significa que 4 bytes (int) + 40 bytes (20 chars x 2 bytes cada uno) + 8 bytes (double) = 52 bytes

    // Atributos
    private int id;
    private String nombre;
    private double nota;

    // Constructor
    public Alumno(int id, String nombre, double nota) {
        this.id = id;
        this.nombre = ajustarNombre (nombre);
        this.nota = nota;
    }

    /**
     * Creamos un método que ajuste el nombre a una longitud fija,
     * haciendo que se rellenen espacios o que se acorte si es muy largo
     */

    private static String ajustarNombre (String nombre) {
        if (nombre == null) nombre = "";
        if (nombre.length() > nombre_longitud) {
            //Si el nombre es más largo de 20 caracteres, se corta
            return nombre.substring(0, nombre_longitud);
        } else {
            //Si el nombre es más corto, se rellenan los espacios hasta llegar a 20
            return String.format("%-" + nombre_longitud + "s", nombre);
        }
    }

    /**
     * Creamos un método para guardar al alumno en un fichero binario (RandomAccessFile)
     * El método escribe los datos en orden: id, nombre, nota.
     */

    public void escribir(RandomAccessFile raf) throws IOException {
        raf.writeInt(id); //Escribe 4 bytes (int)
        raf.writeChars(nombre); // Escribe 40 bytes (20 caracteres)
        raf.writeDouble(nota); // Escribe 8 bytes (double)
    }

    /**
     * Creamos un método que lee un alumno desde la posición actual del fichero binario y devuleve
     * un objeto Alumno con los datos leídos.
     */

    public static Alumno leer(RandomAccessFile raf) throws IOException {
        int id = raf.readInt(); // Lee 4 bytes
        char [] nombreChars = new char[nombre_longitud];
        for (int i = 0; i < nombre_longitud; i++) {
            nombreChars[i] = raf.readChar(); // Lee los 40 bytes de los 20 caracteres
        }
        String nombre = new String(nombreChars).trim();
        double nota = raf.readDouble(); // Lee 8 bytes
        return new Alumno(id, nombre, nota);
    }

    // Getters y Setters

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre.trim();
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    // Método toString
    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Nota: %.2f", id, getNombre(), nota);
    }

}

