package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Autor_ODM;
import br.com.grupo9.middleware.model.orm.Autor_ORM;
import org.springframework.stereotype.Component;

@Component
public class AutorTransformer {

    /**
     * Converte um objeto Autor do modelo relacional (ORM) para o modelo de documento (ODM).
     * @param orm O objeto Autor vindo do banco de dados SQLite.
     * @return Um novo objeto Autor_ODM pronto para ser salvo no MongoDB.
     */
    public Autor_ODM toOdm(Autor_ORM orm) {
        if (orm == null) {
            return null;
        }

        Autor_ODM odm = new Autor_ODM();
        // O ID do ODM não é definido aqui; o SyncService decidirá se é uma criação ou atualização.
        odm.setNome(orm.getNome());
        odm.setNacionalidade(orm.getNacionalidade());
        return odm;
    }
}