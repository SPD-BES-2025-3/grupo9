package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.LivroAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import br.ufg.inf.grupo9.biblioteca.repository.AutorRepository;
import br.ufg.inf.grupo9.biblioteca.repository.EditoraRepository;
import br.ufg.inf.grupo9.biblioteca.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private LivroAdapter livroAdapter;

    @Mock
    private EditoraRepository editoraRepository;

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLivro() {
        Livro livro = new Livro();
        LivroResponseDTO dto = new LivroResponseDTO();

        when(livroRepository.findAll()).thenReturn(List.of(livro));
        when(livroAdapter.toLivroDTO(livro)).thenReturn(dto);

        List<LivroResponseDTO> result = livroService.getAllLivro();

        assertEquals(1, result.size());
        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void testGetLivroByIdSuccess() {
        Livro livro = new Livro();
        livro.setId("123");
        LivroResponseDTO dto = new LivroResponseDTO();

        when(livroRepository.findById("123")).thenReturn(Optional.of(livro));
        when(livroAdapter.toLivroDTO(livro)).thenReturn(dto);

        LivroResponseDTO result = livroService.getLivroById("123");

        assertNotNull(result);
    }

    @Test
    void testGetLivroByIdNotFound() {
        when(livroRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> livroService.getLivroById("123"));
    }

    @Test
    void testGetLivroByTitulo() {
        Livro livro = new Livro();
        LivroResponseDTO dto = new LivroResponseDTO();

        when(livroRepository.findByTitulo("Dom Casmurro")).thenReturn(List.of(livro));
        when(livroAdapter.toLivroDTO(livro)).thenReturn(dto);

        List<LivroResponseDTO> result = livroService.getLivroByTitulo("Dom Casmurro");

        assertEquals(1, result.size());
    }

    @Test
    void testCreateLivroSuccess() {
        LivroRequestDTO requestDTO = new LivroRequestDTO();
        requestDTO.setIdAutor("1");
        requestDTO.setIdEditora("2");

        Autor autor = new Autor();
        Editora editora = new Editora();
        Livro livro = new Livro();
        Livro livroSalvo = new Livro();
        LivroResponseDTO responseDTO = new LivroResponseDTO();

        when(autorRepository.findById("1")).thenReturn(Optional.of(autor));
        when(editoraRepository.findById("2")).thenReturn(Optional.of(editora));
        when(livroAdapter.toLivro(requestDTO, autor, editora)).thenReturn(livro);
        when(livroRepository.save(livro)).thenReturn(livroSalvo);
        when(livroAdapter.toLivroDTO(livroSalvo)).thenReturn(responseDTO);

        LivroResponseDTO result = livroService.createLivro(requestDTO);

        assertNotNull(result);
    }

    @Test
    void testDeleteLivroSuccess() {
        Livro livro = new Livro();
        livro.setId("123");

        when(livroRepository.findById("123")).thenReturn(Optional.of(livro));

        livroService.deleteLivro("123");

        verify(livroRepository, times(1)).delete(livro);
    }

    @Test
    void testDeleteLivroNotFound() {
        when(livroRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> livroService.deleteLivro("123"));
    }

    @Test
    void testUpdateLivroSuccess() {
        LivroRequestDTO requestDTO = new LivroRequestDTO();
        requestDTO.setIdAutor("1");
        requestDTO.setIdEditora("2");

        Livro livroEncontrado = new Livro();
        livroEncontrado.setId("123");

        Autor autor = new Autor();
        Editora editora = new Editora();
        Livro livroAtualizado = new Livro();
        livroAtualizado.setId("123");
        Livro livroSalvo = new Livro();
        LivroResponseDTO responseDTO = new LivroResponseDTO();

        when(livroRepository.findById("123")).thenReturn(Optional.of(livroEncontrado));
        when(autorRepository.findById("1")).thenReturn(Optional.of(autor));
        when(editoraRepository.findById("2")).thenReturn(Optional.of(editora));
        when(livroAdapter.toLivro(requestDTO, autor, editora)).thenReturn(livroAtualizado);
        when(livroRepository.save(livroAtualizado)).thenReturn(livroSalvo);
        when(livroAdapter.toLivroDTO(livroSalvo)).thenReturn(responseDTO);

        LivroResponseDTO result = livroService.updateLivro("123", requestDTO);

        assertNotNull(result);
    }

    @Test
    void testUpdateLivroNotFound() {
        LivroRequestDTO requestDTO = new LivroRequestDTO();

        when(livroRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> livroService.updateLivro("123", requestDTO));
    }
}
