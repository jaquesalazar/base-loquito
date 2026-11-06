# Laboratorio de pruebas

## Evidencias de Persistencia y Base de Datos (Backend / DB)

### Configuración y creación de la base de datos

Para el laboratorio de pruebas de CatTube se utilizó PostgreSQL 17 como sistema gestor de base de datos, pgAdmin 4 para su administración, Postman para las pruebas de la API REST y Visual Studio Code para desarrollar el backend con Java y Spring Boot.

La base de datos `loquito` se creó mediante pgAdmin 4. Posteriormente, se definieron manualmente las tablas `usuarios` y `videos` mediante instrucciones SQL. También se configuró la conexión desde Spring Boot y se adaptaron las entidades Java para representar la estructura de PostgreSQL.

**Archivos utilizados en Visual Studio Code:**

| Archivo | Función |
|---|---|
| `application.properties` | Configura la conexión a PostgreSQL, la base de datos `loquito`, el puerto `5433` y las propiedades de Hibernate. |
| `Usuario.java` | Define la entidad correspondiente a la tabla `usuarios`, con los campos `id_usuario`, `username`, `seguidores`, `dinero` y `cohesion`. |
| `Video.java` | Define la entidad correspondiente a la tabla `videos`, con sus campos de información, contadores, fecha de creación y relación con `usuarios`. |
| `VideoRepository.java` | Permite realizar operaciones de consulta y persistencia sobre la tabla `videos` mediante Spring Data JPA. |
| `UsuarioRepository.java` | Permite realizar operaciones de consulta y persistencia sobre la tabla `usuarios` mediante Spring Data JPA. |
| `VideoService.java` | Contiene la lógica para consultar y registrar videos, incluyendo la validación de existencia del usuario asociado. |
| `UsuarioService.java` | Contiene la lógica para consultar y registrar usuarios en PostgreSQL. |
| `VideoController.java` | Implementa los endpoints GET y POST para consultar y registrar videos. |
| `UsuarioController.java` | Implementa los endpoints GET y POST para consultar y registrar usuarios. |

**Estructura de las tablas**

La base de datos contiene dos tablas relacionadas mediante la llave foránea `id_usuario`.

La tabla `usuarios` almacena la información del perfil de CatTube y contiene los siguientes campos:

- `id_usuario`: identificador principal autogenerado.
- `username`: nombre del usuario, obligatorio.
- `seguidores`: cantidad de seguidores.
- `dinero`: cantidad de dinero.
- `cohesion`: valor de cohesión.

La tabla `videos` almacena la información de los videos publicados y contiene:

- `id_video`: identificador principal autogenerado.
- `likes`: cantidad de likes.
- `titulo`: título del video.
- `vistas`: cantidad de visualizaciones.
- `id_usuario`: identificador del usuario propietario.
- `tag`: categoría o etiqueta del video.
- `caption`: descripción del video, obligatoria.
- `hashtags`: etiquetas asociadas al video.
- `video_url`: dirección del video, obligatoria.
- `comments`: cantidad de comentarios.
- `shares`: cantidad de veces compartido.
- `created_at`: fecha de creación generada automáticamente por PostgreSQL.

La relación se establece mediante una llave foránea desde `videos.id_usuario` hacia `usuarios.id_usuario`. Se configuró la opción `ON DELETE CASCADE` para que, al eliminar un usuario, también se eliminen los videos asociados.

**Configuración de Hibernate**

Las tablas se crearon manualmente en PostgreSQL mediante pgAdmin 4. En la configuración de Spring Boot se estableció la propiedad `spring.jpa.hibernate.ddl-auto=validate`, con la finalidad de comprobar que las entidades Java coincidan con la estructura existente en la base de datos, sin permitir que Hibernate modifique automáticamente las tablas.

### 1. Scripts de Carga y Limpieza (Data Provisioning)

Se prepararon los scripts `01-carga.sql` y `02-limpieza.sql`, ubicados en la carpeta `scripts/` del proyecto, para administrar los datos iniciales del laboratorio en PostgreSQL.

**Script de carga (`01-carga.sql`)**

Este script permite insertar los registros iniciales de CatTube en la base de datos. Registra un usuario correspondiente a El loquito y tres videos asociados mediante la llave foránea `id_usuario`.

El identificador del usuario se obtiene mediante `RETURNING id_usuario`, evitando tener que establecer manualmente el valor de la llave primaria. Los identificadores de usuarios y videos son generados automáticamente por PostgreSQL.

Además, los campos `comments` y `shares` utilizan los valores predeterminados definidos en la tabla, mientras que `created_at` recibe automáticamente la fecha de creación.

**Script de limpieza (`02-limpieza.sql`)**

Este script permite eliminar los registros almacenados en las tablas `videos` y `usuarios` mediante la instrucción `TRUNCATE TABLE`, utilizando `RESTART IDENTITY` para reiniciar los identificadores y `CASCADE` para considerar la relación entre ambas tablas.

La operación se realiza dentro de una transacción mediante las instrucciones `BEGIN` y `COMMIT`.

La limpieza elimina los registros, pero conserva la estructura de las tablas, sus columnas y las relaciones definidas en PostgreSQL.

**Ejecución y verificación de los scripts**

Los scripts se ejecutan mediante Query Tool de pgAdmin 4, utilizando la conexión a la base de datos `loquito`.

Después de ejecutar `02-limpieza.sql`, se pueden realizar consultas `SELECT` para comprobar que ambas tablas se encuentran vacías.

Posteriormente, se ejecuta `01-carga.sql` para recuperar los datos iniciales del laboratorio.

La cantidad de registros esperada después de la carga inicial es la siguiente:

| Tabla | Registros esperados |
|---|---:|
| `usuarios` | 1 |
| `videos` | 3 |

Estas cantidades corresponden a los datos definidos en el script de carga.

**Evidencias de carga y limpieza**

Se documentarán las siguientes actividades mediante capturas de pantalla:

- Contenido del script de carga `01-carga.sql`.
- Consulta de los registros iniciales almacenados en PostgreSQL.
- Ejecución del script de limpieza `02-limpieza.sql`.
- Verificación de las tablas vacías después de la limpieza.
- Consulta final para comprobar la recuperación del usuario y sus tres videos.

Estas evidencias permiten comprobar el funcionamiento de los scripts SQL y la posibilidad de restablecer los datos iniciales para repetir las pruebas del laboratorio.

### 2. Capturas o Logs de Ejecución de Queries

Se realizarán pruebas de consulta y registro de información mediante los servicios REST de Spring Boot, utilizando Postman y el navegador web.

**Pruebas GET**

Se consultarán los siguientes endpoints:

- `GET /api/videos`: permitirá obtener los videos almacenados en PostgreSQL.
- `GET /api/usuarios`: permitirá obtener los usuarios registrados en la base de datos.

Se espera que ambas peticiones devuelvan respuestas HTTP `200 OK`.

**Pruebas POST**

Mediante Postman se enviarán peticiones para registrar nuevos datos:

- `POST /api/videos`: permitirá registrar un nuevo video asociado a un usuario existente.
- `POST /api/usuarios`: permitirá registrar un nuevo usuario con su nombre de usuario y sus valores de seguidores, dinero y cohesión.

Se espera que ambas peticiones devuelvan el código HTTP `201 Created`, confirmando la creación de los registros.

**Verificación de persistencia**

Después de ejecutar las peticiones POST, se consultarán nuevamente los endpoints y se revisarán las tablas en pgAdmin 4.

Se comprobará que los registros enviados desde Postman permanezcan almacenados en PostgreSQL.

**Evidencias**

Las capturas de Postman, las respuestas JSON del navegador y las consultas realizadas en PostgreSQL se almacenarán en la carpeta `docs/evidencias/`.

### 3. Logs del Servidor Backend (Application Logs)

Se configuraron registros de ejecución para visualizar las consultas SQL, los parámetros enviados y las métricas de las peticiones HTTP.

La configuración de logs de Hibernate permite identificar las consultas `SELECT` y las operaciones `INSERT` realizadas sobre PostgreSQL.

Adicionalmente, se verificará el inicio correcto del servidor Spring Boot en el puerto `8080` y la conexión a la base de datos `loquito`.

Los resultados de tiempo de procesamiento y uso de memoria se documentarán mediante capturas o registros obtenidos durante las nuevas pruebas del backend.

Los valores registrados durante pruebas anteriores corresponden a la implementación previa de las tablas `usuario` y `short`, por lo que no se utilizarán como resultados de rendimiento de la estructura actualizada.

### 4. Pruebas de la API REST con Postman

Se utilizará Postman para comprobar el funcionamiento de los métodos HTTP GET y POST correspondientes a los recursos de videos y usuarios.

| Método | Endpoint | Resultado esperado |
|---|---|---|
| GET | `/api/videos` | 200 OK |
| POST | `/api/videos` | 201 Created |
| GET | `/api/usuarios` | 200 OK |
| POST | `/api/usuarios` | 201 Created |

Para las peticiones POST se configurará el cuerpo de la solicitud en formato JSON, utilizando la opción `Body → raw → JSON`.

**Ejemplo de registro de un video:**

```json
{
  "titulo": "Mi primer video desde Postman",
  "vistas": 1500,
  "likes": 250,
  "idUsuario": 1,
  "tag": "Entretenimiento",
  "caption": "Este es un video de prueba de CatTube",
  "hashtags": "#cattube #prueba",
  "videoUrl": "https://example.com/videos/prueba.mp4",
  "comments": 10,
  "shares": 5
}
```

**Ejemplo de registro de un usuario:**

```json
{
  "username": "usuario_prueba",
  "seguidores": 2500,
  "dinero": 1000,
  "cohesion": 80
}
```

Las respuestas del servidor permitirán comprobar la creación de los registros. Posteriormente, se verificará su almacenamiento mediante consultas en pgAdmin y las respuestas JSON de los endpoints GET.

Estas pruebas permitirán verificar la comunicación entre Postman, Spring Boot y PostgreSQL.

Los resultados se conservarán mediante capturas de pantalla y registros de las respuestas HTTP.

## Resultados

La estructura actualizada del backend de CatTube permite trabajar con las tablas `usuarios` y `videos` de PostgreSQL mediante entidades Java, repositorios, servicios y controladores de Spring Boot.

Se adaptaron los modelos y servicios para representar los campos definidos en la base de datos, así como la relación entre los videos y sus usuarios correspondientes.

También se actualizaron los scripts SQL de carga y limpieza para utilizar los nombres de las nuevas tablas y sus respectivos campos.

La configuración de Hibernate se estableció en modo `validate`, de manera que la aplicación compruebe la correspondencia entre las entidades Java y la estructura existente en PostgreSQL.

Las pruebas de integración con Postman, la verificación de persistencia y los resultados de rendimiento deberán repetirse con esta versión del backend para documentar su funcionamiento.

Las pruebas de integración con la interfaz y de funcionamiento offline de la PWA se realizarán posteriormente.