package br.ufg.inf.grupo9.biblioteca.dto.livro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A classe LivroResponseDTO representa um objeto de transferência de dados (DTO)
 * utilizado para enviar informações sobre livros como resposta a requisições.
 * É marcada com as anotações Lombok @Data e @Builder para geração automática de métodos
 * e para facilitar a construção do objeto usando o padrão de design Builder.
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponseDTO {

    private String id;

    private String titulo;

    private String isbn;

    private int anoPublicacao;

    private double preco;

    private String idEditora;

    private String idAutor;
}
