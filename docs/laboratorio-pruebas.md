# Laboratorio de pruebas

## Evidencias de Persistencia y Base de Datos (Backend / DB)

### Configuración y creación de la base de datos

Para el laboratorio de pruebas de CatTube se utilizó PostgreSQL 17 como sistema gestor de base de datos, pgAdmin 4 para su administración, Postman para las pruebas de la API REST y Visual Studio Code para desarrollar el backend con Java y Spring Boot.

La base de datos `loquito` se creó mediante pgAdmin 4. También se configuró la conexión desde Spring Boot y se definieron las entidades necesarias para generar las tablas.

**Archivos utilizados en Visual Studio Code:**

| Archivo | Función |
|---|---|
| `application.properties` | Configura la conexión a PostgreSQL, la base de datos `loquito`, el puerto `5433` y las propiedades de Hibernate. |
| `Usuario.java` | Define la entidad correspondiente a la tabla `usuario`, con los campos `id_usuario`, `seguidores`, `dinero`, `influencia` y `cohesion`. |
| `Short.java` | Define la entidad correspondiente a la tabla `short`, con los campos `id_video`, `titulo`, `vistas`, `likes` y la relación con `usuario`. |
| `ShortRepository.java` | Permite realizar operaciones de consulta y persistencia sobre la tabla `short` mediante Spring Data JPA. |
| `UsuarioRepository.java` | Permite realizar operaciones de consulta y persistencia sobre la tabla `usuario` mediante Spring Data JPA. |
| `ShortService.java` | Contiene la lógica para consultar y registrar shorts, incluyendo la validación de existencia del usuario asociado. |
| `UsuarioService.java` | Contiene la lógica para consultar y registrar usuarios en PostgreSQL. |
| `ShortController.java` | Implementa los endpoints GET y POST para consultar y registrar shorts. |
| `UsuarioController.java` | Implementa los endpoints GET y POST para consultar y registrar usuarios. |

**Proceso de creación de las tablas**

Las tablas `usuario` y `short` se generaron automáticamente en PostgreSQL mediante Hibernate, utilizando las entidades Java y la configuración `spring.jpa.hibernate.ddl-auto=update`.

La tabla `usuario` contiene la información del perfil de El loquito, mientras que `short` almacena sus videos cortos. Ambas tablas están relacionadas mediante la llave foránea `id_usuario`, que permite asociar cada short con su usuario correspondiente.

Después de iniciar Spring Boot, se verificó en pgAdmin que las tablas y su relación se hubieran creado correctamente.

### 1. Scripts de Carga y Limpieza (Data Provisioning)

Se prepararon los scripts `01-carga.sql` y `02-limpieza.sql`, ubicados en la carpeta `scripts/` del proyecto, para administrar los datos iniciales del laboratorio en PostgreSQL.

**Script de carga (`01-carga.sql`)**

Este script permite insertar los registros iniciales de CatTube en la base de datos. Registra un usuario correspondiente a El loquito, identificado con el ID 1, y tres shorts asociados mediante la llave foránea `id_usuario`.

Además, utiliza la función `setval()` de PostgreSQL para sincronizar los identificadores autogenerados con los registros insertados, evitando conflictos al registrar nuevos datos posteriormente.

**Script de limpieza (`02-limpieza.sql`)**

Este script permite eliminar todos los registros almacenados en las tablas `short` y `usuario` mediante la instrucción `TRUNCATE TABLE`, utilizando `RESTART IDENTITY` para reiniciar los identificadores.

La operación se realiza dentro de una transacción mediante las instrucciones `BEGIN` y `COMMIT`.

La limpieza elimina los registros, pero conserva la estructura de las tablas, sus columnas y las relaciones definidas en PostgreSQL.

**Ejecución y verificación de los scripts**

Durante las pruebas de la API REST se registraron datos adicionales mediante Postman. Estos registros se conservaron temporalmente como evidencia del funcionamiento de las operaciones de persistencia.

Una vez obtenidas las capturas correspondientes, se ejecutó el script `02-limpieza.sql` mediante Query Tool de pgAdmin 4.

La ejecución finalizó correctamente y posteriormente se realizaron consultas `SELECT` para comprobar que las tablas `short` y `usuario` se encontraban vacías.

Después, se ejecutó nuevamente el script `01-carga.sql` para recuperar los datos iniciales del laboratorio.

Finalmente, se verificó mediante consultas SQL que la base de datos contenía nuevamente un usuario, correspondiente a El loquito, y tres shorts asociados.

| Etapa | Usuarios | Shorts | Resultado |
|---|---:|---:|---|
| Después de las pruebas con Postman | 2 | 6 | Registros iniciales y de prueba almacenados |
| Después de ejecutar `02-limpieza.sql` | 0 | 0 | Tablas vacías e identificadores reiniciados |
| Después de ejecutar `01-carga.sql` | 1 | 3 | Datos iniciales recuperados correctamente |

**Evidencias de carga y limpieza**

Se documentaron las siguientes actividades mediante capturas de pantalla:

- Contenido del script de carga `01-carga.sql`.
- Consulta de los registros iniciales almacenados en PostgreSQL.
- Ejecución exitosa del script de limpieza `02-limpieza.sql`.
- Verificación de las tablas vacías después de la limpieza.
- Consulta final para comprobar la recuperación del usuario y sus tres shorts.

Estas evidencias permiten comprobar el funcionamiento de los scripts SQL y la posibilidad de restablecer los datos iniciales para repetir las pruebas del laboratorio.

### 2. Capturas o Logs de Ejecución de Queries

Se realizaron pruebas de consulta y registro de información mediante los servicios REST de Spring Boot, utilizando el navegador web y Postman.

**Pruebas GET**

Se consultaron los siguientes endpoints:

- `GET /api/shorts`: permitió obtener los shorts almacenados en PostgreSQL.
- `GET /api/usuarios`: permitió obtener los usuarios registrados en la base de datos.

Ambas peticiones devolvieron respuestas HTTP `200 OK`.

**Pruebas POST**

Mediante Postman se enviaron peticiones para registrar nuevos datos:

- `POST /api/shorts`: permitió registrar un nuevo short asociado a un usuario existente.
- `POST /api/usuarios`: permitió registrar un nuevo usuario con sus valores de seguidores, dinero, influencia y cohesión.

En ambos casos, el servidor respondió con el código HTTP `201 Created`, confirmando la creación de los registros.

**Verificación de persistencia**

Después de ejecutar las peticiones POST, se consultaron nuevamente los endpoints desde el navegador y se revisaron las tablas en pgAdmin 4.

Se comprobó que los registros enviados desde Postman permanecían almacenados en PostgreSQL.

Durante esta etapa se verificó la existencia de seis shorts y dos usuarios, incluyendo los registros creados para las pruebas.

Posteriormente, estos registros se eliminaron mediante el script de limpieza y se recuperaron únicamente los datos iniciales mediante el script de carga.

**Evidencias**

Las capturas de Postman, las respuestas JSON del navegador y las consultas realizadas en PostgreSQL se almacenaron en la carpeta `docs/evidencias/`.

### 3. Logs del Servidor Backend (Application Logs)

Se configuraron registros de ejecución para visualizar las consultas SQL, los parámetros enviados y las métricas de las peticiones HTTP.

Durante las pruebas iniciales de shorts se obtuvieron los siguientes valores:

| Métrica | GET | POST |
|---|---|---|
| Código HTTP | 200 | 201 |
| Tiempo de procesamiento | 775 ms | 89 ms |
| Memoria antes | 61,345 KB | 45,883 KB |
| Memoria después | 43,984 KB | 52,735 KB |

Los valores de memoria corresponden al uso aproximado del heap de Java durante las peticiones iniciales.

Los logs de Hibernate permitieron identificar las consultas `SELECT` y las operaciones `INSERT` realizadas sobre PostgreSQL.

Adicionalmente, se verificó el inicio correcto del servidor Spring Boot en el puerto `8080` y la conexión a la base de datos `loquito`.

### 4. Pruebas de la API REST con Postman

Se utilizó Postman para comprobar el funcionamiento de los métodos HTTP GET y POST correspondientes a los recursos de shorts y usuarios.

| Método | Endpoint | Resultado esperado | Resultado obtenido |
|---|---|---|---|
| GET | `/api/shorts` | Consultar los shorts registrados | 200 OK |
| POST | `/api/shorts` | Registrar un nuevo short | 201 Created |
| GET | `/api/usuarios` | Consultar los usuarios registrados | 200 OK |
| POST | `/api/usuarios` | Registrar un nuevo usuario | 201 Created |

Para las peticiones POST se configuró el cuerpo de la solicitud en formato JSON, utilizando la opción `Body → raw → JSON`.

**Ejemplo de registro de un short:**

```json
{
  "titulo": "Mi primer short desde Postman",
  "vistas": 1500,
  "likes": 250,
  "idUsuario": 1
}
```

**Ejemplo de registro de un usuario:**

```json
{
  "seguidores": 2500,
  "dinero": 1000,
  "influencia": 65,
  "cohesion": 80
}
```

Las respuestas del servidor confirmaron la creación de los registros. Posteriormente, se comprobó su almacenamiento mediante consultas en pgAdmin y las respuestas JSON de los endpoints GET.

Estas pruebas permitieron verificar la comunicación entre Postman, Spring Boot y PostgreSQL.

Los resultados de estas pruebas se conservaron mediante capturas de pantalla antes de ejecutar la limpieza de la base de datos.

## Resultados

Las pruebas realizadas confirmaron que el backend de CatTube puede consultar y registrar shorts y usuarios en PostgreSQL mediante los métodos HTTP GET y POST.

Se verificó el funcionamiento de los controladores, servicios y repositorios, así como la persistencia de los registros y la relación entre las tablas `usuario` y `short`.

Las pruebas con Postman fueron satisfactorias y los datos registrados se comprobaron tanto en PostgreSQL como en las respuestas JSON del navegador.

Asimismo, se comprobó el funcionamiento de los scripts SQL de carga y limpieza. El script de limpieza permitió vaciar ambas tablas y reiniciar sus identificadores, mientras que el script de carga permitió recuperar correctamente los datos iniciales.

Al finalizar el laboratorio, la base de datos quedó con un usuario correspondiente a El loquito y tres shorts asociados, manteniendo la información necesaria para continuar con el desarrollo del proyecto.

Las pruebas de integración con la interfaz y de funcionamiento offline de la PWA se realizarán posteriormente.