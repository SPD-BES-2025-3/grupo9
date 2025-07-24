package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * A classe LivroAdapter é responsável por realizar a conversão entre diferentes representações
 * de objetos, como LivroRequestDTO, LivroResponseDTO e Livro.
 * <p>
 * Esta classe facilita a transformação de dados entre:
 * - LivroRequestDTO -> Livro (para criação/atualização de dados)
 * - Livro -> LivroResponseDTO (para exibição de dados)
 */
@Component
public class LivroAdapter {

    /**
     * Converte um objeto LivroRequestDTO, juntamente com as entidades Autor e Editora correspondentes,
     * em uma instância da entidade Livro.
     *
     * @param dto     O DTO com os dados do livro a ser criado ou atualizado.
     * @param autor   A entidade Autor já carregada do banco de dados.
     * @param editora A entidade Editora já carregada do banco de dados.
     * @return Uma instância da entidade Livro com os dados do DTO e referências completas.
     */
    public Livro toLivro(LivroRequestDTO dto, Autor autor, Editora editora) {
        return Livro.builder()
                .titulo(dto.getTitulo())
                .isbn(dto.getIsbn())
                .anoPublicacao(dto.getAnoPublicacao())
                .preco(dto.getPreco())
                .autor(autor)
                .editora(editora)
                .build();
    }

    /**
     * Converte uma entidade Livro em um objeto LivroResponseDTO, extraindo os dados principais
     * e os identificadores das entidades relacionadas (Autor e Editora).
     *
     * @param livro A entidade Livro a ser convertida.
     * @return Um DTO contendo os dados do livro e os IDs do autor e da editora.
     */
    public LivroResponseDTO toLivroDTO(Livro livro) {
        return LivroResponseDTO.builder()
                .id(livro.getId())
                .titulo(livro.getTitulo())
                .isbn(livro.getIsbn())
                .anoPublicacao(livro.getAnoPublicacao())
                .preco(livro.getPreco())
                .idAutor(Optional.ofNullable(livro.getAutor()).map(Autor::getId).orElse(null))
                .idEditora(Optional.ofNullable(livro.getEditora()).map(Editora::getId).orElse(null))
                .build();
    }
}