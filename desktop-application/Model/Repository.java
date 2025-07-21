package Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public abstract class Repository<T, ID> {

    protected Dao<T, ID> dao;
    private Class<T> entityClass;
    protected Database database;

    public Repository(Database db, Class<T> entityClass) {
        this.database = db;
        this.entityClass = entityClass;
        try {
            dao = DaoManager.createDao(db.getConnection(), entityClass);
            TableUtils.createTableIfNotExists(db.getConnection(), entityClass);
        } catch (SQLException e) {
            System.out.println("Erro ao criar DAO ou tabela para " + entityClass.getSimpleName() + ": " + e.getMessage());
        }
    }

    public T create(T entity) {
        try {
            dao.create(entity);
            System.out.println(entityClass.getSimpleName() + " criado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar " + entityClass.getSimpleName() + ": " + e.getMessage());
        }
        return entity;
    }

    public void update(T entity) {
        try {
            dao.update(entity);
            System.out.println(entityClass.getSimpleName() + " atualizado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar " + entityClass.getSimpleName() + ": " + e.getMessage());
        }
    }

    public void delete(T entity) {
        try {
            dao.delete(entity);
            System.out.println(entityClass.getSimpleName() + " deletado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar " + entityClass.getSimpleName() + ": " + e.getMessage());
        }
    }

    public T loadById(ID id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            System.out.println("Erro ao carregar " + entityClass.getSimpleName() + " por ID: " + e.getMessage());
        }
        return null;
    }

    public List<T> loadAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar todos os " + entityClass.getSimpleName() + "s: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}