package br.com.grupo9.middleware.listener;

import br.com.grupo9.middleware.service.SyncService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisListener {

    private final SyncService syncService;
    private final Gson gson = new Gson();

    public void handleMessage(String jsonMessage) {
        log.info("<< Mensagem recebida do Redis: {}", jsonMessage);
        try {
            JsonObject envelope = gson.fromJson(jsonMessage, JsonObject.class);
            String source = envelope.get("source").getAsString();
            String operation = envelope.get("operation").getAsString();
            String entityType = envelope.get("entityType").getAsString();
            JsonObject payload = envelope.getAsJsonObject("payload");

            if ("ORM_Desktop".equalsIgnoreCase(source)) {
                handleOrmToOdm(operation, entityType, payload);
            } else if ("ODM_API".equalsIgnoreCase(source)) {
                handleOdmToOrm(operation, entityType, payload);
            } else {
                log.warn("Ignorando mensagem de fonte desconhecida: {}", source);
            }
        } catch (Exception e) {
            log.error("Falha ao processar mensagem. Enviando para fila de retry. Mensagem: {}", jsonMessage, e);
        }
    }

    private void handleOrmToOdm(String operation, String entityType, JsonObject payload) {
        log.info("-> Processando fluxo ORM -> ODM para entidade {}", entityType);
        switch (entityType) {
            case "Autor": syncService.syncAutorFromOrm(operation, payload); break;
            case "Editora": syncService.syncEditoraFromOrm(operation, payload); break;
            case "Usuario": syncService.syncUsuarioFromOrm(operation, payload); break;
            case "Livro": syncService.syncLivroFromOrm(operation, payload); break;
            case "Emprestimo": syncService.syncEmprestimoFromOrm(operation, payload); break;
            default: log.warn("Tipo de entidade não suportado: {}", entityType);
        }
    }

    private void handleOdmToOrm(String operation, String entityType, JsonObject payload) {
        log.info("<- Processando fluxo ODM -> ORM para entidade {}", entityType);
        switch (entityType) {
            case "Autor": syncService.syncAutorFromOdm(operation, payload); break;
            case "Editora": syncService.syncEditoraFromOdm(operation, payload); break;
            case "Usuario": syncService.syncUsuarioFromOdm(operation, payload); break;
            case "Livro": syncService.syncLivroFromOdm(operation, payload); break;
            case "Emprestimo": syncService.syncEmprestimoFromOdm(operation, payload); break;
            default: log.warn("Tipo de entidade não suportado para sincronização inversa: {}", entityType);
        }
    }
}
