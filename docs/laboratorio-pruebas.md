# Laboratorio de pruebas

## Evidencias de Persistencia y Base de Datos (Backend / DB)

### Configuración y creación de la base de datos

Para el laboratorio de pruebas de CatTube se utilizó PostgreSQL 17 como sistema gestor de base de datos, pgAdmin 4 para su administración y Visual Studio Code para desarrollar el backend con Java y Spring Boot.

La base de datos `loquito` se creó mediante pgAdmin 4. Posteriormente, se configuró la conexión desde Spring Boot y se definieron las entidades necesarias para generar las tablas.

**Archivos utilizados en Visual Studio Code:**

| Archivo | Función |
|---|---|
| `application.properties` | Configura la conexión a PostgreSQL, la base de datos `loquito`, el puerto `5433` y las propiedades de Hibernate. |
| `Usuario.java` | Define la entidad correspondiente a la tabla `usuario`, con los campos `id_usuario`, `seguidores`, `dinero`, `influencia` y `cohesion`. |
| `Short.java` | Define la entidad correspondiente a la tabla `short`, con los campos `id_video`, `titulo`, `vistas`, `likes` y la relación con `usuario`. |
| `ShortRepository.java` | Permite realizar operaciones de consulta y persistencia sobre la tabla `short` mediante Spring Data JPA. |
| `ShortController.java` | Implementa los endpoints GET y POST para consultar y registrar shorts. |

**Proceso de creación de las tablas**

Las tablas `usuario` y `short` se generaron automáticamente en PostgreSQL mediante Hibernate, utilizando las entidades Java y la configuración `spring.jpa.hibernate.ddl-auto=update`.

La tabla `usuario` contiene la información del perfil de El loquito, mientras que `short` almacena sus videos cortos. Ambas tablas están relacionadas mediante la llave foránea `id_usuario`, que permite asociar cada short con su usuario correspondiente.

Después de iniciar Spring Boot, se verificó en pgAdmin que las tablas y su relación se hubieran creado correctamente.

### 1. Scripts de Carga y Limpieza (Data Provisioning)

Se prepararon los scripts `01-carga.sql` y `02-limpieza.sql` para administrar los datos iniciales del laboratorio en PostgreSQL.

El script de carga registra un usuario correspondiente a El loquito y tres shorts asociados mediante la llave foránea `id_usuario`. El script de limpieza permite eliminar los registros de prueba y reiniciar los identificadores para repetir las pruebas.

### 2. Capturas o Logs de Ejecución de Queries

Se realizaron pruebas de consulta y registro de información mediante los servicios REST de Spring Boot.

**Prueba GET:** Se ejecutó una petición a `/api/shorts`, obteniendo correctamente los registros almacenados en PostgreSQL. La respuesta HTTP fue `200 OK`.

**Prueba POST:** Se enviaron nuevos shorts mediante peticiones POST a `/api/shorts`. El servidor respondió con `201 Created` y generó los identificadores correspondientes.

Posteriormente, se verificó en pgAdmin que los nuevos registros permanecieran almacenados en la tabla `short`, conservando su relación con el usuario.

### 3. Logs del Servidor Backend (Application Logs)

Se configuraron registros de ejecución para visualizar las consultas SQL, los parámetros enviados y las métricas de las peticiones HTTP.

| Métrica | GET | POST |
|---|---|---|
| Código HTTP | 200 | 201 |
| Tiempo de procesamiento | 775 ms | 89 ms |
| Memoria antes | 61,345 KB | 45,883 KB |
| Memoria después | 43,984 KB | 52,735 KB |

Los valores de memoria corresponden al uso aproximado del heap de Java durante las peticiones.

Los logs de Hibernate permitieron identificar las consultas `SELECT` y la operación `INSERT` realizada sobre PostgreSQL.

## Resultados

Las pruebas realizadas confirmaron que el backend puede consultar y registrar shorts en PostgreSQL mediante los métodos GET y POST. También se verificó la persistencia de los registros y la relación entre las tablas `usuario` y `short`.

Las pruebas de integración con la interfaz y de funcionamiento offline de la PWA se realizarán posteriormente.



