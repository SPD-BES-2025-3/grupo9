package br.ufg.inf.grupo9.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.ufg.inf.grupo9.biblioteca.adapter.UsuarioAdapter;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioResponseDTO;
import br.ufg.inf.grupo9.biblioteca.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAdapter usuarioAdapter;
    private final RedisPublisher redisPublisher;

    /**
     * Obtém todas os usuarios
     *
     * @return Uma lista de DTOs de resposta das usuarios.
     */
    public List<UsuarioResponseDTO> getAllUsuario() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioAdapter::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém uma usuario por ‘ID’.
     *
     * @param id O ‘ID’ da usuario a ser obtida.
     * @return A usuario encontrada.
     * @throws RuntimeException Se a usuario não for encontrada.
     */
    public UsuarioResponseDTO getUsuarioById(String id) {
        final var  usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        return usuarioAdapter.toUsuarioDTO(usuario);
    }

    /**
     * Obtém usuario por nome.
     *
     * @param nome O nome pelo qual os usuarios serão filtradas.
     * @return Uma lista de usuarios encontradas pelo nome.
     */
    public List<UsuarioResponseDTO> getUsuarioByNome(String nome) {
        return this.usuarioRepository.findByNome(nome)
                .stream()
                .map(usuarioAdapter::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtém usuario por email.
     *
     * @param email O email pelo qual os usuarios serão filtradas.
     * @return Uma lista de usuarios encontradas pelo email.
     */
    public List<UsuarioResponseDTO> getUsuarioByEmail(String email) {
        return this.usuarioRepository.findByEmail(email)
                .stream()
                .map(usuarioAdapter::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um usuario.
     *
     * @param usuarioRequestDTO DTO contendo os dados da novo usuario.
     * @return A usuario recém-criada.
     */
    public UsuarioResponseDTO createUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        final var usuario = this.usuarioAdapter.toUsuario(usuarioRequestDTO);
        final var usuarioSalva = this.usuarioRepository.save(usuario);
        redisPublisher.publish(RedisPublisher.OperationType.CREATE, usuarioSalvo);

        return this.usuarioAdapter.toUsuarioDTO(usuarioSalva);
    }

    /**
     * Atualiza um usuario.
     *
     * @param id                O ‘ID’ da usuario a ser atualizada.
     * @param usuarioRequestDTO DTO contendo os novos dados da usuario.
     * @return A ususario atualizada.
     */
    public UsuarioResponseDTO updateUsuario(String id, UsuarioRequestDTO usuarioRequestDTO) {
        final var usuarioExistente = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        final var usuarioAtualizada = this.usuarioAdapter.toUsuario(usuarioRequestDTO);
        usuarioAtualizada.setId(usuarioExistente.getId());

        final var editoraSalva = this.usuarioRepository.save(usuarioAtualizada);
        redisPublisher.publish(RedisPublisher.OperationType.UPDATE, usuarioSalvo);
        return this.usuarioAdapter.toUsuarioDTO(editoraSalva);
    }

    /**
     * Delete uma usuario por ‘ID’.
     *
     * @param id O ‘ID’ da usuario a ser deletada.
     * @throws RuntimeException Se a usuario não for encontrada.
     */
    public void deleteUsuario(String id) {
        final var usuario = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario não encontrado"));

        this.usuarioRepository.delete(usuario);
        redisPublisher.publish(RedisPublisher.OperationType.DELETE, usuario);
    }
}
