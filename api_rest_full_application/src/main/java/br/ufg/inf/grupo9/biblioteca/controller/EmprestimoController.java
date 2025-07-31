package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/emprestimo")
@RequiredArgsConstructor
@Tag(name = "Empréstimo", description = "Endpoints para gerenciamento de empréstimos de livros")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @Operation(summary = "Listar todos os empréstimos", description = "Retorna uma lista com todos os empréstimos registrados.")
    @ApiResponse(responseCode = "200", description = "Lista de empréstimos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> getAllEmprestimo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.emprestimoService.getAllEmprestimo());
    }

    @Operation(summary = "Buscar empréstimo por ID", description = "Retorna os dados de um empréstimo específico com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empréstimo encontrado"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> getEmprestimoById(
            @Parameter(description = "ID do empréstimo", required = true)
            @PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.emprestimoService.getEmprestimoById(id));
    }

    @Operation(summary = "Buscar empréstimos por data do empréstimo", description = "Retorna empréstimos realizados na data especificada.")
    @ApiResponse(responseCode = "200", description = "Empréstimos retornados com sucesso")
    @GetMapping("/dataEmprestimo/{dataEmprestimo}")
    public ResponseEntity<List<EmprestimoResponseDTO>> getEmprestimoByDataEmprestimo(
            @Parameter(description = "Data do empréstimo (formato: yyyy-MM-dd)", required = true)
            @PathVariable Date dataEmprestimo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.getEmprestimoByDataEmprestimo(dataEmprestimo));
    }

    @Operation(summary = "Buscar empréstimos por ID do livro", description = "Retorna todos os empréstimos associados a um determinado livro.")
    @ApiResponse(responseCode = "200", description = "Empréstimos retornados com sucesso")
    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<EmprestimoResponseDTO>> getEmprestimosPorLivro(
            @Parameter(description = "ID do livro", required = true)
            @PathVariable String livroId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.getEmprestimosPorLivro(livroId));
    }

    @Operation(summary = "Criar um novo empréstimo", description = "Registra um novo empréstimo com os dados fornecidos.")
    @ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso")
    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> create(
            @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emprestimoService.createEmprestimo(emprestimoRequestDTO));
    }

    @Operation(summary = "Atualizar um empréstimo", description = "Atualiza os dados de um empréstimo existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> update(
            @Parameter(description = "ID do empréstimo", required = true)
            @PathVariable String id,
            @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.updateEmprestimo(id, emprestimoRequestDTO));
    }

    @Operation(summary = "Deletar um empréstimo", description = "Remove um empréstimo com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Empréstimo deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "ID do empréstimo", required = true)
            @PathVariable String id) {
        this.emprestimoService.deleteEmprestimo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
