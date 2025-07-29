package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.EditoraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditoraControllerTest {

    @Mock
    private EditoraService editoraService;

    @InjectMocks
    private EditoraController editoraController;

    private EditoraResponseDTO editoraResponse;
    private EditoraRequestDTO editoraRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        editoraRequest = new EditoraRequestDTO("Editora A", "Brasil", "Contato");
        editoraResponse = new EditoraResponseDTO("1", "Editora A", "Rua 1", "Brasil");
    }

    @Test
    void getAllEditora_deveRetornarListaDeEditoras() {
        when(editoraService.getAllEditora()).thenReturn(List.of(editoraResponse));

        ResponseEntity<List<EditoraResponseDTO>> response = editoraController.getAllEditora();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(editoraService, times(1)).getAllEditora();
    }

    @Test
    void getEditoraById_deveRetornarEditora() {
        when(editoraService.getEditoraById("1")).thenReturn(editoraResponse);

        ResponseEntity<EditoraResponseDTO> response = editoraController.getEditoraById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Editora A", response.getBody().getNome());
        verify(editoraService).getEditoraById("1");
    }

    @Test
    void findByNome_deveRetornarListaDeEditorasComNome() {
        when(editoraService.getEditoraByNome("Editora A")).thenReturn(List.of(editoraResponse));

        ResponseEntity<List<EditoraResponseDTO>> response = editoraController.findByNome("Editora A");

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        verify(editoraService).getEditoraByNome("Editora A");
    }

    @Test
    void addEditora_deveCriarNovaEditora() {
        when(editoraService.createEditora(editoraRequest)).thenReturn(editoraResponse);

        ResponseEntity<EditoraResponseDTO> response = editoraController.addEditora(editoraRequest);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Editora A", response.getBody().getNome());
        verify(editoraService).createEditora(editoraRequest);
    }

    @Test
    void updateEditora_deveAtualizarEditora() {
        when(editoraService.updateEditora("1", editoraRequest)).thenReturn(editoraResponse);

        ResponseEntity<EditoraResponseDTO> response = editoraController.updateEditora("1", editoraRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Editora A", response.getBody().getNome());
        verify(editoraService).updateEditora("1", editoraRequest);
    }

    @Test
    void deleteEditora_deveDeletarEditora() {
        doNothing().when(editoraService).deleteEditora("1");

        ResponseEntity<String> response = editoraController.deleteEditora("1");

        assertEquals(204, response.getStatusCodeValue());
        verify(editoraService).deleteEditora("1");
    }
}

