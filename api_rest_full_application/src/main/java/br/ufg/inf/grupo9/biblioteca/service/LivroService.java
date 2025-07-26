package br.ufg.inf.grupo9.biblioteca.service;

import br.ufg.inf.grupo9.biblioteca.adapter.LivroAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import br.ufg.inf.grupo9.biblioteca.model.Livro;
import br.ufg.inf.grupo9.biblioteca.repository.AutorRepository;
import br.ufg.inf.grupo9.biblioteca.repository.EditoraRepository;
import br.ufg.inf.grupo9.biblioteca.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroAdapter livroAdapter;
    private final EditoraRepository editoraRepository;
    private final AutorRepository autorRepository;

    /**
     * Obtém todos os livros.
     *
     * @return Uma lista de DTOs de resposta dos livros.
     */
    public List<LivroResponseDTO> getAllLivro() {
        return livroRepository.findAll()
                .stream()
                .map(livroAdapter::toLivroDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém um livro por ‘ID’.
     *
     * @param id O ‘ID’ do livro a ser obtida.
     * @return A livro encontrada.
     * @throws RuntimeException Se o livro não for encontrada.
     */
    public LivroResponseDTO getLivroById(String id) {
        final var livro = this.livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado."));

        return livroAdapter.toLivroDTO(livro);
    }

    /**
     * Obtém livro por título.
     *
     * @param titulo O título pelo qual o livro serão filtradas.
     * @return Uma lista de livro encontradas pela titulo, encapsulada em ResponseEntity.
     */
    public List<LivroResponseDTO> getLivroByTitulo(String titulo) {
        return this.livroRepository.findByTitulo(titulo)
                .stream()
                .map(livroAdapter::toLivroDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um livro.
     *
     * @param dto DTO contendo os dados da nova livro.
     * @return O livro recém-criada, encapsulada em ResponseEntity.
     */
    public LivroResponseDTO createLivro(LivroRequestDTO livroRequestDTO) {
        System.out.println("ID Editora: " + livroRequestDTO.getIdEditora());

        Editora editora = editoraRepository.findById(livroRequestDTO.getIdEditora())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Editora não encontrado"));

        System.out.println("ID Autor: " + livroRequestDTO.getIdAutor());

        Autor autor = autorRepository.findById(livroRequestDTO.getIdAutor())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Autor não encontrado"));

        Livro livro = livroAdapter.toLivro(livroRequestDTO, autor, editora);
        Livro livroSalvo = livroRepository.save(livro);

        return livroAdapter.toLivroDTO(livroSalvo);
    }

    public LivroResponseDTO updateLivro(String id, LivroRequestDTO dto) {
        Livro livroFound = livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado"));

        Editora editora = editoraRepository.findById(dto.getIdEditora())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Editora não encontrado"));

        Autor autor = autorRepository.findById(dto.getIdAutor())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Autor não encontrado"));

        Livro livroAtualizado =  livroAdapter.toLivro(dto, autor, editora);
        livroAtualizado.setId(livroFound.getId());
        Livro livroSalvo = livroRepository.save(livroAtualizado);

        return livroAdapter.toLivroDTO(livroSalvo);
    }

    /**
     * Delete um livro por ‘ID’.
     *
     * @param id O ‘ID’ do livro a ser deletada.
     * @return ResponseEntity sem corpo indicando que a livro foi deletada com sucesso.
     */
    public void deleteLivro(String id) {
        final var livro = this.livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Livro não encontrado"));

        this.livroRepository.delete(livro);
    }
}
