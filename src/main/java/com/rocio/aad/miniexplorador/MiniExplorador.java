package com.rocio.aad.miniexplorador;

import java.io.File;
import java.util.Scanner;

public class MiniExplorador {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);// Con esto leemos la entrada del usuario

        System.out.print("Introduce una ruta de directorio: "); //Pedimos la ruta al usuario
        String ruta = sc.nextLine(); // Guardamos los datos que aporta el usuario

        File directorio = new File(ruta); // Creamos un objeto File con esa ruta

        // Comprobamos si existe y es un directorio
        if (directorio.exists() && directorio.isDirectory()) {
            System.out.println("La ruta existe y es un directorio.");
        } else {
            System.out.println("La ruta no existe o no es un directorio.");
        }

        sc.close(); // Cerramos el Scanner
    }
}