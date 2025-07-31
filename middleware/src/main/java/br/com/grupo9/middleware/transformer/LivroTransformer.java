package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Livro_ODM;
import br.com.grupo9.middleware.model.orm.Livro_ORM;
import br.com.grupo9.middleware.repository.odm.AutorOdmRepository;
import br.com.grupo9.middleware.repository.odm.EditoraOdmRepository;
import br.com.grupo9.middleware.repository.orm.AutorOrmRepository;
import br.com.grupo9.middleware.repository.orm.EditoraOrmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LivroTransformer {

    private final AutorOdmRepository autorOdmRepository;
    private final EditoraOdmRepository editoraOdmRepository;
    private final AutorOrmRepository autorOrmRepository;
    private final EditoraOrmRepository editoraOrmRepository;

    public Livro_ODM toOdm(Livro_ORM orm) {
        if (orm == null) return null;

        var autorOdm = autorOdmRepository.findByNome(orm.getAutor().getNome()).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência ORM->ODM não encontrada: Autor '" + orm.getAutor().getNome() + "'"));
        var editoraOdm = editoraOdmRepository.findByNome(orm.getEditora().getNome()).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência ORM->ODM não encontrada: Editora '" + orm.getEditora().getNome() + "'"));

        Livro_ODM odm = new Livro_ODM();
        odm.setTitulo(orm.getTitulo());
        odm.setAnoPublicacao(orm.getAnoPublicacao());
        odm.setPreco(orm.getPreco());
        odm.setAutor(autorOdm);
        odm.setEditora(editoraOdm);
        return odm;
    }

    public Livro_ORM toOrm(Livro_ODM odm) {
        if (odm == null) return null;

        var autorOrm = autorOrmRepository.findFirstByNome(odm.getAutor().getNome())
                .orElseThrow(() -> new IllegalStateException("Dependência ODM->ORM não encontrada: Autor '" + odm.getAutor().getNome() + "'"));
        var editoraOrm = editoraOrmRepository.findFirstByNome(odm.getEditora().getNome())
                .orElseThrow(() -> new IllegalStateException("Dependência ODM->ORM não encontrada: Editora '" + odm.getEditora().getNome() + "'"));

        Livro_ORM orm = new Livro_ORM();
        orm.setTitulo(odm.getTitulo());
        orm.setAnoPublicacao(odm.getAnoPublicacao());
        orm.setPreco(odm.getPreco());
        orm.setAutor(autorOrm);
        orm.setEditora(editoraOrm);
        return orm;
    }
}
