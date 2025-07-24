package br.ufg.inf.grupo9.biblioteca.repository;

import br.ufg.inf.grupo9.biblioteca.model.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LivroRepository extends MongoRepository<Livro, String> {
    List<Livro> findByTitulo(String titulo);
}
