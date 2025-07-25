package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.EditoraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/editora")
@RequiredArgsConstructor
public class EditoraController {

    private final EditoraService editoraService;

    /**
     * Obtém todas as editoras.
     *
     * @return Uma lista de DTOs de resposta das editoras.
     */
    @GetMapping
    public ResponseEntity<List<EditoraResponseDTO>> getAllEditora() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.getAllEditora());
    }

    /**
     * Obtém uma editora por ‘ID’.
     *
     * @param id O ID da editora a ser obtida.
     * @return A editora encontrada, encapsulada em ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> getEditoraById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.getEditoraById(id));
    }

    /**
     * Obtém editora por nome.
     *
     * @param nome O nome pelo qual as editoras serão filtradas.
     * @return Uma lista de editoras encontradas pela nome, encapsulada em ResponseEntity.
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<EditoraResponseDTO>> findByNome(@PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.getEditoraByNome(nome));
    }

    /**
     * Cria uma editora.
     *
     * @param dto DTO contendo os dados da nova editora.
     * @return A editora recém-criada, encapsulada em ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<EditoraResponseDTO> addEditora(@RequestBody EditoraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.editoraService.createEditora(dto));
    }

    /**
     * Atualiza uma editora.
     *
     * @param id  ‘ID’ da editora a ser atualizada.
     * @param dto DTO contendo os dados da editora.
     * @return A editora atualiza, encapsulada em ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> updateEditora(@PathVariable String id, @RequestBody EditoraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.updateEditora(id, dto));
    }

    /**
     * Delete uma editora por ‘ID’.
     *
     * @param id O ‘ID’ da editora a ser deletada.
     * @return ResponseEntity sem corpo indicando que a editora foi deletada com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEditora(@PathVariable String id) {
        this.editoraService.deleteEditora(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}