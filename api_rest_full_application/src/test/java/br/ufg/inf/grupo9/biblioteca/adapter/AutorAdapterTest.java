package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutorAdapterTest {

    private AutorAdapter autorAdapter;

    @BeforeEach
    void setUp() {
        autorAdapter = new AutorAdapter();
    }

    @Test
    void testToAutor() {
        AutorRequestDTO dto = new AutorRequestDTO("João Silva", "Brasileiro");

        Autor autor = autorAdapter.toAutor(dto);

        assertNotNull(autor);
        assertEquals("João Silva", autor.getNome());
        assertEquals("Brasileiro", autor.getNacionalidade());
    }

    @Test
    void testToAutorDTO() {
        Autor autor = Autor.builder()
                .id("123")
                .nome("Maria Souza")
                .nacionalidade("Portuguesa")
                .build();

        AutorResponseDTO dto = autorAdapter.toAutorDTO(autor);

        assertNotNull(dto);
        assertEquals("123", dto.getId());
        assertEquals("Maria Souza", dto.getNome());
        assertEquals("Portuguesa", dto.getNacionalidade());
    }
}

