package br.com.grupo9.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "livraria-pu";
    private static EntityManagerFactory factory;

    // Inicializa o EntityManagerFactory estaticamente
    static {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Throwable ex) {
            System.err.println("Falha ao criar o EntityManagerFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retorna um EntityManager para realizar operações no banco de dados.
     * Cada operação (ou conjunto de operações relacionadas) deve usar seu próprio EntityManager.
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    /**
     * Fecha o EntityManagerFactory. Deve ser chamado ao encerrar a aplicação.
     */
    public static void shutdown() {
        if (factory != null) {
            factory.close();
        }
    }
}
