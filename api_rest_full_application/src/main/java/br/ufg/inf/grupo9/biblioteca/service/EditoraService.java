package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.EditoraAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.repository.EditoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EditoraService {

    private final EditoraRepository editoraRepository;
    private final EditoraAdapter editoraAdapter;
    private final RedisPublisher redisPublisher;
    /**
     * Obtém todas as editoras.
     *
     * @return Uma lista de DTOs de resposta das editoras.
     */
    public List<EditoraResponseDTO> getAllEditora() {
        return editoraRepository.findAll()
                .stream()
                .map(editoraAdapter::toEditoraDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém uma editora por ‘ID’.
     *
     * @param id O ID da editora a ser obtida.
     * @return A editora encontrada.
     * @throws RuntimeException Se a editora não for encontrada.
     */
    public EditoraResponseDTO getEditoraById(String id) {
        final var editoraFound = this.editoraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Emprestimo não encontrado"));

        return this.editoraAdapter.toEditoraDTO(editoraFound);
    }

    /**
     * Obtém editoras por nome.
     *
     * @param nome O nome pelo qual as editoras serão filtradas.
     * @return Uma lista de editoras encontradas pelo nome.
     */
    public List<EditoraResponseDTO> getEditoraByNome(String nome) {
        return this.editoraRepository.findByNome(nome)
                .stream()
                .map(editoraAdapter::toEditoraDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma editora.
     *
     * @param editoraRequestDTO DTO contendo os dados da nova editora.
     * @return A editora recém-criada.
     */
    public EditoraResponseDTO createEditora(EditoraRequestDTO editoraRequestDTO) {
        final var editora = this.editoraAdapter.toEditora(editoraRequestDTO);
        final var editoraSalva = this.editoraRepository.save(editora);
        redisPublisher.publish(RedisPublisher.OperationType.CREATE, editoraSalva);

        return this.editoraAdapter.toEditoraDTO(editoraSalva);
    }

    /**
     * Atualiza uma editora.
     *
     * @param id                O ‘ID’ da editora a ser atualizada.
     * @param editoraRequestDTO DTO contendo os novos dados da editora.
     * @return A editora atualizada.
     */
    public EditoraResponseDTO updateEditora(String id, EditoraRequestDTO editoraRequestDTO) {
        final var editoraExistente = this.editoraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Editora não encontrado"));

        final var editoraAtualizada = this.editoraAdapter.toEditora(editoraRequestDTO);
        editoraAtualizada.setId(editoraExistente.getId());

        final var editoraSalva = this.editoraRepository.save(editoraAtualizada);
        redisPublisher.publish(RedisPublisher.OperationType.UPDATE, editoraSalva);
        return this.editoraAdapter.toEditoraDTO(editoraSalva);
    }

    /**
     * Delete uma editora por ‘ID’.
     *
     * @param id O ‘ID’ da editora a ser deletada.
     * @throws RuntimeException Se a editora não for encontrada.
     */
    public void deleteEditora(String id) {
        final var editora = this.editoraRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Editora não encontrado"));

        this.editoraRepository.delete(editora);
        redisPublisher.publish(RedisPublisher.OperationType.DELETE, editora);
    }
}
