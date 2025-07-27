package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Autor_ODM;
import br.com.grupo9.middleware.model.odm.Editora_ODM;
import br.com.grupo9.middleware.model.odm.Livro_ODM;
import br.com.grupo9.middleware.model.orm.Livro_ORM;
import br.com.grupo9.middleware.repository.odm.AutorOdmRepository;
import br.com.grupo9.middleware.repository.odm.EditoraOdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LivroTransformer {

    private final AutorOdmRepository autorOdmRepository;
    private final EditoraOdmRepository editoraOdmRepository;

    /**
     * Converte um Livro_ORM para um Livro_ODM, resolvendo as dependências de Autor e Editora.
     * @param orm O objeto Livro vindo do SQLite.
     * @return Um novo Livro_ODM com as DBRefs preenchidas.
     * @throws IllegalStateException se uma dependência (Autor ou Editora) não for encontrada no MongoDB.
     */
    public Livro_ODM toOdm(Livro_ORM orm) {
        if (orm == null) {
            return null;
        }

        // 1. Busca o Autor correspondente no MongoDB usando o nome como chave.
        String nomeAutor = orm.getAutor().getNome();
        Autor_ODM autorOdm = autorOdmRepository.findByNome(nomeAutor).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência não sincronizada: Autor '" + nomeAutor + "' não encontrado no MongoDB."));

        // 2. Busca a Editora correspondente no MongoDB usando o nome como chave.
        String nomeEditora = orm.getEditora().getNome();
        Editora_ODM editoraOdm = editoraOdmRepository.findByNome(nomeEditora).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência não sincronizada: Editora '" + nomeEditora + "' não encontrada no MongoDB."));

        // 3. Constrói o Livro_ODM
        Livro_ODM odm = new Livro_ODM();
        odm.setTitulo(orm.getTitulo());
        odm.setAnoPublicacao(orm.getAnoPublicacao());
        odm.setPreco(orm.getPreco());
        // Nota: O modelo ODM da API REST tem 'isbn', mas o modelo ORM do desktop não.
        // Se este campo for importante, ele deve ser adicionado ao modelo ORM e à UI.
        // odm.setIsbn(...);

        // 4. Atribui as referências encontradas para criar as DBRefs
        odm.setAutor(autorOdm);
        odm.setEditora(editoraOdm);

        return odm;
    }
}