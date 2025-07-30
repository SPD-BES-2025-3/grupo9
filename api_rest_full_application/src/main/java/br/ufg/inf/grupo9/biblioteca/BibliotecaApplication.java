package br.ufg.inf.grupo9.biblioteca;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "API Biblioteca – Grupo 9",
                version = "1.0.0",
                description = "Documentação dos endpoints de Autores, Editoras, Livros, Empréstimos e Usuários",
                termsOfService = "http://minhaempresa.com.br/termos",
                contact = @Contact(
                        name = "Grupo 9 ",
                        url = " ",
                        email = " @ufg.inf"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        )
)
public class BibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

}
