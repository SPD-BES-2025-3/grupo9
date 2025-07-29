package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsuario() {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        when(usuarioService.getAllUsuario()).thenReturn(List.of(usuario));

        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.getAllUsuario();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetUsuarioById() {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        when(usuarioService.getUsuarioById("1")).thenReturn(usuario);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.getUsuarioById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void testFindByNome() {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        when(usuarioService.getUsuarioByNome("João"))
                .thenReturn(Arrays.asList(usuario));

        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.findByNome("João");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindByEmail() {
        UsuarioResponseDTO usuario = new UsuarioResponseDTO();
        when(usuarioService.getUsuarioByEmail("joao@email.com"))
                .thenReturn(Arrays.asList(usuario));

        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.findByEmail("joao@email.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testCreateUsuario() {
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        when(usuarioService.createUsuario(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testUpdateUsuario() {
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        when(usuarioService.updateUsuario("1", requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UsuarioResponseDTO> response = usuarioController.updateUsuario("1", requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioService).deleteUsuario("1");

        ResponseEntity<String> response = usuarioController.deleteUsuario("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}

