package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.EditoraAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import br.ufg.inf.grupo9.biblioteca.repository.EditoraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditoraServiceTest {

    private EditoraRepository editoraRepository;
    private EditoraAdapter editoraAdapter;
    private EditoraService editoraService;

    private Editora editoraEntity;
    private EditoraRequestDTO requestDTO;
    private EditoraResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        editoraRepository = mock(EditoraRepository.class);
        editoraAdapter = mock(EditoraAdapter.class);
        editoraService = new EditoraService(editoraRepository, editoraAdapter);

        editoraEntity = new Editora();
        editoraEntity.setId("1");
        editoraEntity.setNome("Editora Exemplo");

        requestDTO = new EditoraRequestDTO();
        requestDTO.setNome("Editora Exemplo");

        responseDTO = new EditoraResponseDTO();
        responseDTO.setId("1");
        responseDTO.setNome("Editora Exemplo");

    }

    @Test
    void testGetAllEditora() {
        when(editoraRepository.findAll()).thenReturn(List.of(editoraEntity));
        when(editoraAdapter.toEditoraDTO(editoraEntity)).thenReturn(responseDTO);

        List<EditoraResponseDTO> result = editoraService.getAllEditora();

        assertEquals(1, result.size());
        assertEquals("Editora Exemplo", result.get(0).getNome());
        verify(editoraRepository).findAll();
    }

    @Test
    void testGetEditoraById_ComSucesso() {
        when(editoraRepository.findById("1")).thenReturn(Optional.of(editoraEntity));
        when(editoraAdapter.toEditoraDTO(editoraEntity)).thenReturn(responseDTO);

        EditoraResponseDTO result = editoraService.getEditoraById("1");

        assertEquals("Editora Exemplo", result.getNome());
        verify(editoraRepository).findById("1");
    }

    @Test
    void testGetEditoraById_NaoEncontrada() {
        when(editoraRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> editoraService.getEditoraById("2"));
        verify(editoraRepository).findById("2");
    }

    @Test
    void testGetEditoraByNome() {
        when(editoraRepository.findByNome("Editora Exemplo")).thenReturn(List.of(editoraEntity));
        when(editoraAdapter.toEditoraDTO(editoraEntity)).thenReturn(responseDTO);

        List<EditoraResponseDTO> result = editoraService.getEditoraByNome("Editora Exemplo");

        assertEquals(1, result.size());
        assertEquals("Editora Exemplo", result.get(0).getNome());
    }

    @Test
    void testCreateEditora() {
        when(editoraAdapter.toEditora(requestDTO)).thenReturn(editoraEntity);
        when(editoraRepository.save(editoraEntity)).thenReturn(editoraEntity);
        when(editoraAdapter.toEditoraDTO(editoraEntity)).thenReturn(responseDTO);

        EditoraResponseDTO result = editoraService.createEditora(requestDTO);

        assertNotNull(result);
        assertEquals("Editora Exemplo", result.getNome());
        verify(editoraRepository).save(editoraEntity);
    }

    @Test
    void testUpdateEditora_ComSucesso() {
        when(editoraRepository.findById("1")).thenReturn(Optional.of(editoraEntity));
        when(editoraAdapter.toEditora(requestDTO)).thenReturn(editoraEntity);
        when(editoraRepository.save(editoraEntity)).thenReturn(editoraEntity);
        when(editoraAdapter.toEditoraDTO(editoraEntity)).thenReturn(responseDTO);

        EditoraResponseDTO result = editoraService.updateEditora("1", requestDTO);

        assertEquals("Editora Exemplo", result.getNome());
        verify(editoraRepository).save(editoraEntity);
    }

    @Test
    void testUpdateEditora_NaoEncontrada() {
        when(editoraRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> editoraService.updateEditora("999", requestDTO));
    }

    @Test
    void testDeleteEditora_ComSucesso() {
        when(editoraRepository.findById("1")).thenReturn(Optional.of(editoraEntity));

        editoraService.deleteEditora("1");

        verify(editoraRepository).delete(editoraEntity);
    }

    @Test
    void testDeleteEditora_NaoEncontrada() {
        when(editoraRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> editoraService.deleteEditora("999"));
    }
}

