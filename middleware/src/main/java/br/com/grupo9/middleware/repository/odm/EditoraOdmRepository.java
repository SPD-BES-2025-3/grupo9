package br.com.grupo9.middleware.repository.odm;

import br.com.grupo9.middleware.model.odm.Editora_ODM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditoraOdmRepository extends MongoRepository<Editora_ODM, String> {

    /**
     * Busca editoras pelo nome.
     * @param nome O nome da editora.
     * @return Uma lista de editoras encontradas.
     */
    List<Editora_ODM> findByNome(String nome);
}