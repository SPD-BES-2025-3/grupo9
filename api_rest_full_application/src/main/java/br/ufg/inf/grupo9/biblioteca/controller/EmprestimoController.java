package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.emprestimo.EmprestimoResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/emprestimo")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    /**
     * Obtém todas os emprestimos.
     *
     * @return Uma lista de DTOs de resposta dos emprestimos.
     */
    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> getAllEmprestimo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.emprestimoService.getAllEmprestimo());
    }

    /**
     * Obtém uma editora por ‘ID’.
     *
     * @param id O ‘ID’ de emprestimo a ser obtida.
     * @return O emprestimo encontrada.
     * @throws RuntimeException Se o emprestimo não for encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> getEmprestimoById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.emprestimoService.getEmprestimoById(id));
    }

    /**
     * Obtém emprestimos por dataEmprestimo.
     *
     * @param dataEmprestimo A dataEmprestimo pelo qual os emprestimos serão filtradas.
     * @return Uma lista de emprestimos encontradas pelo dataEmprestimo.
     */
    @GetMapping("/dataEmprestimo/{dataEmprestimo}")
    public ResponseEntity<List<EmprestimoResponseDTO>> getEmprestimoByDataEmprestimo(@PathVariable Date dataEmprestimo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.getEmprestimoByDataEmprestimo(dataEmprestimo));
    }

    /**
     * Obtém emprestimos por livroId.
     *
     * @param livroId O livroId pelo qual os emprestimos serão filtradas.
     * @return Uma lista de emprestimos encontradas pelo livroId.
     */
    @GetMapping("/livro/{livroId}")
    public ResponseEntity<List<EmprestimoResponseDTO>> getEmprestimosPorLivro(@PathVariable String livroId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.getEmprestimosPorLivro(livroId));
    }

    /**
     * Cria um emprestimo.
     *
     * @param emprestimoRequestDTO DTO contendo os dados do novo emprestimo.
     * @return A emprestimo recém-criada, encapsulada em ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<EmprestimoResponseDTO> create(@RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emprestimoService.createEmprestimo(emprestimoRequestDTO));
    }

    /**
     * Atualiza um emprestimo.
     *
     * @param id  ‘ID’ do emprestimo a ser atualizada.
     * @param emprestimoRequestDTO DTO contendo os dados do emprestimo.
     * @return O emprestimo atualiza, encapsulada em ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoResponseDTO> update(@PathVariable String id, @RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(emprestimoService.updateEmprestimo(id, emprestimoRequestDTO));
    }

    /**
     * Delete um emprestimo por ‘ID’.
     *
     * @param id O ‘ID’ da emprestimo a ser deletada.
     * @return ResponseEntity sem corpo indicando que a emprestimo foi deletada com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        this.emprestimoService.deleteEmprestimo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
