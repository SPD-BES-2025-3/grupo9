package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Autor_ODM;
import br.com.grupo9.middleware.model.orm.Autor_ORM;
import org.springframework.stereotype.Component;

@Component
public class AutorTransformer {

    public Autor_ODM toOdm(Autor_ORM orm) {
        if (orm == null) return null;
        Autor_ODM odm = new Autor_ODM();
        odm.setNome(orm.getNome());
        odm.setNacionalidade(orm.getNacionalidade());
        return odm;
    }

    public Autor_ORM toOrm(Autor_ODM odm) {
        if (odm == null) return null;
        Autor_ORM orm = new Autor_ORM();
        orm.setNome(odm.getNome());
        orm.setNacionalidade(odm.getNacionalidade());
        return orm;
    }
}
