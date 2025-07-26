package br.com.grupo9.middleware.listener;

import br.com.grupo9.middleware.service.SyncService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisListener implements MessageListener {

    private final SyncService syncService;
    private final Gson gson = new Gson();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String jsonMessage = new String(message.getBody());
        System.out.println("Mensagem recebida do Redis: " + jsonMessage);

        try {
            JsonObject envelope = gson.fromJson(jsonMessage, JsonObject.class);
            String source = envelope.get("source").getAsString();

            // Só processa se a mensagem vier da fonte oposta
            // Neste exemplo, só tratamos ORM -> ODM
            if ("ORM_Desktop".equals(source)) {
                String operation = envelope.get("operation").getAsString();
                String entityType = envelope.get("entityType").getAsString();
                JsonObject payload = envelope.getAsJsonObject("payload");

                switch (entityType) {
                    case "Autor":
                        syncService.syncAutor(operation, payload);
                        break;
                    // Adicionar cases para Livro, Editora, etc.
                }
            }
        } catch (Exception e) {
            System.err.println("Falha ao processar mensagem. Enviando para fila de retry.");
            // Lógica para enviar a 'jsonMessage' para uma fila de retry no Redis.
        }
    }
}