package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Autor_ODM;
import br.com.grupo9.middleware.model.orm.Autor_ORM;
import org.springframework.stereotype.Component;

@Component
public class AutorTransformer {

    public Autor_ODM toOdm(Autor_ORM orm) {
        if (orm == null) return null;
        return Autor_ODM.builder()
                // O ID do ODM será gerenciado pelo listener/serviço
                .nome(orm.getNome())
                .nacionalidade(orm.getNacionalidade())
                .build();
    }
}