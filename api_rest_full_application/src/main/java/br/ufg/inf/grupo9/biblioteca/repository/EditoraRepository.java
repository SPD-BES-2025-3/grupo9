package br.ufg.inf.grupo9.biblioteca.repository;

import br.ufg.inf.grupo9.biblioteca.model.Editora;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EditoraRepository extends MongoRepository<Editora, String> {
}
