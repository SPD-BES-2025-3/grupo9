package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.livro.LivroResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.LivroService;
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
@RequestMapping("/rest/livro")
@RequiredArgsConstructor
@Tag(name = "Livro", description = "Endpoints para gerenciamento de livros")
public class LivroController {

    private final LivroService livroService;

    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista com todos os livros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> getAllLivro() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.getAllLivro());
    }

    @Operation(summary = "Buscar livro por ID", description = "Retorna os dados de um livro com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getLivroById(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.getLivroById(id));
    }

    @Operation(summary = "Buscar livros por título", description = "Retorna uma lista de livros filtrados pelo título.")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<LivroResponseDTO>> getLivroByTitulo(
            @Parameter(description = "Título do livro", required = true)
            @PathVariable String titulo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.getLivroByTitulo(titulo));
    }

    @Operation(summary = "Criar novo livro", description = "Cria um novo livro com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> create(
            @RequestBody LivroRequestDTO livro) {
        System.out.println("Entrou no createLivro");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.livroService.createLivro(livro));
    }

    @Operation(summary = "Atualizar livro", description = "Atualiza os dados de um livro existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> update(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable String id,
            @RequestBody LivroRequestDTO livro) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.livroService.updateLivro(id, livro));
    }

    @Operation(summary = "Deletar livro", description = "Remove um livro com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable String id) {
        this.livroService.deleteLivro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
