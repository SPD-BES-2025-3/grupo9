package br.ufg.inf.grupo9.biblioteca.repository;

import br.ufg.inf.grupo9.biblioteca.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    List<Usuario> findByNome(String nome);
    List<Usuario> findByEmail(String email);
}
