package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EditoraAdapterTest {

    private EditoraAdapter editoraAdapter;

    @BeforeEach
    void setUp() {
        editoraAdapter = new EditoraAdapter();
    }

    @Test
    void testToEditora() {
        EditoraRequestDTO dto = new EditoraRequestDTO("Editora Teste", "Rua Central, 123", "62-99999-9999");

        Editora editora = editoraAdapter.toEditora(dto);

        assertNotNull(editora);
        assertEquals("Editora Teste", editora.getNome());
        assertEquals("Rua Central, 123", editora.getEndereco());
        assertEquals("62-99999-9999", editora.getTelefone());
    }

    @Test
    void testToEditoraDTO() {
        Editora editora = Editora.builder()
                .id("abc123")
                .nome("Editora Alfa")
                .endereco("Av. Brasil, 456")
                .telefone("62-88888-8888")
                .build();

        EditoraResponseDTO dto = editoraAdapter.toEditoraDTO(editora);

        assertNotNull(dto);
        assertEquals("abc123", dto.getId());
        assertEquals("Editora Alfa", dto.getNome());
        assertEquals("Av. Brasil, 456", dto.getEndereco());
        assertEquals("62-88888-8888", dto.getTelefone());
    }
}
