package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LivroAdapterTest {

    private LivroAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new LivroAdapter();
    }

    @Test
    void deveConverterLivroRequestDTOParaLivro() {

        LivroRequestDTO dto = LivroRequestDTO.builder()
                .titulo("O Hobbit")
                .isbn("1234567890")
                .anoPublicacao(1937)
                .preco(39.90) // double
                .build();

        Autor autor = Autor.builder()
                .id("1")
                .nome("J.R.R. Tolkien")
                .build();

        Editora editora = Editora.builder()
                .id("10")
                .nome("HarperCollins")
                .build();


        Livro livro = adapter.toLivro(dto, autor, editora);


        assertEquals(dto.getTitulo(), livro.getTitulo());
        assertEquals(dto.getIsbn(), livro.getIsbn());
        assertEquals(dto.getAnoPublicacao(), livro.getAnoPublicacao());
        assertEquals(dto.getPreco(), livro.getPreco(), 0.0001);
        assertEquals(autor, livro.getAutor());
        assertEquals(editora, livro.getEditora());
    }

    @Test
    void deveConverterLivroParaLivroResponseDTO() {

        Autor autor = Autor.builder()
                .id("1")
                .nome("J.R.R. Tolkien")
                .build();

        Editora editora = Editora.builder()
                .id("10")
                .nome("HarperCollins")
                .build();

        Livro livro = Livro.builder()
                .id("abc123")
                .titulo("O Senhor dos Anéis")
                .isbn("9876543210")
                .anoPublicacao(1954)
                .preco(59.90)
                .autor(autor)
                .editora(editora)
                .build();


        LivroResponseDTO dto = adapter.toLivroDTO(livro);

        assertEquals(livro.getId(), dto.getId());
        assertEquals(livro.getTitulo(), dto.getTitulo());
        assertEquals(livro.getIsbn(), dto.getIsbn());
        assertEquals(livro.getAnoPublicacao(), dto.getAnoPublicacao());
        assertEquals(livro.getPreco(), dto.getPreco(), 0.0001);
        assertEquals(autor.getId(), dto.getIdAutor());
        assertEquals(editora.getId(), dto.getIdEditora());
    }
}
