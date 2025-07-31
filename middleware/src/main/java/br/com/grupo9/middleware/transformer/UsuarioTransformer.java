package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Usuario_ODM;
import br.com.grupo9.middleware.model.orm.Usuario_ORM;
import org.springframework.stereotype.Component;

@Component
public class UsuarioTransformer {

    public Usuario_ODM toOdm(Usuario_ORM orm) {
        if (orm == null) return null;
        Usuario_ODM odm = new Usuario_ODM();
        odm.setNome(orm.getNome());
        odm.setEmail(orm.getEmail());
        odm.setSenha(orm.getSenha());
        odm.setTelefone(orm.getTelefone());
        return odm;
    }

    public Usuario_ORM toOrm(Usuario_ODM odm) {
        if (odm == null) return null;
        Usuario_ORM orm = new Usuario_ORM();
        orm.setNome(odm.getNome());
        orm.setEmail(odm.getEmail());
        orm.setSenha(odm.getSenha());
        orm.setTelefone(odm.getTelefone());
        return orm;
    }
}
