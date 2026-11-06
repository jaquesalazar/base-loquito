Pruebas unitarias de servicios -- CatTube
1. Descripción de la actividad
En esta actividad se implementaron pruebas unitarias para verificar el
funcionamiento de los servicios del backend de CatTube, desarrollado con
Java y Spring Boot. Las pruebas se enfocaron en las operaciones de
consulta y creación de usuarios y videos.

Este documento complementa la documentación del laboratorio de pruebas
de persistencia y base de datos. Mientras aquella describe la estructura
de PostgreSQL, los scripts SQL y las pruebas de la API REST, esta
actividad se concentra en comprobar de manera aislada la lógica de los
servicios.
2. Herramientas utilizadas
- JUnit 5: ejecución y organización de los casos de prueba.
- Mockito: simulación de repositorios y dependencias de los
  servicios.
- AssertJ: validación de los resultados obtenidos.
- Maven: ejecución de las pruebas mediante el wrapper del
  proyecto.
- Spring Boot: framework del backend donde se encuentran los
  servicios evaluados.
3. Archivos creados
Las clases de prueba se ubicaron en:

src/test/java/com/elloquito/baseloquito/service/
  Archivo                             Propósito
  UsuarioServiceTest.java           Prueba la consulta y creación de
usuarios.
VideoServiceTest.java             Prueba la consulta y creación de
videos.
4. Pruebas implementadas
UsuarioServiceTest
Se implementaron dos casos:
- obtenerUsuariosDebeDevolverLaListaDeUsuarios: comprueba que
  obtenerUsuarios() devuelva la lista proporcionada por
  usuarioRepository.findAll().
- crearUsuarioDebeGuardarYDevolverElUsuario: verifica que
  crearUsuario() construya el usuario con los datos recibidos, lo
  guarde mediante el repositorio y devuelva el resultado.
VideoServiceTest
Se implementaron dos casos:
- obtenerVideosDebeDevolverLaListaDeVideos: comprueba que
  obtenerVideos() devuelva la lista proporcionada por
  videoRepository.findAll().
- crearVideoDebeGuardarYDevolverElVideo: verifica que
  crearVideo() encuentre al usuario relacionado, construya el video
  con sus datos, lo guarde y actualice la entidad para recuperar los
  valores generados por la persistencia.
En las dos clases se utilizaron @Mock para crear dependencias
simuladas y @InjectMocks para inyectarlas en el servicio evaluado. Las
comprobaciones se realizaron con AssertJ y se verificaron las llamadas
esperadas mediante Mockito.
5. Ejecución
Los comandos utilizados desde Git Bash fueron:
./mvnw -Dtest=UsuarioServiceTest test
Para ejecutar la clase de pruebas de videos:
./mvnw -Dtest=VideoServiceTest test
Para ejecutar todas las pruebas del proyecto:
./mvnw test
6. Resultados
Las pruebas individuales de usuarios y videos finalizaron correctamente.
La ejecución general reportó:

  Resultado              Cantidad
  Pruebas ejecutadas            5
Fallos                        0
Errores                       0
Omitidas                      0

Maven finalizó con el mensaje BUILD SUCCESS.

El total incluye cuatro pruebas unitarias de los servicios y una prueba
de contexto de Spring Boot (BaseLoquitoApplicationTests). Las pruebas
unitarias utilizan dependencias simuladas con Mockito; por ello, no
realizan operaciones reales de escritura en PostgreSQL. La prueba de
contexto comprueba el arranque del contexto de la aplicación.
7. Evidencias
Las capturas de pantalla de la actividad muestran el código de las
clases de prueba y la ejecución de Maven. Se recomienda revisar la documentación en el archivo pdf.
8. Conclusión
La actividad permitió comprobar de forma aislada los métodos de consulta
y creación de usuarios y videos. El uso de JUnit 5, Mockito y AssertJ
facilitó la validación de los servicios sin depender de operaciones
reales en la base de datos durante las pruebas unitarias. La ejecución
general de Maven terminó sin fallos ni errores, por lo que los casos
implementados cumplieron con las verificaciones establecidas.