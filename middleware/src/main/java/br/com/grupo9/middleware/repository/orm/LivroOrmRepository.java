package br.com.grupo9.middleware.repository.orm;

import br.com.grupo9.middleware.model.orm.Livro_ORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroOrmRepository extends JpaRepository<Livro_ORM, Integer> {

    /**
     * Busca o primeiro livro encontrado com o título fornecido.
     * @param titulo O título do livro a ser buscado.
     * @return um Optional contendo o Livro_ORM se encontrado.
     */
    Optional<Livro_ORM> findFirstByTitulo(String titulo);
}