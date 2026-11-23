# Backend — They Are Not Pigeons

Backend desarrollado con **Java y Spring Boot**, encargado de gestionar las operaciones y la información de la plataforma CatTube, perteneciente al proyecto *They Are Not Pigeons*.

Funciona como intermediario entre la aplicación web (PWA) y la base de datos PostgreSQL alojada en Supabase. Recibe solicitudes, procesa la información y devuelve las respuestas correspondientes.

## Tecnologías utilizadas

Para desarrollar el backend se utilizaron las siguientes tecnologías y herramientas, seleccionadas de acuerdo con las necesidades del proyecto y los requisitos establecidos para su desarrollo. Algunas también fueron solicitadas por el profesor como parte de las indicaciones de la actividad.

| Tecnología | Uso dentro del proyecto |
|---|---|
| **Java 21** | Lenguaje utilizado para desarrollar la lógica y las funcionalidades del backend. Se eligió por su compatibilidad con Spring Boot y sus herramientas para crear aplicaciones robustas. |
| **Spring Boot 4.1.1** | Framework utilizado para crear y configurar el servidor backend, facilitando la implementación de controladores y servicios REST. |
| **Spring Data JPA** | Permite gestionar la comunicación entre las entidades Java y la base de datos, facilitando las consultas y operaciones sobre los registros. |
| **Spring Web MVC** | Se utiliza para crear los endpoints REST que reciben y responden las solicitudes HTTP enviadas desde la PWA. |
| **Spring Validation** | Permite validar los datos recibidos por el backend para evitar procesar información que no cumpla con las condiciones establecidas. |
| **PostgreSQL** | Sistema gestor de bases de datos relacional utilizado para almacenar y organizar la información de la plataforma. |
| **Supabase** | Servicio en la nube utilizado para alojar la base de datos PostgreSQL y permitir que el backend acceda a la información de manera remota. |
| **Maven** | Herramienta de gestión y construcción del proyecto. Se utiliza para administrar dependencias, ejecutar pruebas y compilar la aplicación. |
| **JUnit 5** | Framework utilizado para ejecutar pruebas automatizadas y comprobar el comportamiento de los componentes del backend. |
| **Mockito** | Biblioteca utilizada para simular dependencias durante las pruebas y evaluar los servicios de manera aislada. |
| **AssertJ** | Biblioteca que facilita la escritura de verificaciones y comprobaciones dentro de las pruebas automatizadas. |

## Estructura del proyecto

```text
base-loquito/
├── .github/
├── .mvn/
├── scripts/
│   ├── migraciones/
│   └── respaldo_proyecto.sql
├── src/
│   ├── main/
│   │   ├── java/com/elloquito/baseloquito/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/elloquito/baseloquito/
│           ├── service/
│           └── BaseLoquitoApplicationTests.java
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Descripción de las carpetas y archivos

### 1. Configuración (`config/`)

Contiene configuraciones generales necesarias para la comunicación y el funcionamiento del backend.

| Archivo | Descripción |
|---|---|
| `CorsConfig.java` | Configura los permisos CORS para permitir la comunicación entre la PWA y el backend. |
| `RequestLoggingFilter.java` | Registra información de las solicitudes HTTP que recibe el servidor, facilitando el seguimiento de las peticiones. |

### 2. Controladores (`controller/`)

Contiene los controladores REST que reciben las solicitudes HTTP enviadas desde la PWA. Cada controlador atiende un grupo de operaciones y utiliza los servicios correspondientes para procesar las solicitudes.

| Archivo | Descripción |
|---|---|
| `ComentarioController.java` | Recibe las solicitudes relacionadas con los comentarios de los videos. |
| `HealthController.java` | Proporciona un endpoint para comprobar que el backend se encuentra disponible. |
| `JugadorVideoController.java` | Gestiona las solicitudes relacionadas con la asociación entre jugadores y videos. |
| `ProgresoVideoController.java` | Expone las operaciones para consultar y gestionar el progreso de los videos. |
| `UsuarioController.java` | Atiende las solicitudes relacionadas con los usuarios. |
| `VideoController.java` | Gestiona las solicitudes para consultar y administrar los videos. |

### 3. Modelos (`model/`)

Contiene las clases que representan las entidades de la base de datos. Cada una define la información que se almacena y las relaciones entre los registros.

| Archivo | Descripción |
|---|---|
| `Comentario.java` | Representa los comentarios asociados a los videos. |
| `HistorialEstadistica.java` | Representa los registros históricos de estadísticas. |
| `JugadorVideo.java` | Representa la relación entre un jugador y un video. |
| `JugadorVideoId.java` | Define la clave primaria compuesta de la relación jugador-video. |
| `ProgresoVideo.java` | Representa el progreso de un jugador dentro de un video. |
| `ProgresoVideoId.java` | Define la clave primaria compuesta del registro de progreso. |
| `Usuario.java` | Representa a los usuarios registrados en la plataforma. |
| `Video.java` | Representa los videos almacenados en la base de datos. |

### 4. Repositorios (`repository/`)

Contiene las interfaces que permiten acceder a los datos almacenados en PostgreSQL mediante Spring Data JPA. Se encargan de realizar consultas y operaciones sobre las entidades sin necesidad de escribir manualmente todas las instrucciones SQL.

| Archivo | Descripción |
|---|---|
| `ComentarioRepository.java` | Permite consultar y administrar los comentarios. |
| `HistorialEstadisticaRepository.java` | Permite acceder al historial de estadísticas. |
| `JugadorVideoRepository.java` | Gestiona las consultas de las relaciones entre jugadores y videos. |
| `ProgresoVideoRepository.java` | Permite consultar y actualizar el progreso de los videos. |
| `UsuarioRepository.java` | Permite consultar y administrar los registros de usuarios. |
| `VideoRepository.java` | Permite consultar y administrar los registros de videos. |

### 5. Servicios (`service/`)

Contiene la lógica de negocio del backend. Los controladores utilizan estos servicios para ejecutar operaciones, aplicar reglas y comunicarse con los repositorios.

| Archivo | Descripción |
|---|---|
| `ComentarioService.java` | Implementa las operaciones relacionadas con los comentarios. |
| `JugadorVideoService.java` | Gestiona la lógica de las relaciones entre jugadores y videos. |
| `LikesAutomaticosScheduler.java` | Programa la ejecución periódica de las operaciones de likes automáticos. |
| `LikesAutomaticosService.java` | Contiene la lógica encargada de gestionar los likes automáticos. |
| `ProgresoVideoService.java` | Gestiona las operaciones relacionadas con el progreso de los videos. |
| `UsuarioService.java` | Implementa las operaciones y reglas de negocio de los usuarios. |
| `VideoService.java` | Implementa las operaciones y reglas de negocio de los videos. |

### 6. Recursos (`resources/`)

Contiene los archivos necesarios para configurar la aplicación.

**`application.properties`**

Es el archivo principal de configuración del backend. Define el nombre de la aplicación, el puerto `8080` y los parámetros necesarios para establecer la conexión con PostgreSQL alojado en Supabase.

También contiene la configuración de HikariCP, que administra el conjunto de conexiones a la base de datos; las propiedades de Hibernate y JPA para validar la estructura de las tablas; y los niveles de registro que permiten visualizar las consultas SQL y facilitar la revisión de las operaciones.

La contraseña de Supabase se obtiene mediante la variable de entorno `DB_PASSWORD`, evitando almacenarla directamente en el archivo de configuración.

### 7. Pruebas automatizadas (`src/test/`)

Contiene las pruebas utilizadas para comprobar el funcionamiento de los servicios y verificar que la aplicación pueda iniciar correctamente.

La ruta principal es:

`src/test/java/com/elloquito/baseloquito/`

| Archivo o carpeta | Descripción |
|---|---|
| `BaseLoquitoApplicationTests.java` | Comprueba que el contexto de Spring Boot pueda iniciar correctamente, incluyendo la configuración y conexión necesarias. |
| `service/ComentarioServiceTest.java` | Verifica las operaciones del servicio de comentarios. |
| `service/LikesAutomaticosServiceTest.java` | Comprueba el funcionamiento de los likes automáticos. |
| `service/ProgresoVideoServiceTest.java` | Verifica las operaciones relacionadas con el progreso de los videos. |
| `service/UsuarioServiceTest.java` | Comprueba las operaciones del servicio de usuarios. |
| `service/VideoServiceTest.java` | Verifica las operaciones del servicio de videos. |

### 8. Scripts y respaldo de la base de datos (`scripts/`)

Contiene archivos relacionados con la estructura y conservación de la base de datos.

- `migraciones/`: almacena los scripts SQL creados para incorporar cambios y nuevas estructuras a la base de datos conforme evolucionó el proyecto.
- `respaldo_proyecto.sql`: contiene el respaldo del trabajo realizado anteriormente con PostgreSQL, incluyendo la importación de la estructura de la base de datos, la inserción de registros y las consultas desarrolladas.

### 9. Archivos principales del proyecto

| Archivo | Descripción |
|---|---|
| `pom.xml` | Define las dependencias, versiones y configuración necesarias para compilar y ejecutar el proyecto mediante Maven. |
| `mvnw` | Permite ejecutar Maven en sistemas Unix mediante el wrapper. |
| `mvnw.cmd` | Permite ejecutar Maven en Windows mediante el wrapper. |

## Conexión entre la PWA, el backend y Supabase

El proyecto utiliza una arquitectura en la que la PWA y el backend son aplicaciones independientes, mientras que Supabase aloja la base de datos PostgreSQL compartida.

Cada componente cumple una función específica:

- **CatTube (PWA):** interfaz web desarrollada con React. Desde ella se realizan las solicitudes para consultar y utilizar la información.
- **Backend (Spring Boot):** recibe las solicitudes de la PWA, procesa las operaciones mediante sus controladores y servicios, y accede a la base de datos a través de los repositorios.
- **Supabase (PostgreSQL):** almacena de forma persistente la información utilizada por la aplicación, como usuarios, videos, comentarios y progreso.

### Flujo de comunicación

```text
┌──────────────────────────────┐
│         CatTube (PWA)        │
│        React / Vite          │
│       localhost:5173         │
└──────────────┬───────────────┘
               │
               │ Solicitudes HTTP
               ▼
┌──────────────────────────────┐
│       Backend Spring Boot    │
│        localhost:8080        │
│                              │
│  Controller → Service        │
│             → Repository     │
└──────────────┬───────────────┘
               │
               │ Conexión JDBC
               ▼
┌──────────────────────────────┐
│       Supabase               │
│       PostgreSQL             │
│                              │
│       Almacenamiento         │
│        de datos              │
└──────────────────────────────┘
```

La comunicación funciona de la siguiente manera:

1. El usuario realiza una acción desde CatTube.
2. La PWA envía una solicitud HTTP al backend mediante su cliente API.
3. Spring Boot recibe la solicitud en el controlador correspondiente.
4. El controlador utiliza el servicio para ejecutar la operación solicitada.
5. El servicio se comunica con el repositorio, que consulta o modifica los registros en PostgreSQL.
6. Supabase almacena o devuelve la información solicitada.
7. El backend envía una respuesta a la PWA, que utiliza los datos para actualizar la interfaz.

**Importante:** Supabase está alojado en la nube, pero el backend Spring Boot se ejecuta localmente durante el desarrollo. Por ello, para que la PWA pueda consultar y modificar información mediante la API, es necesario que el backend esté iniciado y que ambos servicios puedan comunicarse.

## Configuración y ejecución

### 1. Configurar la conexión con Supabase

Antes de iniciar el backend, se debe proporcionar la contraseña de la base de datos mediante la variable de entorno `DB_PASSWORD`.

En Git Bash:

```bash
read -s -p "Contraseña de Supabase: " DB_PASSWORD
echo
export DB_PASSWORD
```

La contraseña no se muestra en la terminal y la variable permanece disponible durante la sesión actual de Git Bash.

### 2. Iniciar el backend

Desde la carpeta del proyecto:

```bash
./mvnw spring-boot:run
```

El servidor estará disponible en:

`http://localhost:8080`

### 3. Ejecutar las pruebas

Para ejecutar las pruebas automatizadas:

```bash
./mvnw test
```

**Última ejecución verificada: 22 de noviembre de 2026.**

- Pruebas ejecutadas: 18.
- Pruebas aprobadas: 18.
- Fallos: 0.
- Errores: 0.
- Resultado: `BUILD SUCCESS`.

## Repositorio

[base-loquito — GitHub](https://github.com/jaquesalazar/base-loquito)

## Integrantes del equipo

**Equipo Arcoiris — Grupo IDGS102N**

| Integrante | Matrícula |
|---|---|
| Erick Noel Aguilar Chavarria | 1123150103 |
| Valeria Castillo López | 1122150036 |
| Alexandro De la Vega Rodríguez | 1123150120 |
| Ana Sofia Pérez Ortega | 1123150060 |
| Jaqueline Salazar Rivera | 1123150100 |
| Moira Villalobos Hinojos | 1123150011 |

**Institución:** Universidad Tecnológica de Chihuahua