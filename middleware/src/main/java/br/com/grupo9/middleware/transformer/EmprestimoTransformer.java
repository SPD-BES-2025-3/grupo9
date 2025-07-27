package br.com.grupo9.middleware.transformer;

import br.com.grupo9.middleware.model.odm.Emprestimo_ODM;
import br.com.grupo9.middleware.model.odm.Livro_ODM;
import br.com.grupo9.middleware.model.odm.Usuario_ODM;
import br.com.grupo9.middleware.model.orm.Emprestimo_ORM;
import br.com.grupo9.middleware.repository.odm.LivroOdmRepository;
import br.com.grupo9.middleware.repository.odm.UsuarioOdmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmprestimoTransformer {

    private final UsuarioOdmRepository usuarioOdmRepository;
    private final LivroOdmRepository livroOdmRepository;

    /**
     * Converte um Emprestimo_ORM para um Emprestimo_ODM, resolvendo as dependências de Usuario e Livro.
     * @param orm O objeto Emprestimo vindo do SQLite.
     * @return Um novo Emprestimo_ODM com as DBRefs preenchidas.
     * @throws IllegalStateException se uma dependência (Usuario ou Livro) não for encontrada no MongoDB.
     */
    public Emprestimo_ODM toOdm(Emprestimo_ORM orm) {
        if (orm == null) {
            return null;
        }

        // 1. Busca o Usuário correspondente no MongoDB usando o email como chave.
        String emailUsuario = orm.getUsuario().getEmail();
        Usuario_ODM usuarioOdm = usuarioOdmRepository.findByEmail(emailUsuario).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência não sincronizada: Usuário com email '" + emailUsuario + "' não encontrado no MongoDB."));

        // 2. Busca o Livro correspondente no MongoDB usando o título como chave.
        String tituloLivro = orm.getLivro().getTitulo();
        Livro_ODM livroOdm = livroOdmRepository.findByTitulo(tituloLivro).stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("Dependência não sincronizada: Livro com título '" + tituloLivro + "' não encontrado no MongoDB."));

        // 3. Constrói o Emprestimo_ODM
        Emprestimo_ODM odm = new Emprestimo_ODM();
        odm.setDataEmprestimo(orm.getDataEmprestimo());
        odm.setDataDevolucaoPrevista(orm.getDataDevolucaoPrevista());
        odm.setDataDevolucaoRealizada(orm.getDataDevolucaoReal());

        // 4. Atribui as referências encontradas
        odm.setUsuario(usuarioOdm);
        odm.setLivro(livroOdm);

        return odm;
    }
}