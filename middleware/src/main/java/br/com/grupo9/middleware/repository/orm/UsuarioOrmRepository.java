package br.com.grupo9.middleware.repository.orm;

import br.com.grupo9.middleware.model.orm.Usuario_ORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioOrmRepository extends JpaRepository<Usuario_ORM, Integer> {

    /**
     * Busca o primeiro usuário encontrado com o email fornecido.
     * @param email O email do usuário a ser buscado.
     * @return um Optional contendo o Usuario_ORM se encontrado.
     */
    Optional<Usuario_ORM> findFirstByEmail(String email);
}