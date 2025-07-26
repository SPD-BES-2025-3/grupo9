package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    /**
     * Obtém todos os livros.
     *
     * @return Uma lista de DTOs de resposta dos livros.
     */
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> getAllLivro() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.getAllLivro());
    }

    /**
     * Obtém um livro por ‘ID’.
     *
     * @param id O ‘ID’ de livro a ser obtida.
     * @return O livro encontrada.
     * @throws RuntimeException Se o livro não for encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getLivroById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.getLivroById(id));
    }

    /**
     * Obtém livro por título.
     *
     * @param titulo O título pelo qual os livros serão filtradas.
     * @return Uma lista de livro encontradas pelo título.
     */
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<LivroResponseDTO>> getLivroByTitulo(String titulo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.getLivroByTitulo(titulo));
    }

    /**
     * Cria um livro.
     *
     * @param livro DTO contendo os dados do novo livro.
     * @return O livro recém-criada, encapsulada em ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<LivroResponseDTO> create(@RequestBody LivroRequestDTO livro) {
        System.out.println("Entrou no createLivro");
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.createLivro(livro));
    }

    /**
     * Atualiza um livro.
     *
     * @param id  ‘ID’ do livro a ser atualizada.
     * @param livro DTO contendo os dados do livro.
     * @return O livro atualiza, encapsulada em ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> update(@PathVariable String id, @RequestBody LivroRequestDTO livro) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.updateLivro(id, livro));
    }

    /**
     * Delete um livro por ‘ID’.
     *
     * @param id O ‘ID’ do livro a ser deletada.
     * @return ResponseEntity sem corpo indicando que a livro foi deletada com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        this.livroService.deleteLivro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
