package br.com.grupo9.middleware.service;

import br.com.grupo9.middleware.config.RedisConfig;
import br.com.grupo9.middleware.model.odm.*;
import br.com.grupo9.middleware.model.orm.*;
import br.com.grupo9.middleware.repository.odm.*;
import br.com.grupo9.middleware.repository.orm.*;
import br.com.grupo9.middleware.transformer.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    // Configura o Gson para lidar com formatos de data específicos, se necessário.
    private final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();

    // Injetado para poder republicar mensagens nas filas de retry/DLQ
    private final StringRedisTemplate redisTemplate;

    // Repositórios ODM (MongoDB)
    private final AutorOdmRepository autorOdmRepository;
    private final EditoraOdmRepository editoraOdmRepository;
    private final UsuarioOdmRepository usuarioOdmRepository;
    private final LivroOdmRepository livroOdmRepository;
    private final EmprestimoOdmRepository emprestimoOdmRepository;

    // Repositórios ORM (SQLite)
    private final AutorOrmRepository autorOrmRepository;
    private final EditoraOrmRepository editoraOrmRepository;
    private final UsuarioOrmRepository usuarioOrmRepository;
    private final LivroOrmRepository livroOrmRepository;
    private final EmprestimoOrmRepository emprestimoOrmRepository;

    // Transformadores de dados
    private final AutorTransformer autorTransformer;
    private final EditoraTransformer editoraTransformer;
    private final UsuarioTransformer usuarioTransformer;
    private final LivroTransformer livroTransformer;
    private final EmprestimoTransformer emprestimoTransformer;

    // --- FLUXO ORM -> ODM ---
    public void syncAutorFromOrm(String op, JsonObject payload) {
        Autor_ORM orm = gson.fromJson(payload, Autor_ORM.class);
        if ("DELETE".equals(op)) {
            autorOdmRepository.findByNome(orm.getNome()).forEach(autorOdmRepository::delete);
            log.info("ORM->ODM [DELETE]: Autor '{}' removido.", orm.getNome());
        } else {
            Autor_ODM odm = autorTransformer.toOdm(orm);
            autorOdmRepository.findByNome(odm.getNome()).stream().findFirst().ifPresent(e -> odm.setId(e.getId()));
            autorOdmRepository.save(odm);
            log.info("ORM->ODM [{}]: Autor '{}' salvo.", op, odm.getNome());
        }
    }

    public void syncEditoraFromOrm(String op, JsonObject payload) {
        Editora_ORM orm = gson.fromJson(payload, Editora_ORM.class);
        if ("DELETE".equals(op)) {
            editoraOdmRepository.findByNome(orm.getNome()).forEach(editoraOdmRepository::delete);
            log.info("ORM->ODM [DELETE]: Editora '{}' removida.", orm.getNome());
        } else {
            Editora_ODM odm = editoraTransformer.toOdm(orm);
            editoraOdmRepository.findByNome(odm.getNome()).stream().findFirst().ifPresent(e -> odm.setId(e.getId()));
            editoraOdmRepository.save(odm);
            log.info("ORM->ODM [{}]: Editora '{}' salva.", op, odm.getNome());
        }
    }

    public void syncUsuarioFromOrm(String op, JsonObject payload) {
        Usuario_ORM orm = gson.fromJson(payload, Usuario_ORM.class);
        if ("DELETE".equals(op)) {
            usuarioOdmRepository.findByEmail(orm.getEmail()).forEach(usuarioOdmRepository::delete);
            log.info("ORM->ODM [DELETE]: Usuário '{}' removido.", orm.getEmail());
        } else {
            Usuario_ODM odm = usuarioTransformer.toOdm(orm);
            usuarioOdmRepository.findByEmail(odm.getEmail()).stream().findFirst().ifPresent(e -> odm.setId(e.getId()));
            usuarioOdmRepository.save(odm);
            log.info("ORM->ODM [{}]: Usuário '{}' salvo.", op, odm.getEmail());
        }
    }

    public void syncLivroFromOrm(String op, JsonObject payload) {
        Livro_ORM orm = gson.fromJson(payload, Livro_ORM.class);
        if ("DELETE".equals(op)) {
            livroOdmRepository.findByTitulo(orm.getTitulo()).forEach(livroOdmRepository::delete);
            log.info("ORM->ODM [DELETE]: Livro '{}' removido.", orm.getTitulo());
        } else {
            try {
                Livro_ODM odm = livroTransformer.toOdm(orm);
                livroOdmRepository.findByTitulo(odm.getTitulo()).stream().findFirst().ifPresent(e -> odm.setId(e.getId()));
                livroOdmRepository.save(odm);
                log.info("ORM->ODM [{}]: Livro '{}' salvo.", op, odm.getTitulo());
            } catch (IllegalStateException e) {
                log.error("ORM->ODM Falha na sincronização do Livro: {}. Enviando para retry.", e.getMessage());
                republishForRetry(payload);
            }
        }
    }

    public void syncEmprestimoFromOrm(String op, JsonObject payload) {
        Emprestimo_ORM orm = gson.fromJson(payload, Emprestimo_ORM.class);
        if ("DELETE".equals(op)) {
            log.warn("ORM->ODM [DELETE] para Empréstimo não implementado.");
        } else {
            try {
                Emprestimo_ODM odm = emprestimoTransformer.toOdm(orm);
                emprestimoOdmRepository.save(odm);
                log.info("ORM->ODM [{}]: Empréstimo do livro '{}' salvo.", op, odm.getLivro().getTitulo());
            } catch (IllegalStateException e) {
                log.error("ORM->ODM Falha na sincronização do Empréstimo: {}. Enviando para retry.", e.getMessage());
                republishForRetry(payload);
            }
        }
    }

    // --- FLUXO ODM -> ORM ---
    public void syncAutorFromOdm(String op, JsonObject payload) {
        Autor_ODM odm = gson.fromJson(payload, Autor_ODM.class);
        if ("DELETE".equals(op)) {
            autorOrmRepository.findFirstByNome(odm.getNome()).ifPresent(autorOrmRepository::delete);
            log.info("ODM->ORM [DELETE]: Autor '{}' removido.", odm.getNome());
        } else {
            Autor_ORM orm = autorTransformer.toOrm(odm);
            autorOrmRepository.findFirstByNome(orm.getNome()).ifPresent(e -> orm.setId(e.getId()));
            autorOrmRepository.save(orm);
            log.info("ODM->ORM [{}]: Autor '{}' salvo.", op, orm.getNome());
        }
    }

    public void syncEditoraFromOdm(String op, JsonObject payload) {
        Editora_ODM odm = gson.fromJson(payload, Editora_ODM.class);
        if ("DELETE".equals(op)) {
            editoraOrmRepository.findFirstByNome(odm.getNome()).ifPresent(editoraOrmRepository::delete);
            log.info("ODM->ORM [DELETE]: Editora '{}' removida.", odm.getNome());
        } else {
            Editora_ORM orm = editoraTransformer.toOrm(odm);
            editoraOrmRepository.findFirstByNome(orm.getNome()).ifPresent(e -> orm.setId(e.getId()));
            editoraOrmRepository.save(orm);
            log.info("ODM->ORM [{}]: Editora '{}' salva.", op, orm.getNome());
        }
    }

    public void syncUsuarioFromOdm(String op, JsonObject payload) {
        Usuario_ODM odm = gson.fromJson(payload, Usuario_ODM.class);
        if ("DELETE".equals(op)) {
            usuarioOrmRepository.findFirstByEmail(odm.getEmail()).ifPresent(usuarioOrmRepository::delete);
            log.info("ODM->ORM [DELETE]: Usuário '{}' removido.", odm.getEmail());
        } else {
            Usuario_ORM orm = usuarioTransformer.toOrm(odm);
            usuarioOrmRepository.findFirstByEmail(orm.getEmail()).ifPresent(e -> orm.setId(e.getId()));
            usuarioOrmRepository.save(orm);
            log.info("ODM->ORM [{}]: Usuário '{}' salvo.", op, orm.getEmail());
        }
    }

    public void syncLivroFromOdm(String op, JsonObject payload) {
        Livro_ODM odm = gson.fromJson(payload, Livro_ODM.class);
        if ("DELETE".equals(op)) {
            livroOrmRepository.findFirstByTitulo(odm.getTitulo()).ifPresent(livroOrmRepository::delete);
            log.info("ODM->ORM [DELETE]: Livro '{}' removido.", odm.getTitulo());
        } else {
            try {
                Livro_ORM orm = livroTransformer.toOrm(odm);
                livroOrmRepository.findFirstByTitulo(orm.getTitulo()).ifPresent(e -> orm.setId(e.getId()));
                livroOrmRepository.save(orm);
                log.info("ODM->ORM [{}]: Livro '{}' salvo.", op, orm.getTitulo());
            } catch (IllegalStateException e) {
                log.error("ODM->ORM Falha na sincronização do Livro: {}. Enviando para retry.", e.getMessage());
                republishForRetry(payload);
            }
        }
    }

    public void syncEmprestimoFromOdm(String op, JsonObject payload) {
        Emprestimo_ODM odm = gson.fromJson(payload, Emprestimo_ODM.class);
        if ("DELETE".equals(op)) {
            log.warn("ODM->ORM [DELETE] para Empréstimo não implementado.");
        } else {
            try {
                Emprestimo_ORM orm = emprestimoTransformer.toOrm(odm);
                emprestimoOrmRepository.save(orm);
                log.info("ODM->ORM [{}]: Empréstimo do livro '{}' salvo.", op, orm.getLivro().getTitulo());
            } catch (IllegalStateException e) {
                log.error("ODM->ORM Falha na sincronização do Empréstimo: {}. Enviando para retry.", e.getMessage());
                republishForRetry(payload);
            }
        }
    }

    /**
     * Republica uma mensagem falha em um canal de retentativa ou DLQ.
     * @param originalPayload O payload da mensagem que falhou.
     */
    private void republishForRetry(JsonObject originalPayload) {
        try {
            int retryCount = 0;
            if (originalPayload.has("retryCount")) {
                retryCount = originalPayload.get("retryCount").getAsInt();
            }

            // Define um limite de retentativas para evitar loops infinitos
            if (retryCount < 3) {
                originalPayload.addProperty("retryCount", retryCount + 1);
                String messageToRetry = gson.toJson(originalPayload);
                redisTemplate.convertAndSend(RedisConfig.RETRY_CHANNEL, messageToRetry);
                log.info("Mensagem enviada para o canal de retry (tentativa {}).", retryCount + 1);
            } else {
                log.error("Limite de retentativas atingido. Enviando mensagem para a Dead-Letter Queue (DLQ).");
                redisTemplate.convertAndSend(RedisConfig.DLQ_CHANNEL, gson.toJson(originalPayload));
            }
        } catch (Exception ex) {
            log.error("Falha CRÍTICA ao enviar mensagem para a fila de retry/DLQ.", ex);
        }
    }
}