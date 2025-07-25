package br.ufg.inf.grupo9.biblioteca.dto.editora;

import lombok.Builder;
import lombok.Data;

/**
 * A classe EditoraResponseDTO representa um objeto de transferência de dados (DTO)
 * utilizado para enviar informações sobre editora como resposta a requisições.
 * É marcada com as anotações Lombok @Data e @Builder para geração automática de métodos
 * e para facilitar a construção do objeto usando o padrão de design Builder.
 */

@Data
@Builder
public class EditoraResponseDTO {

    private String id;

    private String nome;

    private String endereco;

    private String telefone;
}
