package br.com.grupo9.middleware.repository.odm;

import br.com.grupo9.middleware.model.odm.Usuario_ODM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioOdmRepository extends MongoRepository<Usuario_ODM, String> {

    /**
     * Busca usuários pelo email, que é um campo único.
     * @param email O email do usuário.
     * @return Uma lista de usuários encontrados (idealmente, um ou nenhum).
     */
    List<Usuario_ODM> findByEmail(String email);
}