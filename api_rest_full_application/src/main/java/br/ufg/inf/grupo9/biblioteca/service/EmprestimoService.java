package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.EmprestimoAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Emprestimo;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import br.ufg.inf.grupo9.biblioteca.pubsub.RedisPublisher;
import br.ufg.inf.grupo9.biblioteca.repository.EmprestimoRepository;
import br.ufg.inf.grupo9.biblioteca.repository.LivroRepository;
import br.ufg.inf.grupo9.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmprestimoAdapter emprestimoAdapter;
    private final RedisPublisher redisPublisher;

    /**
     * Obtém todos os emprestimo.
     *
     * @return Uma lista de DTOs de resposta das editoras.
     */
    public List<EmprestimoResponseDTO> getAllEmprestimo() {
        return emprestimoRepository.findAll()
                .stream()
                .map(emprestimoAdapter::toEmprestimoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém um emprestimo por ‘ID’.
     *
     * @param id O ‘ID’ da emprestimo a ser obtida.
     * @return A emprestimo encontrada.
     * @throws RuntimeException Se a emprestimo não for encontrada.
     */
    public EmprestimoResponseDTO getEmprestimoById(String id) {
        final var emprestimoFound = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Emprestimo não encontrado."));

        return emprestimoAdapter.toEmprestimoDTO(emprestimoFound);
    }

    /**
     * Obtém emprestimo por dataEmprestimo.
     *
     * @param dataEmprestimo A dataEmprestimo pelo qual os emprestimos serão filtradas.
     * @return Uma lista de emprestimos encontradas pela dataEmprestimo, encapsulada em ResponseEntity.
     */
    public List<EmprestimoResponseDTO> getEmprestimoByDataEmprestimo(Date dataEmprestimo) {
        return this.emprestimoRepository.findByDataEmprestimo(dataEmprestimo)
                .stream()
                .map(emprestimoAdapter::toEmprestimoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém emprestimo por livroId.
     *
     * @param livroId O livroId pelo qual os emprestimos serão filtradas.
     * @return Uma lista de emprestimos encontradas pelo livroId, encapsulada em ResponseEntity.
     */
    public List<EmprestimoResponseDTO> getEmprestimosPorLivro(String livroId) {
        final var livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado"));

        final var emprestimos = emprestimoRepository.findByLivro(String.valueOf(livro));

        return emprestimos.stream()
                .map(emprestimoAdapter::toEmprestimoDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma emprestimo.
     *
     * @param emprestimoRequestDTO DTO contendo os dados da nova emprestimo.
     * @return O emprestimo recém-criada, encapsulada em ResponseEntity.
     */
    public EmprestimoResponseDTO createEmprestimo(EmprestimoRequestDTO emprestimoRequestDTO) {
        System.out.println("Entrou no método createEmprestimo");

        Usuario usuario = usuarioRepository.findById(emprestimoRequestDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Livro livro = livroRepository.findById(emprestimoRequestDTO.getIdLivro())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));

        Emprestimo emprestimo = emprestimoAdapter.toEmprestimo(emprestimoRequestDTO, usuario, livro);
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimo);
        redisPublisher.publish(RedisPublisher.OperationType.CREATE, emprestimoSalvo);

        return emprestimoAdapter.toEmprestimoDTO(emprestimoSalvo);
    }

    /**
     * Atualiza uma emprestimo.
     *
     * @param id                O ‘ID’ da editora a ser atualizada.
     * @param emprestimoRequestDTO DTO contendo os novos dados da emprestimo.
     * @return A emprestimo atualizada.
     */
    public EmprestimoResponseDTO updateEmprestimo(String id, EmprestimoRequestDTO emprestimoRequestDTO) {
        Emprestimo emprestimoFound = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Empréstimo não encontrado"));

        Usuario usuario = usuarioRepository.findById(emprestimoRequestDTO.getIdUsuario())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Livro livro = livroRepository.findById(emprestimoRequestDTO.getIdLivro())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado"));

        Emprestimo emprestimoAtualizado = emprestimoAdapter.toEmprestimo(emprestimoRequestDTO, usuario, livro);
        emprestimoAtualizado.setId(emprestimoFound.getId());
        Emprestimo emprestimoSalvo = emprestimoRepository.save(emprestimoAtualizado);
        redisPublisher.publish(RedisPublisher.OperationType.UPDATE, emprestimoSalvo);

        return emprestimoAdapter.toEmprestimoDTO(emprestimoSalvo);
    }

    /**
     * Delete uma emprestimo por ‘ID’.
     *
     * @param id O ‘ID’ da emprestimo a ser deletada.
     * @return ResponseEntity sem corpo indicando que a emprestimo foi deletada com sucesso.
     */
    public void deleteEmprestimo(String id) {
        final var emprestimo = this.emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Emprestimo não encontrado"));

        this.emprestimoRepository.delete(emprestimo);
        redisPublisher.publish(RedisPublisher.OperationType.DELETE, emprestimo);
    }
}
