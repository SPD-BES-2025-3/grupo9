package br.com.grupo9.middleware.model.orm;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "emprestimo")
public class Emprestimo_ORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario_ORM usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro_ORM livro;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataEmprestimo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataDevolucaoPrevista;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucaoReal;

    public Emprestimo_ORM() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario_ORM getUsuario() { return usuario; }
    public void setUsuario(Usuario_ORM usuario) { this.usuario = usuario; }
    public Livro_ORM getLivro() { return livro; }
    public void setLivro(Livro_ORM livro) { this.livro = livro; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(Date dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }
}
