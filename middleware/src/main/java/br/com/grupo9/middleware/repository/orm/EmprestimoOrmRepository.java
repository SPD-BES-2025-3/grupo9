package br.com.grupo9.middleware.repository.orm;

import br.com.grupo9.middleware.model.orm.Emprestimo_ORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoOrmRepository extends JpaRepository<Emprestimo_ORM, Integer> {

    /**
     * Busca um empréstimo pela combinação do ID do usuário e do ID do livro.
     * Útil para verificar se um empréstimo específico já existe.
     * @param usuarioId O ID do usuário.
     * @param livroId O ID do livro.
     * @return um Optional contendo o Emprestimo_ORM se encontrado.
     */
    Optional<Emprestimo_ORM> findByUsuarioIdAndLivroId(Integer usuarioId, Integer livroId);
}