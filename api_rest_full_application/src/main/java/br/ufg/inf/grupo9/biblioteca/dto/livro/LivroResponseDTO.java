package br.ufg.inf.grupo9.biblioteca.dto.livro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A classe LivroResponseDTO representa um objeto de transferência de dados (DTO)
 * utilizado para enviar informações sobre livros como resposta a requisições.
 * É marcada com as anotações Lombok @Data e @Builder para geração automática de métodos
 * e para facilitar a construção do objeto usando o padrão de design Builder.
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroResponseDTO {

    private String id;

    private String titulo;

    private String isbn;

    private int anoPublicacao;

    private double preco;

    private String idEditora;

    private String idAutor;

    public LivroResponseDTO(String id, String titulo, String isbn, int anoPublicacao, double preco, String idEditora, String idAutor) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.idEditora = idEditora;
        this.idAutor = idAutor;
    }

    public LivroResponseDTO(String id, String titulo, String isbn, int anoPublicacao) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(String idEditora) {
        this.idEditora = idEditora;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }
}
