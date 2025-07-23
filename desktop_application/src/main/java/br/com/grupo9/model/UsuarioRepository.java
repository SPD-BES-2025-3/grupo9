package br.com.grupo9.model;

public class UsuarioRepository extends Repository<Usuario, Integer> {
    public UsuarioRepository() {
        super(Usuario.class);
    }
}