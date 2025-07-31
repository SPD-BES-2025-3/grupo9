package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.LivroService;
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

class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLivro() {
        List<LivroResponseDTO> livros = Arrays.asList(
                new LivroResponseDTO("1", "Livro A", "123", 2020, 29.9, null, null),
                new LivroResponseDTO("2", "Livro B", "456", 2021, 39.9, null, null)
        );
        when(livroService.getAllLivro()).thenReturn(livros);

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getAllLivro();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(livroService, times(1)).getAllLivro();
    }

    @Test
    void testGetLivroById() {
        LivroResponseDTO livro = new LivroResponseDTO("1", "Livro A", "123", 2020, 29.9, null, null);
        when(livroService.getLivroById("1")).thenReturn(livro);

        ResponseEntity<LivroResponseDTO> response = livroController.getLivroById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Livro A", response.getBody().getTitulo());
        verify(livroService, times(1)).getLivroById("1");
    }

    @Test
    void testGetLivroByTitulo() {
        List<LivroResponseDTO> livros = List.of(new LivroResponseDTO("1", "Livro A", "123", 2020, 29.9, null, null));
        when(livroService.getLivroByTitulo("Livro A")).thenReturn(livros);

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getLivroByTitulo("Livro A");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(livroService, times(1)).getLivroByTitulo("Livro A");
    }

    @Test
    void testCreateLivro() {
        LivroRequestDTO requestDTO = new LivroRequestDTO("Livro A", "123", 2020, 29.9, null, null);
        LivroResponseDTO responseDTO = new LivroResponseDTO("1", "Livro A", "123", 2020, 29.9, null, null);

        when(livroService.createLivro(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<LivroResponseDTO> response = livroController.create(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Livro A", response.getBody().getTitulo());
        verify(livroService, times(1)).createLivro(requestDTO);
    }

    @Test
    void testUpdateLivro() {
        LivroRequestDTO requestDTO = new LivroRequestDTO("Livro Atualizado", "456", 2022, 39.9, null, null);
        LivroResponseDTO responseDTO = new LivroResponseDTO("1", "Livro Atualizado", "456", 2022, 39.9, null, null);

        when(livroService.updateLivro("1", requestDTO)).thenReturn(responseDTO);

        ResponseEntity<LivroResponseDTO> response = livroController.update("1", requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Livro Atualizado", response.getBody().getTitulo());
        verify(livroService, times(1)).updateLivro("1", requestDTO);
    }

    @Test
    void testDeleteLivro() {
        doNothing().when(livroService).deleteLivro("1");

        ResponseEntity<String> response = livroController.delete("1");

        assertEquals(204, response.getStatusCodeValue());
        verify(livroService, times(1)).deleteLivro("1");
    }
}

