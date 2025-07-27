package br.ufg.inf.grupo9.biblioteca.pubsub;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, String> redisTemplate;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String CRUD_CHANNEL = "crud-channel";

    public enum OperationType { CREATE, UPDATE, DELETE }

    public void publish(OperationType operation, Object entity) {
        try {
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("source", "ODM_API");
            messageObject.addProperty("operation", operation.name());
            messageObject.addProperty("entityType", entity.getClass().getSimpleName().replace("_ODM", ""));
            messageObject.add("payload", gson.toJsonTree(entity));

            String message = gson.toJson(messageObject);
            redisTemplate.convertAndSend(CRUD_CHANNEL, message);
            System.out.println("ODM_API publicou no canal Redis '" + CRUD_CHANNEL + "': " + operation);
        } catch (Exception e) {
            System.err.println("Erro ao publicar mensagem no Redis: " + e.getMessage());
        }
    }
}
