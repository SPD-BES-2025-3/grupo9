package br.ufg.inf.grupo9.biblioteca.repository;

import br.ufg.inf.grupo9.biblioteca.model.Emprestimo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface EmprestimoRepository extends MongoRepository<Emprestimo, String> {
    List<Emprestimo> findByDataEmprestimo(Date dataEmprestimo);

    List<Emprestimo> findByLivro(String livroId);
}
