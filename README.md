# LiterAlura
LiterAlura es una aplicación de consola Java que te permite gestionar una biblioteca de libros. Utiliza Gutendex para consultar información sobre libros y permite realizar diversas operaciones como buscar libros por título, listar libros registrados, listar autores registrados, buscar autores vivos en un determinado año, listar libros por idioma y mostrar el TOP 10 de libros con más descargas.

## Requisitos
- Java 11 o superior
- Maven
- PostgreSQL (u otro sistema de gestión de base de datos compatible con Spring Data)
## Configuración
- Clona el repositorio:

git clone https://github.com/tu_usuario/literalura.git

- Importa el proyecto en tu IDE (por ejemplo, Eclipse, IntelliJ IDEA).

- Configura la conexión a la base de datos en application.properties. Asegúrate de tener PostgreSQL instalado y ejecutándose.

## Properties
- spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
- spring.datasource.username=usuario
- spring.datasource.password=contraseña
## Ejecución
Desde tu IDE, ejecuta la clase MainApplication ubicada en src/main/java/com/aluraCurso/literAlura/main/MainApplication.java.

Sigue las instrucciones en la consola para interactuar con la aplicación.

## Funcionalidades
La aplicación proporciona las siguientes funcionalidades básicas:

- Buscar libro por título: Permite buscar un libro específico por su título.
- Listar libros registrados: Muestra todos los libros registrados en la base de datos.
- Listar autores registrados: Muestra todos los autores registrados en la base de datos.
- Listar autores vivos en un determinado año: Permite buscar autores que estaban vivos en un año específico.
- Listar libros por idioma: Muestra la cantidad de libros disponibles por cada idioma registrado en la base de datos.
- Listar el TOP 10 Libros con más descargas: Muestra los 10 libros más descargados.
