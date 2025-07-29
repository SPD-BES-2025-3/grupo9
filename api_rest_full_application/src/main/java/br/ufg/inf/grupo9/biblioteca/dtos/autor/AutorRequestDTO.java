package br.ufg.inf.grupo9.biblioteca.dtos.autor;

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

    public AutorRequestDTO(String nome, String nacionalidade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
