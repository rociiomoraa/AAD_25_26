# 🐘 PostgreSQL con Docker Compose

## Descripción

Este proyecto levanta un contenedor de **PostgreSQL** usando Docker Compose y define el **esquema inicial** de la base
de datos para la aplicación.  
Además, se amplía la clase `PostgresqlDriver.java` para ejecutar automáticamente los scripts SQL al iniciar la
aplicación.

---

## 🚀 Levantar el servicio

```bash
docker-compose up -d
```

Esto crea el contenedor `aad_db_container` con PostgreSQL y la base de datos definida.

---

## ⚙️ Variables usadas

| Variable              | Valor    | Descripción                 |
|-----------------------|----------|-----------------------------|
| **POSTGRES_DB**       | `aad_db` | Nombre de la base de datos  |
| **POSTGRES_USER**     | `user`   | Usuario de la base de datos |
| **POSTGRES_PASSWORD** | `pass`   | Contraseña del usuario      |

---

## 🧩 Conexión a la base de datos

Desde tu máquina local:

```bash
psql -h localhost -p 5433 -U user -d aad_db
```

O desde dentro del contenedor:

```bash
docker exec -it aad_db_container psql -U user -d aad_db
```

Si ves el prompt `aad_db=#`, la conexión funciona correctamente.

---

## 🧱 Esquema inicial de la base de datos

El esquema se define en el archivo `/resources/sql/ddl.sql` con las siguientes tablas:

```sql
CREATE TABLE alumno
(
    id_alumno SERIAL PRIMARY KEY,
    nombre    VARCHAR(100)        NOT NULL,
    email     VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE modulo
(
    id_modulo SERIAL PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    horas     INT CHECK (horas > 0)
);

CREATE TABLE matricula
(
    id_alumno INT NOT NULL REFERENCES alumno (id_alumno) ON DELETE CASCADE,
    id_modulo INT NOT NULL REFERENCES modulo (id_modulo) ON DELETE CASCADE,
    fecha     DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (id_alumno, id_modulo)
);
```

---

## 🔗 Relaciones entre tablas

Relación muchos a muchos entre `alumno` y `modulo` a través de `matricula`:

```
ALUMNO (1) ───< (N) MATRICULA (N) >─── (1) MODULO
```

---

## 🧠 Consulta de verificación

Para comprobar las relaciones entre las tablas:

```sql
SELECT a.nombre AS alumno, m.nombre AS modulo, ma.fecha
FROM matricula ma
         JOIN alumno a ON ma.id_alumno = a.id_alumno
         JOIN modulo m ON ma.id_modulo = m.id_modulo;
```

---

## 🛠️ Ampliación en `PostgresqlDriver.java`

Se ha añadido un método `init()` para ejecutar automáticamente los scripts SQL al iniciar la aplicación.  
Este método recorre todos los archivos `.sql` del directorio `/resources/sql/` y los ejecuta.

```java

@Value("classpath*:sql/*.sql")
private Resource[] scripts;

@PostConstruct
public void init() {
    log.info("🛠️ Initializing database...");
    for (Resource script : scripts) {
        executeSql(script);
    }
    log.info("✅ Database initialized successfully!");
}

private void executeSql(Resource resource) {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement();
         BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
        String sql = reader.lines().collect(Collectors.joining("\n"));
        stmt.execute(sql);
        log.info("📄 Executed script: {}", resource.getFilename());
    } catch (Exception e) {
        log.error("⚠️ Error executing script {}: {}", resource.getFilename(), e.getMessage());
    }
}
```

Este cambio permite reconstruir fácilmente la base de datos ejecutando los scripts DDL definidos en `resources/sql/`.

---

## ✅ Verificación final

- Todas las tablas se crean correctamente en PostgreSQL.
- Las claves primarias y foráneas garantizan la integridad referencial.
- El script `ddl.sql` permite reproducir el esquema en cualquier entorno.

---

📚 **Autora:** *[Rocio Mora Garcia]*
