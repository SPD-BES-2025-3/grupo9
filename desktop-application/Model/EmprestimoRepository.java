package Model;

public class EmprestimoRepository extends Repository<Emprestimo, Integer> {
    public EmprestimoRepository(Database db) {
        super(db, Emprestimo.class);
    }
}