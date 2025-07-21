package Model;

public class EditoraRepository extends Repository<Editora, Integer> {
    public EditoraRepository(Database db) {
        super(db, Editora.class);
    }
}