package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Emprestimo;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import org.springframework.stereotype.Component;

/**
 * A classe EmprestimoAdapter é responsável por realizar a conversão entre diferentes representações
 * de objetos, como EmprestimoRequestDTO, EmprestimoResponseDTO e Emprestimo.
 *
 * Esta classe facilita a transformação de dados entre:
 * - EmprestimoRequestDTO -> Emprestimo (para criação/atualização de dados)
 * - Emprestimo -> EmprestimoResponseDTO (para exibição de dados)
 */
@Component
public class EmprestimoAdapter {

    /**
     * Converte um objeto EmprestimoRequestDTO, juntamente com as entidades Autor e Editora correspondentes,
     * em uma instância da entidade Emprestimo.
     *
     * @param emprestimoRequestDTO     O DTO com os dados do livro a ser criado ou atualizado.
     * @param usuario   A entidade Usuario já carregada do banco de dados.
     * @param livro A entidade Livro já carregada do banco de dados.
     * @return Uma instância da entidade Emprestimo com os dados do DTO e referências completas.
     */
    public Emprestimo toEmprestimo(EmprestimoRequestDTO emprestimoRequestDTO, Usuario usuario, Livro livro) {
        return Emprestimo.builder()
                .usuario(usuario)
                .livro(livro)
                .dataEmprestimo(emprestimoRequestDTO.getDataEmprestimo())
                .dataDevolucaoPrevista(emprestimoRequestDTO.getDataDevolucaoPrevista())
                .dataDevolucaoRealizada(emprestimoRequestDTO.getDataDevolucaoRealizada())
                .build();
    }

    /**
     * Converte uma entidade Emprestimo em um objeto EmprestimoResponseDTO, extraindo os dados principais
     * e os identificadores das entidades relacionadas (Usuario e Livro).
     *
     * @param emprestimo A entidade Emprestimo a ser convertida.
     * @return Um DTO contendo os dados do livro e os IDs do usuario e da livro.
     */
    public EmprestimoResponseDTO toEmprestimoDTO(Emprestimo emprestimo) {
        return EmprestimoResponseDTO.builder()
                .id(emprestimo.getId())
                .idUsuario(emprestimo.getUsuario().getId())
                .idLivro(emprestimo.getLivro().getId())
                .dataEmprestimo(emprestimo.getDataEmprestimo())
                .dataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista())
                .dataDevolucaoRealizada(emprestimo.getDataDevolucaoRealizada())
                .build();
    }
}
