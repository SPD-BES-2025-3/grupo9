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

    public EmprestimoRequestDTO() {
    }

    public EmprestimoRequestDTO(String idUsuario, String idLivro, Date dataEmprestimo, Date dataDevolucaoPrevista, Date dataDevolucaoRealizada) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoRealizada = dataDevolucaoRealizada;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
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
