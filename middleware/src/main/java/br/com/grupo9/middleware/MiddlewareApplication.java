package br.com.grupo9.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
// Habilita os repositórios do MongoDB, especificando o pacote onde eles estão.
@EnableMongoRepositories(basePackages = "br.com.grupo9.middleware.repository.odm")
// Habilita os repositórios JPA (para SQLite), especificando o pacote onde eles estão.
@EnableJpaRepositories(basePackages = "br.com.grupo9.middleware.repository.orm")
public class MiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiddlewareApplication.class, args);
	}

}