package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.AutorAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorAdapter autorAdapter;

    /**
     * Obtém todos os autores.
     *
     * @return Uma lista de DTOs de resposta dos autores.
     */
    public List<AutorResponseDTO> getAllAutor() {
        return autorRepository.findAll()
                .stream()
                .map(autorAdapter::toAutorDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém um autor por ‘ID’.
     *
     * @param id O ID do autor a ser obtido.
     * @return A autor encontrada.
     * @throws RuntimeException Se o autor não for encontrada.
     */
    public AutorResponseDTO getAutorById(String id) {
        final var autorFounded = this.autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return this.autorAdapter.toAutorDTO(autorFounded);
    }

    /**
     * Obtém autor por nacionalidade.
     *
     * @param nacionalidade A nacionalidade pela qual as autor serão filtradas.
     * @return Uma lista de autor encontradas pela nacionalidade.
     */
    public List<AutorResponseDTO> getAutorByNacionalidade(String nacionalidade) {
        return this.autorRepository.findAllByNacionalidade(nacionalidade)
                .stream()
                .map(autorAdapter::toAutorDTO)
                .collect(Collectors.toList());
    }

    public List<AutorResponseDTO> getAutorByNome(String nome) {
        return this.autorRepository.findByNome(nome)
                .stream()
                .map(autorAdapter::toAutorDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo autor.
     *
     * @param autorRequestDTO DTO contendo os dados do novo autor.
     * @return O autor recém-criado como DTO de resposta.
     */
    public AutorResponseDTO create(AutorRequestDTO autorRequestDTO) {
        final var autor = this.autorAdapter.toAutor(autorRequestDTO);
        final var autorSalvo = this.autorRepository.save(autor);

        return this.autorAdapter.toAutorDTO(autorSalvo);
    }

    /**
     * Atualiza os dados de um autor existente.
     *
     * @param autorRequestDTO DTO com os novos dados do autor.
     * @param id              ‘ID’ do autor a ser atualizado.
     * @return DTO com os dados atualizados do autor.
     */
    public AutorResponseDTO update(AutorRequestDTO autorRequestDTO, String id) {
        final var autorExistente = this.autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        final var autorAtualizado = this.autorAdapter.toAutor(autorRequestDTO);
        autorAtualizado.setId(autorExistente.getId());

        final var autorSalvo = this.autorRepository.save(autorAtualizado);

        return this.autorAdapter.toAutorDTO(autorSalvo);
    }

    /**
     * Delete um autor por ‘ID’.
     *
     * @param id O ‘ID’ do autor a ser deletada.
     * @throws RuntimeException Se o autor não for encontrada.
     */
    public void delete(String id) {
        var autor = this.autorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        this.autorRepository.delete(autor);
    }
}