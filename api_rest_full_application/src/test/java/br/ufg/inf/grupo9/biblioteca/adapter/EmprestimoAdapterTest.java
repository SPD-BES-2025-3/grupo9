package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Emprestimo;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoAdapterTest {

    private EmprestimoAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new EmprestimoAdapter();
    }

    @Test
    void testToEmprestimo() {

        EmprestimoRequestDTO dto = new EmprestimoRequestDTO();
        dto.setDataEmprestimo(LocalDate.of(2024, 7, 1));
        dto.setDataDevolucaoPrevista(LocalDate.of(2024, 7, 15));
        dto.setDataDevolucaoRealizada(LocalDate.of(2024, 7, 10));


        Usuario usuario = Usuario.builder().id("u1").nome("João").build();
        Livro livro = Livro.builder().id("l1").titulo("Livro Teste").build();

        Emprestimo emprestimo = adapter.toEmprestimo(dto, usuario, livro);

        assertNotNull(emprestimo);
        assertEquals(usuario, emprestimo.getUsuario());
        assertEquals(livro, emprestimo.getLivro());
        assertEquals(dto.getDataEmprestimo(), emprestimo.getDataEmprestimo());
        assertEquals(dto.getDataDevolucaoPrevista(), emprestimo.getDataDevolucaoPrevista());
        assertEquals(dto.getDataDevolucaoRealizada(), emprestimo.getDataDevolucaoRealizada());
    }

    @Test
    void testToEmprestimoDTO() {

        Usuario usuario = Usuario.builder().id("u123").nome("Maria").build();
        Livro livro = Livro.builder().id("l456").titulo("Java para Todos").build();

        Emprestimo emprestimo = Emprestimo.builder()
                .id("e789")
                .usuario(usuario)
                .livro(livro)
                .dataEmprestimo(LocalDate.of(2025, 1, 5))
                .dataDevolucaoPrevista(LocalDate.of(2025, 1, 20))
                .dataDevolucaoRealizada(LocalDate.of(2025, 1, 18))
                .build();


        EmprestimoResponseDTO dto = adapter.toEmprestimoDTO(emprestimo);

        assertNotNull(dto);
        assertEquals("e789", dto.getId());
        assertEquals("u123", dto.getIdUsuario());
        assertEquals("l456", dto.getIdLivro());
        assertEquals(LocalDate.of(2025, 1, 5), dto.getDataEmprestimo());
        assertEquals(LocalDate.of(2025, 1, 20), dto.getDataDevolucaoPrevista());
        assertEquals(LocalDate.of(2025, 1, 18), dto.getDataDevolucaoRealizada());
    }
}

