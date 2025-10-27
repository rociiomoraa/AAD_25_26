# 🐘 PostgreSQL con Docker Compose

## Descripción

Este proyecto levanta un contenedor de **PostgreSQL** usando Docker Compose.  
El **conector** es lo que usa la aplicación para comunicarse con la base de datos (por ejemplo, una librería como
`psycopg2` en Python o `pg` en Node.js).

---

## Levantar el servicio

``` bash
 docker-compose up -d
```

Esto crea el contenedor `aad_db_container` con PostgreSQL.

---

## Variables usadas

- **POSTGRES_DB:** `aad_db` → nombre de la base de datos
- **POSTGRES_USER:** `user` → usuario de la base de datos
- **POSTGRES_PASSWORD:** `pass` → contraseña del usuario

---

## Probar conexión

Desde mi máquina:

```bash
psql -h localhost -p 5433 -U user -d aad_db
```

O desde dentro del contenedor:

```bash
docker exec -it aad_db_container psql -U user -d aad_db
```

Si ves el prompt `aad_db=#`, la conexión funciona.
