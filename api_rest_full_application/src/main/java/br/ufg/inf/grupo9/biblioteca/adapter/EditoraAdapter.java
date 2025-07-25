package br.ufg.inf.grupo9.biblioteca.adapter;

import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraRequestDTO;
import br.ufg.inf.grupo9.biblioteca.dto.editora.EditoraResponseDTO;
import br.ufg.inf.grupo9.biblioteca.model.Editora;
import org.springframework.stereotype.Component;

/**
 * A classe EditoraAdapter é responsável por realizar a conversão entre diferentes representações
 * de objetos, como EditoraRequestDTO, EditoraResponseDTO e Editora.
 */
@Component
public class EditoraAdapter {

    /**
     * Converte um objeto EditoraRequestDTO para um objeto Editora.
     *
     * @param editoraDTO O objeto DTO a ser convertido.
     * @return Um objeto Editora convertido.
     */
    public Editora toEditora(EditoraRequestDTO editoraDTO) {
        return Editora.builder()
                .nome(editoraDTO.getNome())
                .endereco(editoraDTO.getEndereco())
                .telefone(editoraDTO.getTelefone())
                .build();
    }

    /**
     * Converte um objeto Editora para um objeto EditoraResponseDTO.
     *
     * @param editora O objeto Editora a ser convertido.
     * @return Um DTO convertido.
     */
    public EditoraResponseDTO toEditoraDTO(Editora editora) {
        return EditoraResponseDTO.builder()
                .id(editora.getId())
                .nome(editora.getNome())
                .endereco(editora.getEndereco())
                .telefone(editora.getTelefone())
                .build();
    }
}
