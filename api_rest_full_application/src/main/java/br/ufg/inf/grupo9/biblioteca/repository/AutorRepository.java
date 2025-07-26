package br.ufg.inf.grupo9.biblioteca.repository;

import br.ufg.inf.grupo9.biblioteca.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends MongoRepository<Autor, String> {

    List<Autor> findAllByNacionalidade(String nacionalidade);

    List<Autor> findByNome(String nome);
}