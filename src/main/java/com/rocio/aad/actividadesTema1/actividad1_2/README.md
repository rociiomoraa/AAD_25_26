# Gestión de Notas con Acceso Secuencial y Aleatorio

## 📘 Descripción

Aplicación Java que gestiona un fichero binario con registros de alumnos mediante **acceso secuencial y aleatorio**.
Permite insertar, consultar, modificar y listar alumnos almacenados en un fichero de tamaño fijo.

El fichero se gestiona mediante `RandomAccessFile` y cada registro tiene una longitud fija de **52 bytes**:
- `id` → 4 bytes (entero)
- `nombre` → 40 bytes (20 caracteres × 2 bytes cada uno)
- `nota` → 8 bytes (double)

Esto permite acceder directamente a cualquier registro sin tener que leer todo el fichero.

---

## 🧱 Estructura del proyecto

```
src/main/java/com/rocio/aad/actividadesTema1/actividad1_2/
├── Alumno.java          # Define el registro binario de cada alumno
├── GestorAlumnos.java   # Gestiona el acceso secuencial y aleatorio al fichero
├── MainApp.java         # Clase principal con menú de opciones
└── alumnos.dat          # Fichero binario donde se almacenan los registros
```

---

## ⚙️ Funcionalidades

| Opción | Descripción |
|--------|--------------|
| 1 | Insertar nuevo alumno (acceso secuencial) |
| 2 | Consultar alumno por posición (acceso aleatorio) |
| 3 | Modificar la nota de un alumno sin reescribir el fichero |
| 4 | Listar todos los alumnos |
| 0 | Salir del programa |

---

## 💾 Funcionamiento interno

- Los registros se escriben con longitud fija, lo que permite calcular la posición exacta de cualquier alumno.
- Se usa `seek()` para posicionarse en el registro deseado.
- El programa crea automáticamente la carpeta de la actividad si no existe.
- El fichero `alumnos.dat` se guarda dentro de la carpeta `actividad1_2` para mantener la organización.

---

## 🧪 Ejemplo de uso

```
===== GESTIÓN DE ALUMNOS =====
1. Insertar nuevo alumno
2. Consultar alumno por posición
3. Modificar nota de un alumno
4. Listar todos los alumnos
0. Salir
Elige una opción: 1
ID: 1
Nombre: Ana
Nota: 8.5
✅ Alumno insertado correctamente.

Elige una opción: 4
📘 Listado de alumnos:
Posición 0: ID: 1 | Nombre: Ana | Nota: 8.50
```

---

## 📂 Ruta del fichero de datos

El archivo binario se guarda en:
```
src/main/java/com/rocio/aad/actividadesTema1/actividad1_2/alumnos.dat
```

Esto permite mantener cada actividad dentro de su propia carpeta, organizada y limpia.

---

## 🧠 Aspectos técnicos trabajados

- Acceso **secuencial** y **aleatorio** a ficheros binarios.
- Uso de `RandomAccessFile`.
- Registros de **tamaño fijo** para garantizar el acceso directo.
- Manejo de excepciones (`IOException`).
- Estructuración modular en clases (`Alumno`, `GestorAlumnos`, `MainApp`).
- Organización del proyecto siguiendo buenas prácticas.

---

## 👩‍💻 Autora

**Rocío Moraa**  
Actividad desarrollada para el módulo *Acceso a Datos (AAD)*.  
Curso 2025/2026
