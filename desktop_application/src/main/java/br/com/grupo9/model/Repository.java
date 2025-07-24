package br.com.grupo9.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public abstract class Repository<T, ID> {

    private final Class<T> entityClass;

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Salva uma nova entidade ou atualiza uma existente que está desanexada.
     * @param entity A entidade a ser criada.
     * @return A entidade gerenciada após ser salva.
     */
    public T create(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T managedEntity = em.merge(entity);
            transaction.commit();
            System.out.println(entityClass.getSimpleName() + " criado/mesclado com sucesso.");
            return managedEntity;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao criar " + entityClass.getSimpleName() + ":");
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Atualiza uma entidade existente no banco de dados.
     * @param entity A entidade a ser atualizada.
     */
    public void update(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
            System.out.println(entityClass.getSimpleName() + " atualizado com sucesso.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao atualizar " + entityClass.getSimpleName() + ":");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Deleta uma entidade do banco de dados.
     * @param entity A entidade a ser deletada.
     */
    public void delete(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            transaction.commit();
            System.out.println(entityClass.getSimpleName() + " deletado com sucesso.");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Erro ao deletar " + entityClass.getSimpleName() + ":");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Carrega uma entidade pelo seu ID.
     * @param id O ID da entidade a ser carregada.
     * @return A entidade encontrada ou null.
     */
    public T loadById(ID id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    /**
     * Carrega todas as entidades de um tipo.
     * @return Uma lista com todas as entidades.
     */
    public List<T> loadAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String query = "FROM " + entityClass.getSimpleName();
            return em.createQuery(query, entityClass).getResultList();
        } finally {
            em.close();
        }
    }
}
