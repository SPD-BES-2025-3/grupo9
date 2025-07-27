package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Usuario_ODM;
import br.com.grupo9.middleware.model.orm.Usuario_ORM;
import org.springframework.stereotype.Component;

@Component
public class UsuarioTransformer {

    /**
     * Converte um objeto Usuario do modelo relacional (ORM) para o modelo de documento (ODM).
     * @param orm O objeto Usuario vindo do banco de dados SQLite.
     * @return Um novo objeto Usuario_ODM pronto para ser salvo no MongoDB.
     */
    public Usuario_ODM toOdm(Usuario_ORM orm) {
        if (orm == null) {
            return null;
        }

        Usuario_ODM odm = new Usuario_ODM();
        odm.setNome(orm.getNome());
        odm.setEmail(orm.getEmail());
        odm.setSenha(orm.getSenha());
        odm.setTelefone(orm.getTelefone());
        return odm;
    }
}