package com.rocio.aad.actividadesTema1.actividad1_2;

import java.io.IOException;
import java.io.RandomAccessFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data // Lombok genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Constructor vacío
@AllArgsConstructor // Constructor con todos los parámetros
@Component // Permite que Spring gestione esta clase si es necesario
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

    // Ajusta el nombre al tamaño fijo
    private String ajustarNombre(String nombre) {
        StringBuilder sb = new StringBuilder(nombre);
        if (sb.length() > nombre_longitud) {
            sb.setLength(nombre_longitud);
        } else {
            while (sb.length() < nombre_longitud) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }

    // Método para escribir un alumno en el fichero binario
    public void escribir(RandomAccessFile file) throws IOException {
        file.writeInt(id);
        String nombreAjustado = ajustarNombre(nombre);
        for (int i = 0; i < nombre_longitud; i++) {
            file.writeChar(nombreAjustado.charAt(i));
        }
        file.writeDouble(nota);
    }

    // Método para leer un alumno desde el fichero binario
    public static Alumno leer(RandomAccessFile file) throws IOException {
        int id = file.readInt();
        StringBuilder nombre = new StringBuilder();
        for (int i = 0; i < nombre_longitud; i++) {
            nombre.append(file.readChar());
        }
        double nota = file.readDouble();
        return new Alumno(id, nombre.toString().trim(), nota);
    }
}
