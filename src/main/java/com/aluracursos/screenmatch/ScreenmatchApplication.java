package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {


	// IMPORTANTE INYECCIÓN DE DEPENDECIAS, SE DEBE SER UNA CLASE CREADA GERENCIADA POR SPRING BOOT
	// este caso la inyección de dependencias es para instanciar a interface SerieRepository

	@Autowired
	private SerieRepository repository;  // la inyección, dependecia que queremos que maneje
	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// se pasa al constructor atributo que creamos para que gerencie spring boot
		// al principio da error por que demos crear el costructor
		Principal principal = new Principal(repository);
		principal.muestraElMenu();


	}
}
