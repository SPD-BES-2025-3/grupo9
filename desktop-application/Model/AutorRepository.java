package Model;

public class AutorRepository extends Repository<Autor, Integer> {

    public AutorRepository(Database db) {
        super(db, Autor.class);
    }
}