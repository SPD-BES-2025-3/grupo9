package br.com.grupo9.middleware.repository.odm;

import br.com.grupo9.middleware.model.odm.Livro_ODM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroOdmRepository extends MongoRepository<Livro_ODM, String> {

    /**
     * Busca livros pelo título.
     * @param titulo O título do livro.
     * @return Uma lista de livros encontrados.
     */
    List<Livro_ODM> findByTitulo(String titulo);
}