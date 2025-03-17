package org.br.ct9backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ct9BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ct9BackendApplication.class, args);
	}

}
