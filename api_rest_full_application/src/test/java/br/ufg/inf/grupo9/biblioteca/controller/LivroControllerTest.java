package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        LivroResponseDTO livro1 = new LivroResponseDTO("1", "Livro A", "Autor A", "Editora A", 2020);
        LivroResponseDTO livro2 = new LivroResponseDTO("2", "Livro B", "Autor B", "Editora B", 2021);
        List<LivroResponseDTO> livros = Arrays.asList(livro1, livro2);

        when(livroService.getAllLivro()).thenReturn(livros);

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getAllLivro();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetLivroById() {
        LivroResponseDTO livro = new LivroResponseDTO("1", "Livro A", "Autor A", "Editora A", 2020);
        when(livroService.getLivroById("1")).thenReturn(livro);

        ResponseEntity<LivroResponseDTO> response = livroController.getLivroById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Livro A", response.getBody().getTitulo());
    }

    @Test
    void testGetLivroByTitulo() {
        LivroResponseDTO livro = new LivroResponseDTO("1", "Livro A", "Autor A", "Editora A", 2020);
        when(livroService.getLivroByTitulo("Livro A")).thenReturn(List.of(livro));

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getLivroByTitulo("Livro A");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testCreateLivro() {
        LivroRequestDTO requestDTO = new LivroRequestDTO("Livro A", "Autor A", "Editora A", 2020);
        LivroResponseDTO responseDTO = new LivroResponseDTO("1", "Livro A", "Autor A", "Editora A", 2020);

        when(livroService.createLivro(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<LivroResponseDTO> response = livroController.create(requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Livro A", response.getBody().getTitulo());
    }

    @Test
    void testUpdateLivro() {
        LivroRequestDTO requestDTO = new LivroRequestDTO("Livro Atualizado", "Autor A", "Editora A", 2021);
        LivroResponseDTO responseDTO = new LivroResponseDTO("1", "Livro Atualizado", "Autor A", "Editora A", 2021);

        when(livroService.updateLivro("1", requestDTO)).thenReturn(responseDTO);

        ResponseEntity<LivroResponseDTO> response = livroController.update("1", requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Livro Atualizado", response.getBody().getTitulo());
    }

    @Test
    void testDeleteLivro() {
        doNothing().when(livroService).deleteLivro("1");

        ResponseEntity<String> response = livroController.delete("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
