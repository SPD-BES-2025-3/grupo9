package br.ufg.inf.grupo9.biblioteca.repository;

import br.ufg.inf.grupo9.biblioteca.model.Emprestimo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmprestimoRepository extends MongoRepository<Emprestimo, String> {
}
