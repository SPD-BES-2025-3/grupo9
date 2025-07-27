package br.com.grupo9.middleware.model.odm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "livros")
public class Livro_ODM {

    @Id
    private String id;

    private String titulo;

    private String isbn;

    private int anoPublicacao;

    private double preco;

    @DBRef
    private Editora_ODM editoraODM;

    @DBRef
    private Autor_ODM autorODM;
}