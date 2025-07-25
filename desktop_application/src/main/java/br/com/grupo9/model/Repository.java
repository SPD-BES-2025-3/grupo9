package br.com.grupo9.model;

import br.com.grupo9.service.OperationType;
import br.com.grupo9.service.RedisPublisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public abstract class Repository<T, ID> {

    private final Class<T> entityClass;
    private final RedisPublisher publisher; // Adiciona o publisher

    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.publisher = new RedisPublisher(); // Instancia o publisher
    }

    public T create(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();

            // Publica a operação APÓS o commit no banco de dados
            publisher.publish(OperationType.CREATE, entity);

            System.out.println(entityClass.getSimpleName() + " criado com sucesso.");
            return entity;
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Erro ao criar " + entityClass.getSimpleName() + ":");
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public void update(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        T updatedEntity = null;
        try {
            transaction.begin();
            updatedEntity = em.merge(entity);
            transaction.commit();

            // Publica a operação APÓS o commit no banco de dados
            if (updatedEntity != null) {
                publisher.publish(OperationType.UPDATE, updatedEntity);
            }

            System.out.println(entityClass.getSimpleName() + " atualizado com sucesso.");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Erro ao atualizar " + entityClass.getSimpleName() + ":");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void delete(T entity) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T managedEntity = em.contains(entity) ? entity : em.merge(entity);

            // Publica a operação APÓS o commit no banco de dados
            // Primeiro remove, depois comita, depois publica. A entidade ainda está em memória.
            em.remove(managedEntity);
            transaction.commit();

            publisher.publish(OperationType.DELETE, managedEntity);

            System.out.println(entityClass.getSimpleName() + " deletado com sucesso.");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Erro ao deletar " + entityClass.getSimpleName() + ":");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Métodos loadById e loadAll não precisam de alteração
    public T loadById(ID id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

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