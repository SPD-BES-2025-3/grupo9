package br.com.grupo9.middleware.model.orm;

import jakarta.persistence.*;

@Entity
@Table(name = "livro")
public class Livro_ORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor_ORM autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora_ORM editora;

    @Column(name = "anoPublicacao")
    private int anoPublicacao;

    @Column
    private double preco;

    public Livro_ORM() {}

    // --- Getters e Setters ---
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return this.titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor_ORM getAutor() { return this.autor; }
    public void setAutor(Autor_ORM autor) { this.autor = autor; }
    public Editora_ORM getEditora() { return editora; }
    public void setEditora(Editora_ORM editora) { this.editora = editora; }
    public int getAnoPublicacao() { return this.anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public double getPreco() { return this.preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return this.titulo;
    }
}
