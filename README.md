# 🧩 Conversor de Formatos (CSV ↔ JSON ↔ XML)

📚 **Unidad 1 – Manejo de ficheros**  
Esta práctica forma parte del módulo de **Acceso a Datos (DAM – 2º curso)** y tiene como objetivo trabajar con ficheros
de intercambio de datos y la conversión entre diferentes formatos (CSV, JSON y XML).

---

## 🧠 Enunciado

El programa desarrollado permite convertir un fichero de alumnos en distintos formatos de almacenamiento:

- **Entrada:** `alumnos.csv` con la estructura:
  ```
  id,nombre,nota
  1,Ana,8.5
  2,Juan,6.7
  3,Luis,9.0
  ```
- **Salida:**
    - `alumnos.json` → Representación JSON del listado de alumnos.
    - `alumnos.xml` → Representación XML del mismo contenido.

Los datos se modelan mediante la clase `Alumno`, con los atributos:

```java
private int id;
private String nombre;
private double nota;
```

---

## 🧩 Contenidos trabajados

- Lectura y escritura de ficheros CSV.
- Uso de librerías **Jackson** para la serialización en **JSON** y **XML**.
- Uso de **ObjectMapper** y **XmlMapper** con `PrettyPrinter()` para generar ficheros legibles.
- Conversión entre formatos de ficheros.
- Gestión de excepciones en operaciones de entrada y salida de datos.

---

## ⚙️ Dependencias (pom.xml)

```xml
<!-- Jackson Core -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>${jackson.version}</version>
</dependency>

        <!-- Databind (serialización / deserialización) -->
<dependency>
<groupId>com.fasterxml.jackson.core</groupId>
<artifactId>jackson-databind</artifactId>
<version>${jackson.version}</version>
</dependency>

        <!-- XML -->
<dependency>
<groupId>com.fasterxml.jackson.dataformat</groupId>
<artifactId>jackson-dataformat-xml</artifactId>
<version>${jackson.version}</version>
</dependency>
```

---

## 🚀 Ejecución del programa

1. Crear el fichero `alumnos.csv` con los datos de ejemplo.
2. Ejecutar la aplicación desde el entorno (por ejemplo, IntelliJ o VS Code).
3. El programa leerá el CSV, lo cargará en una lista de objetos `Alumno` y generará los ficheros:
    - `alumnos.json`
    - `alumnos.xml`

4. Los ficheros se crean en la misma carpeta de ejecución y su contenido es legible gracias al uso del método
   `.writerWithDefaultPrettyPrinter()`.

---

## 🧪 Ejemplo de salida (JSON)

```json
[
  {
    "id": 1,
    "nombre": "Ana",
    "nota": 8.5
  },
  {
    "id": 2,
    "nombre": "Juan",
    "nota": 6.7
  },
  {
    "id": 3,
    "nombre": "Luis",
    "nota": 9.0
  }
]
```

---

## 🧾 Criterios de evaluación cumplidos

| Criterio | Descripción                                                                     | Estado |
|----------|---------------------------------------------------------------------------------|--------|
| **c**    | Uso correcto de clases para recuperar información de ficheros (lectura de CSV). | ✅      |
| **d**    | Uso de clases para almacenar información (generación de JSON y XML).            | ✅      |
| **e**    | Conversión entre formatos de ficheros utilizando clases adecuadas (Jackson).    | ✅      |
| **f**    | Manejo completo de excepciones de entrada/salida y validación de datos.         | ✅      |

---

## ✍️ Autora

**Rocío Mora García**  
📧 [rocio.mora.garcia02@gmail.com](mailto:rocio.mora.garcia02@gmail.com)  
🔗 [linkedin.com/in/rociiomoraa](https://linkedin.com/in/rociiomoraa)  
📸 [instagram.com/rociiomoraa_](https://www.instagram.com/rociiomoraa_)