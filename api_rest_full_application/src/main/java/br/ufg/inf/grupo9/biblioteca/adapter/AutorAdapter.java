package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.autor.AutorResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Autor;
import org.springframework.stereotype.Component;

/**
 * A classe AutorAdapter é responsável por realizar a conversão entre diferentes representações
 * de objetos, como AutorRequestDTO, AutorResponseDTO e Autor.
 */
@Component
public class AutorAdapter {

    /**
     * Converte um objeto AutorRequestDTO para um objeto Autor.
     *
     * @param autorDTO O objeto DTO a ser convertido.
     * @return Uma objeto Autor convertido.
     */
    public Autor toAutor(AutorRequestDTO autorDTO) {
        return Autor.builder()
                .nome(autorDTO.getNome())
                .nacionalidade(autorDTO.getNacionalidade())
                .build();
    }

    /**
     * Converte um objeto Autor para um objeto AutorResponseDTO.
     *
     * @param autor O objeto Autor a ser convertido.
     * @return Um DTO convertido.
     */
    public AutorResponseDTO toAutorDTO(Autor autor) {
        return AutorResponseDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .nacionalidade(autor.getNacionalidade())
                .build();
    }
}