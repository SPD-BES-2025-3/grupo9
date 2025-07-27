package br.com.grupo9.middleware.listener;

import br.com.grupo9.middleware.service.SyncService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j // Adiciona um logger SLF4J gerenciado pelo Lombok
public class RedisListener {

    private final SyncService syncService;
    private final Gson gson = new Gson();

    /**
     * Este método é chamado pelo MessageListenerAdapter quando uma nova mensagem
     * é publicada no canal 'crud-channel'.
     * @param jsonMessage A mensagem recebida, já convertida para String.
     */
    public void handleMessage(String jsonMessage) {
        log.info("Mensagem recebida do Redis: {}", jsonMessage);

        try {
            JsonObject envelope = gson.fromJson(jsonMessage, JsonObject.class);
            String source = envelope.get("source").getAsString();

            // Lógica para sincronização bidirecional:
            // O Middleware não deve processar mensagens que ele mesmo poderia ter originado no futuro.
            if ("Middleware".equalsIgnoreCase(source)) {
                log.warn("Ignorando mensagem da própria fonte (Middleware).");
                return;
            }

            String operation = envelope.get("operation").getAsString();
            String entityType = envelope.get("entityType").getAsString();
            JsonObject payload = envelope.getAsJsonObject("payload");

            // Delega a sincronização para o serviço apropriado
            switch (entityType) {
                case "Autor":
                    syncService.syncAutor(operation, payload);
                    break;
                case "Editora":
                    syncService.syncEditora(operation, payload);
                    break;
                case "Usuario":
                    syncService.syncUsuario(operation, payload);
                    break;
                case "Livro":
                    syncService.syncLivro(operation, payload);
                    break;
                case "Emprestimo":
                    syncService.syncEmprestimo(operation, payload);
                    break;
                default:
                    log.warn("Tipo de entidade não suportado para sincronização: {}", entityType);
            }
        } catch (Exception e) {
            log.error("Falha ao processar mensagem. Enviando para fila de retry. Mensagem: {}", jsonMessage, e);
            // TODO: Implementar lógica de envio para uma fila de reprocessamento (retry queue).
            // Exemplo: redisTemplate.opsForList().leftPush("crud-retry-queue", jsonMessage);
        }
    }
}