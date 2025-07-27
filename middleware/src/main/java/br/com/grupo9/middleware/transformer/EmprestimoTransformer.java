package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Emprestimo_ODM;
import br.com.grupo9.middleware.model.orm.Emprestimo_ORM;
import br.com.grupo9.middleware.repository.odm.LivroOdmRepository;
import br.com.grupo9.middleware.repository.odm.UsuarioOdmRepository;
import br.com.grupo9.middleware.repository.orm.LivroOrmRepository;
import br.com.grupo9.middleware.repository.orm.UsuarioOrmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmprestimoTransformer {

    private final UsuarioOdmRepository usuarioOdmRepository;
    private final LivroOdmRepository livroOdmRepository;
    private final UsuarioOrmRepository usuarioOrmRepository;
    private final LivroOrmRepository livroOrmRepository;

    public Emprestimo_ODM toOdm(Emprestimo_ORM orm) {
        if (orm == null) return null;

        var usuarioOdm = usuarioOdmRepository.findByEmail(orm.getUsuario().getEmail()).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência ORM->ODM não encontrada: Usuário '" + orm.getUsuario().getEmail() + "'"));
        var livroOdm = livroOdmRepository.findByTitulo(orm.getLivro().getTitulo()).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência ORM->ODM não encontrada: Livro '" + orm.getLivro().getTitulo() + "'"));

        Emprestimo_ODM odm = new Emprestimo_ODM();
        odm.setDataEmprestimo(orm.getDataEmprestimo());
        odm.setDataDevolucaoPrevista(orm.getDataDevolucaoPrevista());
        odm.setDataDevolucaoRealizada(orm.getDataDevolucaoReal());
        odm.setUsuario(usuarioOdm);
        odm.setLivro(livroOdm);
        return odm;
    }
    
    public Emprestimo_ORM toOrm(Emprestimo_ODM odm) {
        if (odm == null) return null;

        var usuarioOrm = usuarioOrmRepository.findFirstByEmail(odm.getUsuario().getEmail())
                .orElseThrow(() -> new IllegalStateException("Dependência ODM->ORM não encontrada: Usuário '" + odm.getUsuario().getEmail() + "'"));
        var livroOrm = livroOrmRepository.findFirstByTitulo(odm.getLivro().getTitulo())
                .orElseThrow(() -> new IllegalStateException("Dependência ODM->ORM não encontrada: Livro '" + odm.getLivro().getTitulo() + "'"));

        Emprestimo_ORM orm = new Emprestimo_ORM();
        orm.setDataEmprestimo(odm.getDataEmprestimo());
        orm.setDataDevolucaoPrevista(odm.getDataDevolucaoPrevista());
        orm.setDataDevolucaoReal(odm.getDataDevolucaoRealizada());
        orm.setUsuario(usuarioOrm);
        orm.setLivro(livroOrm);
        return orm;
    }
}
