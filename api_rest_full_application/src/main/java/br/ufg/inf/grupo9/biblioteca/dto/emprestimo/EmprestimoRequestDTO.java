package br.ufg.inf.grupo9.biblioteca.dto.emprestimo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * A classe EmprestimoRequestDTO representa um objeto de transferência de dados (DTO)
 * utilizado para receber informações sobre emprestimos a serem criadas ou atualizadas.
 * É marcada com a anotação Lombok @Data para geração automática de getters, setters, equals, hashCode, e toString.
 */

@Data
@Builder
public class EmprestimoRequestDTO {

    private String idUsuario;

    private String idLivro;

    private Date dataEmprestimo;

    private Date dataDevolucaoPrevista;

    private Date dataDevolucaoRealizada;
}
