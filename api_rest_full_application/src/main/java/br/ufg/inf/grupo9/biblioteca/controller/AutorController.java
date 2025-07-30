package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/autor")
@RequiredArgsConstructor
@Tag(name = "Autor", description = "Endpoints para gerenciamento de autores")
public class AutorController {

    private final AutorService autorService;

    @Operation(summary = "Listar todos os autores", description = "Retorna uma lista com todos os autores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> getAllAutor() {
        return ResponseEntity.ok(this.autorService.getAllAutor());
    }

    @Operation(summary = "Buscar autor por ID", description = "Retorna os dados de um autor a partir do seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> getAutorById(
            @Parameter(description = "ID do autor", required = true)
            @PathVariable String id) {
        return ResponseEntity.ok(this.autorService.getAutorById(id));
    }

    @Operation(summary = "Buscar autores por nacionalidade", description = "Retorna uma lista de autores filtrados por nacionalidade.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/nacionalidade/{nacionalidade}")
    public ResponseEntity<List<AutorResponseDTO>> getAutorByNacionalidade(
            @Parameter(description = "Nacionalidade dos autores", required = true)
            @PathVariable String nacionalidade) {
        return ResponseEntity.ok(this.autorService.getAutorByNacionalidade(nacionalidade));
    }

    @Operation(summary = "Buscar autores por nome", description = "Retorna uma lista de autores filtrados por nome.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<AutorResponseDTO>> getAutorByNome(
            @Parameter(description = "Nome do autor", required = true)
            @PathVariable String nome) {
        return ResponseEntity.ok(this.autorService.getAutorByNome(nome));
    }

    @Operation(summary = "Criar novo autor", description = "Cria um novo autor com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso")
    @PostMapping
    public ResponseEntity<AutorResponseDTO> create(
            @RequestBody AutorRequestDTO autorRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.autorService.create(autorRequestDTO));
    }

    @Operation(summary = "Atualizar autor", description = "Atualiza os dados de um autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> update(
            @Parameter(description = "ID do autor", required = true)
            @PathVariable String id,
            @RequestBody AutorRequestDTO autorRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.autorService.update(autorRequestDTO, id));
    }

    @Operation(summary = "Deletar autor", description = "Deleta um autor com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID do autor", required = true)
            @PathVariable String id) {
        this.autorService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
