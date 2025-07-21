package Model;

public class UsuarioRepository extends Repository<Usuario, Integer> {
    public UsuarioRepository(Database db) {
        super(db, Usuario.class);
    }
}