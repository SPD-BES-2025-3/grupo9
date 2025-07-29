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

    public EmprestimoResponseDTO() {
    }

    public EmprestimoResponseDTO(String id, String idUsuario, String idLivro, Date dataEmprestimo, Date dataDevolucaoPrevista, Date dataDevolucaoRealizada) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoRealizada = dataDevolucaoRealizada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucaoRealizada() {
        return dataDevolucaoRealizada;
    }

    public void setDataDevolucaoRealizada(Date dataDevolucaoRealizada) {
        this.dataDevolucaoRealizada = dataDevolucaoRealizada;
    }
}
