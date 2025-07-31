package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.EmprestimoAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Emprestimo;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import br.ufg.inf.grupo9.biblioteca.repository.EmprestimoRepository;
import br.ufg.inf.grupo9.biblioteca.repository.LivroRepository;
import br.ufg.inf.grupo9.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmprestimoAdapter emprestimoAdapter;

    @InjectMocks
    private EmprestimoService emprestimoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmprestimoById_found() {
        String id = "123";
        Emprestimo emprestimo = new Emprestimo();
        EmprestimoResponseDTO dto = new EmprestimoResponseDTO();

        when(emprestimoRepository.findById(id)).thenReturn(Optional.of(emprestimo));
        when(emprestimoAdapter.toEmprestimoDTO(emprestimo)).thenReturn(dto);

        EmprestimoResponseDTO result = emprestimoService.getEmprestimoById(id);
        assertEquals(dto, result);
    }

    @Test
    void testGetEmprestimoById_notFound() {
        when(emprestimoRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> emprestimoService.getEmprestimoById("1"));
    }

    @Test
    void testCreateEmprestimo_success() {
        EmprestimoRequestDTO request = new EmprestimoRequestDTO();
        request.setIdLivro("livro1");
        request.setIdUsuario("usuario1");

        Usuario usuario = new Usuario();
        Livro livro = new Livro();
        Emprestimo emprestimo = new Emprestimo();
        Emprestimo emprestimoSalvo = new Emprestimo();
        EmprestimoResponseDTO responseDTO = new EmprestimoResponseDTO();

        when(usuarioRepository.findById("usuario1")).thenReturn(Optional.of(usuario));
        when(livroRepository.findById("livro1")).thenReturn(Optional.of(livro));
        when(emprestimoAdapter.toEmprestimo(request, usuario, livro)).thenReturn(emprestimo);
        when(emprestimoRepository.save(emprestimo)).thenReturn(emprestimoSalvo);
        when(emprestimoAdapter.toEmprestimoDTO(emprestimoSalvo)).thenReturn(responseDTO);

        EmprestimoResponseDTO result = emprestimoService.createEmprestimo(request);
        assertEquals(responseDTO, result);
    }

    @Test
    void testDeleteEmprestimo_success() {
        Emprestimo emprestimo = new Emprestimo();
        when(emprestimoRepository.findById("123")).thenReturn(Optional.of(emprestimo));

        emprestimoService.deleteEmprestimo("123");
        verify(emprestimoRepository, times(1)).delete(emprestimo);
    }

    @Test
    void testDeleteEmprestimo_notFound() {
        when(emprestimoRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> emprestimoService.deleteEmprestimo("999"));
    }
}

