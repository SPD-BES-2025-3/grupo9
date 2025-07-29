package br.ufg.inf.grupo9.biblioteca.dto.editora;

import lombok.Builder;
import lombok.Data;

/**
 * A classe EditoraRequestDTO representa um objeto de transferência de dados (DTO)
 * utilizado para receber informações sobre editora a serem criadas ou atualizadas.
 * É marcada com a anotação Lombok @Data para geração automática de getters, setters, equals, hashCode, e toString.
 */

@Data
@Builder
public class EditoraRequestDTO {

    private String nome;

    private String endereco;

    private String telefone;

    public EditoraRequestDTO() {
    }

    public EditoraRequestDTO(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
