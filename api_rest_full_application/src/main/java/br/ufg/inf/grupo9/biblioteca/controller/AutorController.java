package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/autor")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;

    /**
     * Obtém todos os autores.
     *
     * @return Uma lista de DTOs de resposta dos autores.
     */
    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> getAllAutor() {
        return ResponseEntity.ok(this.autorService.getAllAutor());
    }

    /**
     * Obtém um autor por ‘ID’.
     *
     * @param id O ID do autor a ser obtida.
     * @return O autor encontrada.
     * @throws RuntimeException Se o autor não for encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> getAutorById(@PathVariable String id) {
        return ResponseEntity.ok(this.autorService.getAutorById(id));
    }

    /**
     * Obtém autores por nacionalidade.
     *
     * @param nacionalidade A nacionalidade pela qual os autores serão filtrados.
     * @return Uma lista de autores encontrados com a nacionalidade fornecida.
     */
    @GetMapping("nacionalidade/{nacionalidade}")
    public ResponseEntity<List<AutorResponseDTO>> getAutorByNacionalidade(@PathVariable String nacionalidade) {
        return ResponseEntity.ok(this.autorService.getAutorByNacionalidade(nacionalidade));
    }

    /**
     * Obtém autores por nome.
     *
     * @param nome O nome pelo qual os autores serão filtradas.
     * @return Uma lista de autores encontradas pelo nome.
     */
    @GetMapping("nome/{nome}")
    public ResponseEntity<List<AutorResponseDTO>> getAutorByNome(String nome) {
        return ResponseEntity.ok(this.autorService.getAutorByNome(nome));
    }

    /**
     * Cria um novo autor.
     *
     * @param autorRequestDTO DTO contendo os dados do novo autor.
     * @return O autor recém-criado.
     */
    @PostMapping
    public ResponseEntity<AutorResponseDTO> create(@RequestBody AutorRequestDTO autorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.autorService.create(autorRequestDTO));
    }

    /**
     * Atualiza um autor.
     *
     * @param id              O ‘ID’ do autor a ser atualizado.
     * @param autorRequestDTO DTO contendo os novos dados de autor.
     * @return O autor atualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> update(@PathVariable String id, @RequestBody AutorRequestDTO autorRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.autorService.update(autorRequestDTO, id));
    }

    /**
     * Delete um autor por ‘ID’.
     *
     * @param id O ‘ID’ do autor a ser deletado.
     * @throws RuntimeException Se o autor não for encontrado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(String id) {
        this.autorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
