package br.com.grupo9.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataEmprestimo;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataDevolucaoPrevista;

    @Temporal(TemporalType.DATE)
    private Date dataDevolucaoReal;

    public Emprestimo() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(Date dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }
}
