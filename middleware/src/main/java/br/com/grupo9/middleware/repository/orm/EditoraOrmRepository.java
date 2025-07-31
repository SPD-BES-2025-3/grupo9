package br.com.grupo9.middleware.repository.orm;

import br.com.grupo9.middleware.model.orm.Editora_ORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditoraOrmRepository extends JpaRepository<Editora_ORM, Integer> {

    /**
     * Busca a primeira editora encontrada com o nome fornecido.
     * @param nome O nome da editora a ser buscada.
     * @return um Optional contendo a Editora_ORM se encontrada.
     */
    Optional<Editora_ORM> findFirstByNome(String nome);
}