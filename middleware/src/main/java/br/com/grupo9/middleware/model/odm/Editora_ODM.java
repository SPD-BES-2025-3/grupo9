package br.com.grupo9.middleware.model.odm;

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
@Document(collection = "editora")
public class Editora_ODM {

    @Id
    private String id;

    private String nome;

    private String endereco;

    private String telefone;
}
