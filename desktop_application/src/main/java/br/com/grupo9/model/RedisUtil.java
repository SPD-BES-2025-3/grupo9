package br.com.grupo9.model;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

public class RedisUtil {

    private static final String REDIS_URI = "redis://localhost:6379";
    private static RedisClient redisClient;
    private static StatefulRedisConnection<String, String> connection;

    // Bloco estático para inicializar a conexão quando a classe for carregada.
    static {
        try {
            redisClient = RedisClient.create(REDIS_URI);
            connection = redisClient.connect();
            System.out.println("Conectado ao Redis com sucesso!");
        } catch (Exception e) {
            System.err.println("Falha ao conectar ao Redis. Verifique se o servidor está rodando.");
            System.err.println("Mensagem: " + e.getMessage());
        }
    }

    /**
     * Retorna a conexão ativa com o Redis.
     * @return A conexão StatefulRedisConnection.
     */
    public static StatefulRedisConnection<String, String> getConnection() {
        if (connection == null || !connection.isOpen()) {
            System.err.println("Conexão com Redis não está disponível. Tentando reconectar...");
            try {
                connection = redisClient.connect();
            } catch (Exception e) {
                System.err.println("Reconexão falhou.");
                return null;
            }
        }
        return connection;
    }

    /**
     * Fecha a conexão com o Redis e desliga o cliente.
     * Deve ser chamado ao encerrar a aplicação.
     */
    public static void shutdown() {
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
        System.out.println("Conexão com Redis encerrada.");
    }
}