package com.rocio.aad.miniexplorador;

import java.io.File;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MiniExplorador {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);// Con esto leemos la entrada del usuario

        System.out.print("Introduce una ruta de directorio: "); //Pedimos la ruta al usuario
        String ruta = sc.nextLine(); // Guardamos los datos que aporta el usuario

        File directorio = new File(ruta); // Creamos un objeto File con esa ruta

        // Comprobamos si existe y es un directorio
        if (directorio.exists() && directorio.isDirectory()) {
            System.out.println("Contenido del directorio:");

            File[] elementos = directorio.listFiles(); // Obtenemos lista de archivos y carpetas

            if (elementos != null && elementos.length > 0) {
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                for (File elemento : elementos) {
                    if (elemento.isDirectory()) {
                        System.out.println("[DIR] " + elemento.getName());
                    } else {
                        long tamano = elemento.length(); // Tamaño en bytes
                        String fecha = formatoFecha.format(new Date(elemento.lastModified()));
                        System.out.println("[FILE] " + elemento.getName() + " (" + tamano + " bytes) - Última modificación: " + fecha);
                    }
                }
            } else {
                System.out.println("El directorio está vacío.");
            }

        } else {
            System.out.println("La ruta no existe o no es un directorio.");
        }

        sc.close(); // Cerramos el Scanner
    }
}