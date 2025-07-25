package br.ufg.inf.grupo9.biblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuario")
public class Usuario {

    @Id
    private String id;

    private String nome;

    private String email;

    private String senha;

    private String telefone;
}
