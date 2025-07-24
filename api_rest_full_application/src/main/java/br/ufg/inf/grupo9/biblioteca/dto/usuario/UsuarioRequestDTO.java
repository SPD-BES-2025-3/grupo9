package br.ufg.inf.grupo9.biblioteca.dto.usuario;

import lombok.Builder;
import lombok.Data;

/**
 * A classe UsuarioRequestDTO representa um objeto de transferência de dados (DTO)
 * utilizado para receber informações sobre usuario a serem criadas ou atualizadas.
 * É marcada com a anotação Lombok @Data para geração automática de getters, setters, equals, hashCode, e toString.
 */

@Data
@Builder
public class UsuarioRequestDTO {

    private String nome;

    private String email;

    private String senha;

    private String telefone;
}
