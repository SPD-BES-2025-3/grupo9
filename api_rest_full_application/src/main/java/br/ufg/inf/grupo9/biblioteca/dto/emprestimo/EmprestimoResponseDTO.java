package br.ufg.inf.grupo9.biblioteca.dto.emprestimo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * A classe EmprestimoResponseDTO representa um objeto de transferência de dados (DTO)
 * utilizado para enviar informações sobre emprestimo como resposta a requisições.
 * É marcada com as anotações Lombok @Data e @Builder para geração automática de métodos
 * e para facilitar a construção do objeto usando o padrão de design Builder.
 */

@Data
@Builder
public class EmprestimoResponseDTO {

    private String id;

    private String idUsuario;

    private String idLivro;

    private Date dataEmprestimo;

    private Date dataDevolucaoPrevista;

    private Date dataDevolucaoRealizada;
}
