# 🌟 Gestor de Logs - Acceso a Datos

Aplicación desarrollada en **Java** utilizando **Spring Boot** y **Lombok**.  
Su objetivo es gestionar los registros (*logs*) de una aplicación, permitiendo añadir eventos con fecha y hora,
filtrarlos por día y configurar la codificación del fichero.

---

## 🧰 Funcionalidades principales

✨ **Añadir eventos al fichero `app.log`**  
Guarda cada evento con su fecha y hora exacta en el formato **DD-MM-YYYY HH:mm:ss**.

🔍 **Filtrar eventos por fecha**  
Permite mostrar solo los eventos registrados en un día concreto.

💾 **Codificación configurable**  
Los logs se almacenan en **UTF-8** (con opción de cambiar a **ISO-8859-1**).

🧩 **Gestión de errores y excepciones**  
Controla las excepciones de entrada/salida y formato de forma segura.

📚 **Documentación completa**  
Cada clase y método cuenta con **Javadoc** y este **README.md** explica su uso y requisitos.

---

## 🧱 Estructura del proyecto

```
src/
 └─ main/
    ├─ java/com/rocio/aad/
    │   ├─ model/
    │   │   └─ LogEvent.java
    │   ├─ repository/
    │   │   └─ LogRepository.java
    │   ├─ service/
    │   │   ├─ EncodingService.java
    │   │   └─ LogService.java
    │   ├─ util/
    │   │   └─ Constants.java
    │   └─ AadApplication.java
    └─ resources/
        └─ application.properties
```

---

## ⚙️ Requisitos

- ☕ **Java 17** o superior
- 🧱 **Maven 3.8+**
- 🌱 **Spring Boot 3.x**
- 🧩 **Lombok habilitado** (Annotation Processing activado en tu IDE)

---

## 🚀 Ejecución del programa

1. Clona o descarga el proyecto.
2. Compila con Maven:
   ```bash
   mvn clean package
   ```
3. Ejecuta el `.jar` generado:
   ```bash
   java -jar target/aad-0.0.1-SNAPSHOT.jar
   ```

Al iniciar, la aplicación:

- Añade eventos de ejemplo al log.
- Filtra los eventos del día actual.
- Cambia la codificación (opcionalmente) y escribe nuevos registros.
- Muestra la actividad en consola con `log.info()`.

---

## 🧠 Ejemplo de salida en consola

```
🚀 Application started - Log Manager Demo
INFO  Log event added successfully: User Ana logged in
INFO  Log event added successfully: User Carlos updated profile
INFO  Log entries found for 28-10-2025: 3
→ [28-10-2025 10:32:45] User Ana logged in
→ [28-10-2025 10:32:48] User Carlos updated profile
→ [28-10-2025 10:33:00] System backup completed successfully
INFO  Encoding changed to ISO-8859-1.
INFO  Encoding changed to UTF-8.
✅ Demo completed. Check 'app.log' for output.
```

---

## 📄 Ejemplo del fichero `app.log`

```
[28-10-2025 10:32:45] User Ana logged in
[28-10-2025 10:32:48] User Carlos updated profile
[28-10-2025 10:33:00] System backup completed successfully
[28-10-2025 10:34:00] Test entry written with ISO-8859-1 encoding
[28-10-2025 10:34:10] Encoding switched back to UTF-8
```

---

## 🧾 Criterios de evaluación cumplidos

| Criterio | Descripción                                                | Estado |
|----------|------------------------------------------------------------|--------|
| a        | Gestión de ficheros con `File`, `BufferedWriter`, etc.     | ✅      |
| c        | Recuperación y filtrado de información desde el fichero    | ✅      |
| d        | Almacenamiento con formato `[DD-MM-YYYY HH:mm:ss] Mensaje` | ✅      |
| e        | Conversión entre UTF-8 e ISO-8859-1                        | ✅      |
| f        | Manejo de excepciones controlado                           | ✅      |
| g        | Código documentado con Javadoc + README.md                 | ✅      |

---

## 💬 Notas importantes

- El fichero de log se crea automáticamente si no existe (`app.log`).
- Todos los registros se guardan en la raíz del proyecto.
- Se recomienda mantener la codificación en UTF-8 para compatibilidad total.
- El cambio de codificación a ISO-8859-1 es opcional pero valorable positivamente.

---

## 👩‍💻 Autora

**Rocío Mora Garcia**  
📅 28-10-2025  
📚 Módulo: Acceso a Datos  
🏫 IES La Marisma

---

> Proyecto desarrollado como práctica de **Acceso a Datos** en Java. 