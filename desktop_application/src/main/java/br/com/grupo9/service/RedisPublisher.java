package br.com.grupo9.service;

import br.com.grupo9.model.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisPublisher {

    private final Gson gson;
    private static final String CRUD_CHANNEL = "crud-channel";

    public RedisPublisher() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Formata uma mensagem JSON e a publica no canal do Redis.
     * @param operation O tipo de operação (CREATE, UPDATE, DELETE).
     * @param entity A entidade que sofreu a ação.
     */
    public void publish(OperationType operation, Object entity) {
        StatefulRedisConnection<String, String> connection = RedisUtil.getConnection();
        if (connection == null) {
            System.err.println("Publicação cancelada: sem conexão com o Redis.");
            return;
        }

        try {
            // Cria um "envelope" JSON para a mensagem
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("operation", operation.name());
            messageObject.addProperty("entityType", entity.getClass().getSimpleName());
            // Adiciona a entidade serializada como um objeto JSON aninhado (o "payload")
            messageObject.add("payload", gson.toJsonTree(entity));

            String message = gson.toJson(messageObject);

            // Obtém os comandos síncronos e publica a mensagem
            RedisCommands<String, String> commands = connection.sync();
            commands.publish(CRUD_CHANNEL, message);

            System.out.println("Publicado no canal '" + CRUD_CHANNEL + "': " + operation + " de " + entity.getClass().getSimpleName());

        } catch (Exception e) {
            System.err.println("Erro ao publicar mensagem no Redis: " + e.getMessage());
            e.printStackTrace();
        }
    }
}