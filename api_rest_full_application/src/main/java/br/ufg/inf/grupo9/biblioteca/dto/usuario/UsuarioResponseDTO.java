package br.ufg.inf.grupo9.biblioteca.dto.usuario;

/**
 * A classe UsuarioResponseDTO representa um objeto de transferência de dados (DTO)
 * utilizado para enviar informações sobre usuarios como resposta a requisições.
 * É marcada com as anotações Lombok @Data e @Builder para geração automática de métodos
 * e para facilitar a construção do objeto usando o padrão de design Builder.
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private String id;

    private String nome;

    private String email;

    private String telefone;
}
