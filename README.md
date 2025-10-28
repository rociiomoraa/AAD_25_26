# 🎓 Gestión de Alumnos - Fichero Binario con Acceso Aleatorio

Aplicación desarrollada en **Java**, utilizando **Spring Boot** y **Lombok**, que permite gestionar registros de alumnos
almacenados en un **fichero binario** de tamaño fijo.

Cada registro contiene:

- `id` (entero, 4 bytes)
- `nombre` (cadena de 20 caracteres fijos, 40 bytes)
- `nota` (float, 4 bytes)

Cada registro ocupa **48 bytes**, lo que permite acceder directamente a cualquier alumno mediante su posición.

---

## 🧩 Funcionalidades principales

✅ Insertar nuevos alumnos (acceso secuencial).  
✅ Consultar un alumno por su posición (acceso aleatorio).  
✅ Modificar la nota de un alumno sin reescribir el fichero.  
✅ Listar todos los alumnos almacenados.  
✅ Control de errores y logs profesionales (`log.info`, `log.error`, `log.warn`).

---

## ⚙️ Estructura del proyecto

```
src/
 └─ main/
    ├─ java/com/rocio/aad/
    │   ├─ model/
    │   │   └─ Student.java
    │   ├─ repository/
    │   │   └─ StudentRepository.java
    │   ├─ service/
    │   │   └─ StudentService.java
    │   ├─ util/
    │   │   └─ Constants.java
    │   └─ AadApplication.java
    └─ resources/
        └─ application.properties
```

---

## 🧱 Diseño del fichero binario

| Campo     | Tipo       | Bytes  | Descripción                                        |
|-----------|------------|--------|----------------------------------------------------|
| id        | int        | 4      | Identificador del alumno                           |
| nombre    | String(20) | 40     | Nombre con longitud fija (20 caracteres × 2 bytes) |
| nota      | float      | 4      | Nota del alumno                                    |
| **Total** |            | **48** | bytes por registro                                 |

---

## 🚀 Ejecución del programa

1. Clonar o descargar el proyecto.
2. Compilar con Maven:
   ```bash
   mvn clean package
   ```
3. Ejecutar el `.jar` generado:
   ```bash
   java -jar target/aad-0.0.1-SNAPSHOT.jar
   ```

4. Al iniciarse, se mostrará un menú interactivo en consola:

```
===== STUDENT MANAGEMENT MENU =====
1. Insert new student
2. Read student by position
3. Update student grade
4. List all students
0. Exit
Choose an option:
```

---

## 🧠 Ejemplo de uso

### ➕ Insertar alumnos

```
ID: 1
Name: Ana
Grade: 8.5
Student inserted successfully.
```

### 🔍 Consultar alumno

```
Enter position: 0
Student found -> Student(id=1, name=Ana, grade=8.5)
```

### ✏️ Modificar nota

```
Enter position: 0
New grade: 9.0
Grade updated for position 0 to 9.0
```

### 📋 Listar alumnos

```
===== STUDENT LIST =====
0 -> Student(id=1, name=Ana, grade=9.0)
```

---

## 🧾 Criterios de evaluación cumplidos

| Criterio | Descripción                                                                                                  | Estado |
|----------|--------------------------------------------------------------------------------------------------------------|--------|
| **b**    | Comentarios y Javadoc claros. Logs y pruebas demuestran el funcionamiento del acceso secuencial y aleatorio. | ✅      |
| **c**    | Uso correcto de `RandomAccessFile` para lectura de registros.                                                | ✅      |
| **d**    | Escritura binaria de registros de tamaño fijo con acceso directo.                                            | ✅      |

---

## 🧠 Conceptos trabajados

- Acceso **secuencial** vs **aleatorio** en ficheros.
- Uso de `RandomAccessFile` para leer y escribir bytes.
- Manejo de **registros de tamaño fijo**.
- Control de excepciones y recursos con `try-with-resources`.
- Integración con **Spring Boot** y **Lombok**.

---

## 👩‍💻 Autora

**Rocío Mora Garcia**  
📅 2025  
📚 Módulo: Acceso a Datos  
🏫 IES La Marisma
