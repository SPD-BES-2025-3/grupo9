package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioAdapterTest {

    private UsuarioAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new UsuarioAdapter();
    }

    @Test
    void deveConverterUsuarioRequestDTOParaUsuario() {

        UsuarioRequestDTO requestDTO = UsuarioRequestDTO.builder()
                .nome("Maria Silva")
                .email("maria@exemplo.com")
                .senha("123456")
                .telefone("61999999999")
                .build();

        Usuario usuario = adapter.toUsuario(requestDTO);


        assertEquals(requestDTO.getNome(), usuario.getNome());
        assertEquals(requestDTO.getEmail(), usuario.getEmail());
        assertEquals(requestDTO.getSenha(), usuario.getSenha());
        assertEquals(requestDTO.getTelefone(), usuario.getTelefone());
        assertNull(usuario.getId()); // O ID geralmente é gerado pelo banco
    }

    @Test
    void deveConverterUsuarioParaUsuarioResponseDTO() {

        Usuario usuario = Usuario.builder()
                .id("123")
                .nome("João Souza")
                .email("joao@exemplo.com")
                .senha("senha123")
                .telefone("61988887777")
                .build();


        UsuarioResponseDTO responseDTO = adapter.toUsuarioDTO(usuario);

        assertEquals(usuario.getId(), responseDTO.getId());
        assertEquals(usuario.getNome(), responseDTO.getNome());
        assertEquals(usuario.getEmail(), responseDTO.getEmail());
        assertEquals(usuario.getTelefone(), responseDTO.getTelefone());
    }
}

