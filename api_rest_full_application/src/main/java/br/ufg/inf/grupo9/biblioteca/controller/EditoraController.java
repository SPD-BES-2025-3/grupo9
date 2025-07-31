package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.EditoraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/editora")
@RequiredArgsConstructor
@Tag(name = "Editora", description = "Endpoints para gerenciamento de editoras")
public class EditoraController {

    private final EditoraService editoraService;

    @Operation(summary = "Listar todas as editoras", description = "Retorna uma lista com todas as editoras cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de editoras retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EditoraResponseDTO>> getAllEditora() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.getAllEditora());
    }

    @Operation(summary = "Buscar editora por ID", description = "Retorna os dados de uma editora com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora encontrada"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> getEditoraById(
            @Parameter(description = "ID da editora", required = true)
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.getEditoraById(id));
    }

    @Operation(summary = "Buscar editoras por nome", description = "Retorna uma lista de editoras filtradas por nome.")
    @ApiResponse(responseCode = "200", description = "Lista de editoras retornada com sucesso")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<EditoraResponseDTO>> findByNome(
            @Parameter(description = "Nome da editora", required = true)
            @PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.getEditoraByNome(nome));
    }

    @Operation(summary = "Criar uma nova editora", description = "Cria uma nova editora com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Editora criada com sucesso")
    @PostMapping
    public ResponseEntity<EditoraResponseDTO> addEditora(
            @RequestBody EditoraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.editoraService.createEditora(dto));
    }

    @Operation(summary = "Atualizar uma editora", description = "Atualiza os dados de uma editora existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> updateEditora(
            @Parameter(description = "ID da editora", required = true)
            @PathVariable String id,
            @RequestBody EditoraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.editoraService.updateEditora(id, dto));
    }

    @Operation(summary = "Deletar uma editora", description = "Remove uma editora com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Editora deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEditora(
            @Parameter(description = "ID da editora", required = true)
            @PathVariable String id) {
        this.editoraService.deleteEditora(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
