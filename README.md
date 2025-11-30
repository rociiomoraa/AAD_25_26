## Actividad 2.2. Migración del Acceso a Datos a Spring JdbcTemplate

Este proyecto corresponde a la **Actividad 2.2** de la asignatura *Acceso a Datos (AAD)* y consiste en migrar el sistema
académico desarrollado en la Actividad 2.1, sustituyendo todo el acceso a datos basado en **JDBC manual** por un enfoque
totalmente integrado en **Spring Boot** utilizando **JdbcTemplate**, **SimpleJdbcCall**, **transacciones declarativas**
y el **DataSource autoconfigurado**.

La aplicación mantiene la misma funcionalidad que la versión anterior, pero ahora cuenta con una arquitectura más
profesional, estable y mantenible.

---

## Índice

1. [Descripción general](#descripción-general)
2. [Tecnologías utilizadas](#tecnologías-utilizadas)
3. [Estructura del proyecto](#estructura-del-proyecto)
4. [Inicialización automática de la base de datos](#inicialización-automática-de-la-base-de-datos)
5. [Migración a Spring JDBC](#migración-a-spring-jdbc)
6. [Funcionalidades implementadas](#funcionalidades-implementadas)
7. [Conclusión personal](#conclusión-personal)

---

## Descripción general

En esta práctica se ha reemplazado por completo el uso de **JDBC puro** (Connection, PreparedStatement, ResultSet,
commits y rollbacks manuales) por una arquitectura basada en:

- **JdbcTemplate** para consultas SQL seguras y simplificadas.
- **SimpleJdbcCall** para la ejecución de funciones almacenadas.
- **@Transactional** para el manejo automático de transacciones.
- **DataSource autoconfigurado** mediante Spring Boot.

Los repositorios han sido reescritos para utilizar JdbcTemplate y la capa de servicio ahora delega el control
transaccional en Spring.

---

## Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring JDBC / JdbcTemplate**
- **PostgreSQL 16 (Docker o local)**
- **PL/pgSQL**
- **Maven**
- **Lombok**

---

## Estructura del proyecto

```
src/
 └── main/
     ├── java/com/rocio/aad/
     │   ├── AadApplication.java
     │   ├── application/
     │   │     ├── ConsoleMenu.java
     │   │     └── StudentManagementService.java
     │   ├── config/
     │   │     ├── JdbcConfig.java
     │   │     └── DatabaseInitializer.java
     │   ├── model/
     │   │     ├── Student.java
     │   │     ├── Module.java
     │   │     └── Enrollment.java
     │   └── repository/
     │         ├── StudentRepository.java
     │         ├── ModuleRepository.java
     │         └── EnrollmentRepository.java
     └── resources/
         ├── application.yml
         └── sql/ddl/
              ├── 01_schema.sql
              ├── 02_procedures.sql
              └── 03_sample_data.sql
```

---

## Inicialización automática de la base de datos

Los scripts SQL se ejecutan al arrancar la aplicación mediante la clase:

```
DatabaseInitializer.java
```

Scripts:

1. `01_schema.sql` → creación de tablas
2. `02_procedures.sql` → función `count_enrollments`
3. `03_sample_data.sql` → datos de prueba

---

## Migración a Spring JDBC

Cambios principales implementados:

- Eliminación de la clase `PostgresqlDriver`.
- Sustitución total del acceso JDBC manual por **JdbcTemplate**.
- Uso de RowMapper con funciones lambda.
- Sustitución de CallableStatement por **SimpleJdbcCall**.
- Implementación de **@Transactional** en métodos del servicio.
- Centralización de la configuración del DataSource.

---

## Funcionalidades implementadas

### 1. CRUD con JdbcTemplate

- Estudiantes
- Módulos
- Matrículas

### 2. Consultas parametrizadas

- findById / findByNif / findByCode
- exists / existsByNif / existsByCode

### 3. Llamada a funciones almacenadas

- `count_enrollments(student_id)` mediante SimpleJdbcCall

### 4. Transacciones declarativas

- Manejo automático de commit y rollback

---

## Conclusión personal

La migración a Spring JDBC ha supuesto una mejora notable en la estructura del proyecto.  
El sistema conserva la misma funcionalidad, pero ahora el acceso a datos es mucho más limpio, seguro y fácil de
mantener.  
Spring se encarga del manejo de conexiones, transacciones y ejecución de consultas, lo cual reduce errores y mejora la
claridad del código.  
Este proyecto marca un avance importante hacia una arquitectura más profesional, escalable y alineada con las prácticas
modernas de desarrollo en Spring Boot.

---

Proyecto desarrollado por **Rocío Mora García** — DAM
