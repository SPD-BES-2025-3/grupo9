package br.ufg.inf.grupo9.biblioteca.dto.livro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A classe LivroRequestDTO representa um objeto de transferência de dados (DTO)
 * utilizado para receber informações sobre livros a serem criadas ou atualizadas.
 * É marcada com a anotação Lombok @Data para geração automática de getters, setters, equals, hashCode, e toString.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LivroRequestDTO {

    private String titulo;

    private String isbn;

    private int anoPublicacao;

    private double preco;

    private String idEditora;

    private String idAutor;
}
