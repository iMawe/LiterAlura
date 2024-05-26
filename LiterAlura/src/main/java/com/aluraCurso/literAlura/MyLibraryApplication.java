package com.aluraCurso.literAlura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluraCurso.literAlura.main.Principal;
import com.aluraCurso.literAlura.repository.AutorRepository;
import com.aluraCurso.literAlura.repository.LibroRepository;

@SpringBootApplication
public class MyLibraryApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(MyLibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository, autorRepository);
		principal.showMenu();
	}
}
