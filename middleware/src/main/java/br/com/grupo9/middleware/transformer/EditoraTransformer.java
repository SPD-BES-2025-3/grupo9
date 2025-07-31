package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Editora_ODM;
import br.com.grupo9.middleware.model.orm.Editora_ORM;
import org.springframework.stereotype.Component;

@Component
public class EditoraTransformer {

    public Editora_ODM toOdm(Editora_ORM orm) {
        if (orm == null) return null;
        Editora_ODM odm = new Editora_ODM();
        odm.setNome(orm.getNome());
        odm.setEndereco(orm.getEndereco());
        odm.setTelefone(orm.getTelefone());
        return odm;
    }

    public Editora_ORM toOrm(Editora_ODM odm) {
        if (odm == null) return null;
        Editora_ORM orm = new Editora_ORM();
        orm.setNome(odm.getNome());
        orm.setEndereco(odm.getEndereco());
        orm.setTelefone(odm.getTelefone());
        return orm;
    }
}
