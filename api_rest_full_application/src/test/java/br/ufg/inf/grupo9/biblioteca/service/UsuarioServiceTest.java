package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.UsuarioAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import br.ufg.inf.grupo9.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {



    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioAdapter usuarioAdapter;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    private UsuarioRequestDTO requestDTO;
    private UsuarioResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        usuario = new br.ufg.inf.grupo9.biblioteca.model.Usuario();
        usuario.setId("1");
        usuario.setNome("Maria");
        usuario.setEmail("maria@email.com");

        requestDTO = new UsuarioRequestDTO("Maria", "maria@email.com", "123456");

        responseDTO = new UsuarioResponseDTO();
        responseDTO.setId("1");
        responseDTO.setNome("Maria");
        responseDTO.setEmail("maria@email.com");


    }

    @Test
    void testGetAllUsuario() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioAdapter.toUsuarioDTO(usuario)).thenReturn(responseDTO);

        List<UsuarioResponseDTO> result = usuarioService.getAllUsuario();

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getNome());
    }

    @Test
    void testGetUsuarioByIdSuccess() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioAdapter.toUsuarioDTO(usuario)).thenReturn(responseDTO);

        UsuarioResponseDTO result = usuarioService.getUsuarioById("1");

        assertEquals("Maria", result.getNome());
    }

    @Test
    void testGetUsuarioByIdNotFound() {
        when(usuarioRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.getUsuarioById("2"));
    }

    @Test
    void testGetUsuarioByNome() {
        when(usuarioRepository.findByNome("Maria")).thenReturn(List.of(usuario));
        when(usuarioAdapter.toUsuarioDTO(usuario)).thenReturn(responseDTO);

        List<UsuarioResponseDTO> result = usuarioService.getUsuarioByNome("Maria");

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getNome());
    }

    @Test
    void testGetUsuarioByEmail() {
        when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(List.of(usuario));
        when(usuarioAdapter.toUsuarioDTO(usuario)).thenReturn(responseDTO);

        List<UsuarioResponseDTO> result = usuarioService.getUsuarioByEmail("maria@email.com");

        assertEquals(1, result.size());
        assertEquals("Maria", result.get(0).getNome());
    }

    @Test
    void testCreateUsuario() {
        when(usuarioAdapter.toUsuario(requestDTO)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioAdapter.toUsuarioDTO(usuario)).thenReturn(responseDTO);

        UsuarioResponseDTO result = usuarioService.createUsuario(requestDTO);

        assertEquals("Maria", result.getNome());
    }

    @Test
    void testUpdateUsuarioSuccess() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        when(usuarioAdapter.toUsuario(requestDTO)).thenReturn(usuario);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioAdapter.toUsuarioDTO(usuario)).thenReturn(responseDTO);

        UsuarioResponseDTO result = usuarioService.updateUsuario("1", requestDTO);

        assertEquals("Maria", result.getNome());
    }

    @Test
    void testUpdateUsuarioNotFound() {
        when(usuarioRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.updateUsuario("2", requestDTO));
    }

    @Test
    void testDeleteUsuarioSuccess() {
        when(usuarioRepository.findById("1")).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(usuario);

        assertDoesNotThrow(() -> usuarioService.deleteUsuario("1"));
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void testDeleteUsuarioNotFound() {
        when(usuarioRepository.findById("2")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> usuarioService.deleteUsuario("2"));
    }
}

