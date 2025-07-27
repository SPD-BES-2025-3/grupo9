package br.com.grupo9.middleware.service;

import br.com.grupo9.middleware.model.orm.*;
import br.com.grupo9.middleware.repository.odm.*;
import br.com.grupo9.middleware.transformer.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncService {

    private final Gson gson = new Gson();

    // Injeção de todos os repositórios ODM (destino)
    private final AutorOdmRepository autorOdmRepository;
    private final EditoraOdmRepository editoraOdmRepository;
    private final UsuarioOdmRepository usuarioOdmRepository;
    private final LivroOdmRepository livroOdmRepository;
    private final EmprestimoOdmRepository emprestimoOdmRepository;

    // Injeção de todos os transformadores
    private final AutorTransformer autorTransformer;
    private final EditoraTransformer editoraTransformer;
    private final UsuarioTransformer usuarioTransformer;
    private final LivroTransformer livroTransformer;
    private final EmprestimoTransformer emprestimoTransformer;

    /**
     * Sincroniza uma entidade Autor do ORM para o ODM.
     */
    public void syncAutor(String operation, JsonObject payload) {
        Autor_ORM orm = gson.fromJson(payload, Autor_ORM.class);

        if ("DELETE".equals(operation)) {
            autorOdmRepository.findByNome(orm.getNome()).forEach(autorOdmRepository::delete);
            log.info("Autor deletado do MongoDB: {}", orm.getNome());
        } else { // CREATE ou UPDATE
            Autor_ODM odm = autorTransformer.toOdm(orm);
            autorOdmRepository.findByNome(odm.getNome()).stream().findFirst().ifPresent(
                    existing -> odm.setId(existing.getId())
            );
            autorOdmRepository.save(odm);
            log.info("Autor sincronizado para MongoDB: {}", odm.getNome());
        }
    }

    /**
     * Sincroniza uma entidade Editora do ORM para o ODM.
     */
    public void syncEditora(String operation, JsonObject payload) {
        Editora_ORM orm = gson.fromJson(payload, Editora_ORM.class);

        if ("DELETE".equals(operation)) {
            editoraOdmRepository.findByNome(orm.getNome()).forEach(editoraOdmRepository::delete);
            log.info("Editora deletada do MongoDB: {}", orm.getNome());
        } else {
            Editora_ODM odm = editoraTransformer.toOdm(orm);
            editoraOdmRepository.findByNome(odm.getNome()).stream().findFirst().ifPresent(
                    existing -> odm.setId(existing.getId())
            );
            editoraOdmRepository.save(odm);
            log.info("Editora sincronizada para MongoDB: {}", odm.getNome());
        }
    }

    /**
     * Sincroniza uma entidade Usuario do ORM para o ODM.
     */
    public void syncUsuario(String operation, JsonObject payload) {
        Usuario_ORM orm = gson.fromJson(payload, Usuario_ORM.class);

        if ("DELETE".equals(operation)) {
            usuarioOdmRepository.findByEmail(orm.getEmail()).forEach(usuarioOdmRepository::delete);
            log.info("Usuário deletado do MongoDB: {}", orm.getEmail());
        } else {
            Usuario_ODM odm = usuarioTransformer.toOdm(orm);
            usuarioOdmRepository.findByEmail(odm.getEmail()).stream().findFirst().ifPresent(
                    existing -> odm.setId(existing.getId())
            );
            usuarioOdmRepository.save(odm);
            log.info("Usuário sincronizado para MongoDB: {}", odm.getEmail());
        }
    }

    /**
     * Sincroniza uma entidade Livro do ORM para o ODM.
     */
    public void syncLivro(String operation, JsonObject payload) {
        Livro_ORM orm = gson.fromJson(payload, Livro_ORM.class);

        if ("DELETE".equals(operation)) {
            livroOdmRepository.findByTitulo(orm.getTitulo()).forEach(livroOdmRepository::delete);
            log.info("Livro deletado do MongoDB: {}", orm.getTitulo());
        } else {
            try {
                Livro_ODM odm = livroTransformer.toOdm(orm);
                livroOdmRepository.findByTitulo(odm.getTitulo()).stream().findFirst().ifPresent(
                        existing -> odm.setId(existing.getId())
                );
                livroOdmRepository.save(odm);
                log.info("Livro sincronizado para MongoDB: {}", odm.getTitulo());
            } catch (IllegalStateException e) {
                log.error("Falha na sincronização do Livro: Dependência não encontrada. {}", e.getMessage());
                // TODO: Enviar para a fila de retry
            }
        }
    }

    /**
     * Sincroniza uma entidade Emprestimo do ORM para o ODM.
     */
    public void syncEmprestimo(String operation, JsonObject payload) {
        Emprestimo_ORM orm = gson.fromJson(payload, Emprestimo_ORM.class);

        if ("DELETE".equals(operation)) {
            // A identificação de um empréstimo para deleção é mais complexa.
            // Precisaríamos de uma chave única (ex: ID do ORM mapeado).
            // Por enquanto, vamos ignorar a deleção de empréstimos para simplificar.
            log.warn("Operação DELETE para Empréstimo ainda não implementada na sincronização.");
        } else {
            try {
                Emprestimo_ODM odm = emprestimoTransformer.toOdm(orm);
                // A lógica de "upsert" para empréstimos também é complexa.
                // Vamos assumir que cada evento é um novo empréstimo no ODM por simplicidade.
                emprestimoOdmRepository.save(odm);
                log.info("Empréstimo sincronizado para MongoDB para o livro: {}", odm.getLivro().getTitulo());
            } catch (IllegalStateException e) {
                log.error("Falha na sincronização do Empréstimo: Dependência não encontrada. {}", e.getMessage());
                // TODO: Enviar para a fila de retry
            }
        }
    }
}