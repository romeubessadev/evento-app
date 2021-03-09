package com.eventoapp.eventoapp;

import com.eventoapp.eventoapp.controllers.EventoController;
import com.eventosapp.eventoapp.repository.EventoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses= EventoController.class)
@EnableJpaRepositories(basePackageClasses = EventoRepository.class)
public class EventoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventoappApplication.class, args);
	}

}
