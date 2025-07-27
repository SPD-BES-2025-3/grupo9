package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Editora_ODM;
import br.com.grupo9.middleware.model.orm.Editora_ORM;
import org.springframework.stereotype.Component;

@Component
public class EditoraTransformer {

    /**
     * Converte um objeto Editora do modelo relacional (ORM) para o modelo de documento (ODM).
     * @param orm O objeto Editora vindo do banco de dados SQLite.
     * @return Um novo objeto Editora_ODM pronto para ser salvo no MongoDB.
     */
    public Editora_ODM toOdm(Editora_ORM orm) {
        if (orm == null) {
            return null;
        }

        Editora_ODM odm = new Editora_ODM();
        odm.setNome(orm.getNome());
        odm.setEndereco(orm.getEndereco());
        odm.setTelefone(orm.getTelefone());
        return odm;
    }
}