package br.com.grupo9.middleware.repository.odm;

import br.com.grupo9.middleware.model.odm.Autor_ODM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorOdmRepository extends MongoRepository<Autor_ODM, String> {

    /**
     * Busca autores pelo nome. O nome é a chave natural que usaremos
     * para verificar a existência de um autor durante a sincronização.
     * @param nome O nome do autor.
     * @return Uma lista de autores encontrados (pode ser vazia).
     */
    List<Autor_ODM> findByNome(String nome);
}