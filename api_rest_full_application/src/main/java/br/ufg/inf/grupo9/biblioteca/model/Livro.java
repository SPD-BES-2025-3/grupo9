package br.ufg.inf.grupo9.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "livros")
public class Livro {

    @Id
    private String id;

    private String titulo;

    private String isbn;

    private int anoPublicacao;

    private double preco;

    @DBRef
    private Editora editora;

    @DBRef
    private Autor autor;
}