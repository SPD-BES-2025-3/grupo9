package br.com.grupo9.middleware.service;

import br.com.grupo9.middleware.model.orm.Autor_ORM;
import br.com.grupo9.middleware.repository.odm.AutorOdmRepository;
import br.com.grupo9.middleware.transformer.AutorTransformer;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyncService {

    private final AutorOdmRepository autorOdmRepository;
    private final AutorTransformer autorTransformer;
    // ... injetar outros repositórios e transformadores

    public void syncAutor(String operation, JsonObject payload) {
        // Para simplicidade, vamos usar um 'save' que funciona para CREATE e UPDATE.
        // Uma lógica mais robusta usaria um campo extra para mapear o ID do ORM para o ID do ODM.
        if ("DELETE".equals(operation)) {
            // Lógica para deletar (precisaria mapear o ID do ORM para o ID do ODM)
        } else { // CREATE ou UPDATE
            Autor_ORM orm = new Gson().fromJson(payload, Autor_ORM.class);
            var odm = autorTransformer.toOdm(orm);

            // Procura se já existe um autor com esse nome para evitar duplicatas
            var existing = autorOdmRepository.findByNome(odm.getNome()).stream().findFirst();
            existing.ifPresent(autor_odm -> odm.setId(autor_odm.getId()));

            autorOdmRepository.save(odm);
            System.out.println("Autor sincronizado para MongoDB: " + odm.getNome());
        }
    }
}