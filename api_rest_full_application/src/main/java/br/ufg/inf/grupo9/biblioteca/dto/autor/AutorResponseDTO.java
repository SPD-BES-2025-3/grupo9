package br.ufg.inf.grupo9.biblioteca.dto.autor;

/**
 * A classe AutorResponseDTO representa um objeto de transferência de dados (DTO)
 * utilizado para enviar informações sobre autores como resposta a requisições.
 * É marcada com as anotações Lombok @Data e @Builder para geração automática de métodos
 * e para facilitar a construção do objeto usando o padrão de design Builder.
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutorResponseDTO {

    private String id;
    private String nome;
    private String nacionalidade;


    public AutorResponseDTO(String id, String nome, String nacionalidade) {
        this.id = id;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    public AutorResponseDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
