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
     * Retorna todos os usuários cadastrados.
     *
     * @return Lista de usuários.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAllUsuario() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.getAllUsuario());
    }

    /**
     * Busca um usuário pelo ID.
     *
     * @param id ID do usuário.
     * @return Usuário correspondente ao ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.getUsuarioById(id));
    }

    /**
     * Busca usuários pelo nome.
     *
     * @param nome Nome do usuário.
     * @return Lista de usuários com o nome fornecido.
     */
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByNome(@PathVariable String nome) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.getUsuarioByNome(nome));
    }

    /**
     * Busca usuários pelo e-mail.
     *
     * @param email E-mail do usuário.
     * @return Lista de usuários com o e-mail fornecido.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<List<UsuarioResponseDTO>> findByEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.getUsuarioByEmail(email));
    }

    /**
     * Cria um novo usuário.
     *
     * @param dto Dados do novo usuário.
     * @return Usuário criado.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.createUsuario(dto));
    }

    /**
     * Atualiza os dados de um usuário.
     *
     * @param id  ID do usuário.
     * @param dto Novos dados.
     * @return Usuário atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@PathVariable String id, @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.updateUsuario(id, dto));
    }

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id ID do usuário a ser removido.
     * @return Resposta sem conteúdo indicando sucesso.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable String id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
