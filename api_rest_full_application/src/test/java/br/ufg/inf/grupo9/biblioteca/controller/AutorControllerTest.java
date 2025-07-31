package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorControllerTest {

    private AutorService autorService;
    private AutorController autorController;

    @BeforeEach
    void setUp() {
        autorService = Mockito.mock(AutorService.class);
        autorController = new AutorController(autorService);
    }

    @Test
    void testGetAllAutor() {
        List<AutorResponseDTO> autores = Arrays.asList(
                new AutorResponseDTO("1", "Autor A", "Brasil"),
                new AutorResponseDTO("2", "Autor B", "Portugal")
        );

        when(autorService.getAllAutor()).thenReturn(autores);

        ResponseEntity<List<AutorResponseDTO>> response = autorController.getAllAutor();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAutorById() {
        AutorResponseDTO autor = new AutorResponseDTO("1", "Autor A", "Brasil");
        when(autorService.getAutorById("1")).thenReturn(autor);

        ResponseEntity<AutorResponseDTO> response = autorController.getAutorById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Autor A", response.getBody().getNome());
    }

    @Test
    void testGetAutorByNacionalidade() {
        List<AutorResponseDTO> autores = Arrays.asList(
                new AutorResponseDTO("1", "Autor A", "Brasil")
        );

        when(autorService.getAutorByNacionalidade("Brasil")).thenReturn(autores);

        ResponseEntity<List<AutorResponseDTO>> response = autorController.getAutorByNacionalidade("Brasil");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAutorByNome() {
        List<AutorResponseDTO> autores = Arrays.asList(
                new AutorResponseDTO("1", "Autor A", "Brasil")
        );

        when(autorService.getAutorByNome("Autor A")).thenReturn(autores);

        ResponseEntity<List<AutorResponseDTO>> response = autorController.getAutorByNome("Autor A");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Autor A", response.getBody().get(0).getNome());
    }

    @Test
    void testCreate() {
        AutorRequestDTO request = new AutorRequestDTO("Autor A", "Brasil");
        AutorResponseDTO responseDTO = new AutorResponseDTO("1", "Autor A", "Brasil");

        when(autorService.create(request)).thenReturn(responseDTO);

        ResponseEntity<AutorResponseDTO> response = autorController.create(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Autor A", response.getBody().getNome());
    }

    @Test
    void testUpdate() {
        AutorRequestDTO request = new AutorRequestDTO("Autor A Editado", "Portugal");
        AutorResponseDTO responseDTO = new AutorResponseDTO("1", "Autor A Editado", "Portugal");

        when(autorService.update(request, "1")).thenReturn(responseDTO);

        ResponseEntity<AutorResponseDTO> response = autorController.update("1", request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Autor A Editado", response.getBody().getNome());
    }

    @Test
    void testDelete() {
        doNothing().when(autorService).delete("1");

        ResponseEntity<String> response = autorController.delete("1");

        assertEquals(204, response.getStatusCodeValue());
        verify(autorService, times(1)).delete("1");
    }
}
