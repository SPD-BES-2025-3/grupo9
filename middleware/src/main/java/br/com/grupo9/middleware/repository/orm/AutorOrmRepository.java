package br.com.grupo9.middleware.repository.orm;

import br.com.grupo9.middleware.model.orm.Autor_ORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorOrmRepository extends JpaRepository<Autor_ORM, Integer> {

    /**
     * Busca o primeiro autor encontrado com o nome fornecido.
     * Usado para verificar a existência e evitar duplicatas durante a sincronização.
     * @param nome O nome do autor a ser buscado.
     * @return um Optional contendo o Autor_ORM se encontrado, ou vazio caso contrário.
     */
    Optional<Autor_ORM> findFirstByNome(String nome);
}