package com.aluraCurso.literAlura.main;

import org.springframework.dao.DataIntegrityViolationException;

import com.aluraCurso.literAlura.api.ConsumoApi;
import com.aluraCurso.literAlura.model.Autor;
import com.aluraCurso.literAlura.model.Libro;
import com.aluraCurso.literAlura.record.DatosApi;
import com.aluraCurso.literAlura.repository.AutorRepository;
import com.aluraCurso.literAlura.repository.LibroIdiomaCount;
import com.aluraCurso.literAlura.repository.LibroRepository;
import com.aluraCurso.literAlura.service.ConvierteDatos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {

	private static final Scanner teclado = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.libroRepository = repository;
        this.autorRepository = autorRepository;
    }

    public void buscarYGuardarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String libroSolicitado = teclado.nextLine();

        String json = consumoApi.obtenerDatos(URL_BASE + "?search=" + libroSolicitado.replace(" ", "+"));
        DatosApi datosApi = convierteDatos.obtenerDatos(json, DatosApi.class);

        datosApi.libros().stream()
            .map(Libro::new)
            .findFirst()
            .ifPresentOrElse(this::guardarLibro, () -> System.out.println("El libro no se encuentra."));
    }

    private void guardarLibro(Libro libro) {
        Autor autor = autorRepository.findByNombreContainsIgnoreCase(libro.getAutor().getNombre());

        if (autor == null) {
            autor = autorRepository.save(libro.getAutor());
        }

        libro.setAutor(autor);

        try {
            libroRepository.save(libro);
            System.out.println(libro);
        } catch (DataIntegrityViolationException ex) {
            System.out.println("El libro con este título ya existe en la base de datos.");
        }
    }

    public void listarLibros() {
        libroRepository.findAll().forEach(System.out::println);
    }

    public void listarAutores() {
        autorRepository.findAll().forEach(System.out::println);
    }

    public void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar: (Ejemplo: 1559)");
        try {
            int fechaBuscada = teclado.nextInt();
            teclado.nextLine();

            List<Autor> autoresVivos = autorRepository.buscarAutorVivo(String.valueOf(fechaBuscada));

            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron registros.");
            } else {
                autoresVivos.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Escriba un año válido, Ejemplo: 1600");
            teclado.nextLine();
        }
    }

    public void listarIdiomas() {
        List<LibroIdiomaCount> idiomas = libroRepository.buscarIdiomasCount();
        idiomas.forEach(i -> System.out.printf("Código idioma: %s, Cantidad de libros: %d%n", i.getIdioma(), i.getCount()));

        System.out.println("Ingresa el código de idioma para listar los libros: (Ejemplo: es)");

        try {
            String codigo = teclado.nextLine();
            if (codigo.length() != 2) {
                throw new InputMismatchException("Los códigos contienen 2 caracteres, Ejemplo: es");
            }

            boolean idiomaValido = idiomas.stream()
                    .anyMatch(idioma -> idioma.getIdioma().equals(codigo));

            if (idiomaValido) {
                libroRepository.findByIdiomaEquals(codigo).forEach(System.out::println);
            } else {
                System.out.println("Código inválido!");
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    public void top10Descargas() {
        System.out.println("Los TOP 10 con más descargas son:");
        libroRepository.findTop10ByOrderByCantidadDeDescargasDesc().forEach(System.out::println);
    }

    public void showMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título.
                    2 - Listar libros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos en un determinado año.
                    5 - Listar libros por idioma
                    6 - Listar el TOP 10 Libros con mas descargas.
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarYGuardarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarIdiomas();
                    break;
                case 6:
                    top10Descargas();
                    break;
                case 0:
                    System.out.println("Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }

    }


}
