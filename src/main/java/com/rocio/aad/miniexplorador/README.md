# MiniExplorador de Ficheros

Esta actividad consiste en la creación de un programa en Java que permite al usuario explorar el contenido de un directorio, diferenciando entre ficheros y subdirectorios, y realizar operaciones básicas sobre los ficheros.

El programa se ejecuta por consola y utiliza la clase `File` de Java junto con utilidades de `java.nio.file` para manipular archivos y directorios.

## Objetivos del ejercicio
- Aplicar el uso de la clase `File` y rutas en Java.
- Leer datos desde consola con `Scanner`.
- Implementar operaciones de gestión de archivos: creación, movimiento y borrado.
- Controlar errores y excepciones en el manejo de ficheros.
- Organizar el desarrollo en pasos y registrarlos con Git mediante commits descriptivos.

## Funcionalidades implementadas
1. **Comprobación de ruta válida**: solicita una ruta y verifica que exista y sea un directorio.
2. **Listado del contenido**: muestra ficheros y subdirectorios, con tamaño y fecha de última modificación.
3. **Menú principal**:
    - Crear un fichero vacío.
    - Mover un fichero a otra ubicación.
    - Borrar un fichero existente.
    - Salir del programa.
4. **Gestión de errores**: control mediante `try/catch` y validaciones (rutas inexistentes, permisos, ficheros que no existen, etc.).

## Estructura del proyecto
```
com.rocio.aad.miniexplorador
│
├── MiniExplorador.java
└── README.md
```

## Ejecución
1. Compilar y ejecutar desde el IDE (IntelliJ) ejecutando la clase `MiniExplorador`.
2. O desde terminal en la raíz del proyecto Maven:


mvn compile
mvn exec:java -Dexec.mainClass="com.rocio.aad.miniexplorador.MiniExplorador"


Al iniciar, introducir una ruta válida del sistema (por ejemplo, una carpeta del Escritorio).

Usar el menú para seleccionar la acción deseada.

### Ejemplo de ejecución

Introduce una ruta de directorio: C:\Users\rocio\Desktop
Contenido del directorio:
[DIR] Acceso A Datos
[FILE] ejemplo.txt (123 bytes) - Última modificación: 01/10/2025 14:25

=== MENÚ MINI EXPLORADOR DE FICHEROS ===
1. Crear un nuevo fichero vacío.
2. Mover un fichero a otra ubicación.
3. Borrar un fichero existente.
4. Salir.
   Selecciona una opción: 1
   Introduce el nombre del nuevo fichero (con su extensión): prueba.txt
   Fichero creado correctamente: C:\Users\rocio\Desktop\prueba.txt

## Tecnologías utilizadas
- **Lenguaje:** Java 17
- **Entorno:** IntelliJ IDEA
- **Construcción:** Maven
- **Librerías/clases:**  
  `java.io.File`,  
  `java.nio.file.Files`,  
  `java.nio.file.Path`,  
  `java.nio.file.StandardCopyOption`,  
  `java.text.SimpleDateFormat`

## Control de versiones
Se ha utilizado **Git** para registrar los avances con commits separados y descriptivos, incluyendo:

- Creación de rama de trabajo.
- Creación de clase base.
- Petición y validación de ruta.
- Listado del contenido del directorio.
- Menú principal y opciones (crear, mover, borrar).

## Mejora propuesta
En una versión más compleja o de mayor escala, se propone refactorizar el código separando cada operación (listar, crear, mover, borrar) 
en métodos independientes. Esto mejoraría la modularidad, la mantenibilidad y la capacidad de prueba. Para esta actividad introductoria, la estructura actual es suficiente y clara.

## Autora
**Rocío Mora García**  
Acceso a Datos — Curso 2025/2026
