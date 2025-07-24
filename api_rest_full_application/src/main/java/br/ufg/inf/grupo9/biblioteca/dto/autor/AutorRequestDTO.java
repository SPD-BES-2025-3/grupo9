package br.ufg.inf.grupo9.biblioteca.dto.autor;

import lombok.Builder;
import lombok.Data;

/**
 * A classe AutorRequestDTO representa um objeto de transferência de dados (DTO)
 * utilizado para receber informações sobre autores a serem criadas ou atualizadas.
 * É marcada com a anotação Lombok @Data para geração automática de getters, setters, equals, hashCode, e toString.
 */

@Data
@Builder
public class AutorRequestDTO {

    private String nome;

    private String nacionalidade;
}
