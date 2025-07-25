package br.ufg.inf.grupo9.biblioteca.controller;

import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioResponseDTO;
import br.ufg.inf.grupo9.biblioteca.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Obtém todas os usuario.
     *
     * @return Uma lista de DTOs de resposta dos usuarios.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuario() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.getAllUsuario());
    }

    /**
     * Obtém uma usuario upor ‘ID’.
     *
     * @param id O ‘ID’ da usuario a ser obtida.
     * @return A usuario encontrada, encapsulada em ResponseEntity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.getUsuarioById(id));
    }

    /**
     * Obtém usuario por nome.
     *
     * @param nome O nome pelo qual as usuarios serão filtradas.
     * @return Uma lista de usuarios encontradas pela nome, encapsulada em ResponseEntity.
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByNome(@PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.usuarioService.getUsuarioByNome(nome));
    }

    /**
     * Obtém usuarios por email.
     *
     * @param email O nome pelo qual os usuarios serão filtradas.
     * @return Uma lista de usuarios encontradas pela email, encapsulada em ResponseEntity.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.usuarioService.getUsuarioByEmail(email));
    }

    /**
     * Cria um usuario.
     *
     * @param dto DTO contendo os dados do novo usuario.
     * @return A usuario recém-criada, encapsulada em ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.usuarioService.createUsuario(dto));
    }

    /**
     * Atualiza uma usuario.
     *
     * @param id  ‘ID’ da usuario a ser atualizada.
     * @param dto DTO contendo os dados da usuario.
     * @return A usuario atualiza, encapsulada em ResponseEntity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable String id, @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.usuarioService.updateUsuario(id, dto));
    }

    /**
     * Delete uma usuario por ‘ID’.
     *
     * @param id O ‘ID’ da usuario a ser deletada.
     * @return ResponseEntity sem corpo indicando que a usuario foi deletada com sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable String id) {
        this.usuarioService.deleteUsuario(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}