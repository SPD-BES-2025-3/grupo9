package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.usuario.UsuarioResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import org.springframework.stereotype.Component;

/**
 * A classe UsuarioAdapter é responsável por realizar a conversão entre diferentes representações
 * de objetos, como UsuarioRequestDTO, UsuarioResponseDTO e Usuario.
 */
@Component
public class UsuarioAdapter {

    /**
     * Converte um objeto UsuarioRequestDTO para um objeto Usuario.
     *
     * @param usuarioRequestDTO O objeto DTO a ser convertido.
     * @return Uma objeto Usuario convertido.
     */
    public Usuario toUsuario(UsuarioRequestDTO usuarioRequestDTO) {
        return Usuario.builder()
                .nome(usuarioRequestDTO.getNome())
                .email(usuarioRequestDTO.getEmail())
                .senha(usuarioRequestDTO.getSenha())
                .telefone(usuarioRequestDTO.getTelefone())
                .build();
    }

    /**
     * Converte um objeto Usuario para um objeto UsuarioResponseDTO.
     *
     * @param usuario O objeto Usuario a ser convertido.
     * @return Um DTO convertido.
     */
    public UsuarioResponseDTO toUsuarioDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .build();
    }
}