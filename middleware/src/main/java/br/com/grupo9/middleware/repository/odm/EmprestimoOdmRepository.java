package br.com.grupo9.middleware.repository.odm;

import br.com.grupo9.middleware.model.odm.Emprestimo_ODM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoOdmRepository extends MongoRepository<Emprestimo_ODM, String> {

    /**
     * Busca empréstimos pelo ID do usuário referenciado.
     * Spring Data MongoDB consegue criar queries baseadas em campos de objetos referenciados (@DBRef).
     * @param usuarioId O ID do documento do usuário no MongoDB.
     * @return Uma lista de empréstimos associados ao usuário.
     */
    List<Emprestimo_ODM> findByUsuario_Id(String usuarioId);

    /**
     * Busca empréstimos pelo ID do livro referenciado.
     * @param livroId O ID do documento do livro no MongoDB.
     * @return Uma lista de empréstimos associados ao livro.
     */
    List<Emprestimo_ODM> findByLivro_Id(String livroId);
}