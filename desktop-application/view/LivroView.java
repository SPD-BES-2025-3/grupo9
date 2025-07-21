package view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LivroView {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty autor;
    private final SimpleIntegerProperty anoPublicacao;
    private final SimpleDoubleProperty preco;
    private final SimpleStringProperty dataCadastro;

    public LivroView(int id, String titulo, String autor, int anoPublicacao, double preco, String dataCadastro) {
        this.id = new SimpleIntegerProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.anoPublicacao = new SimpleIntegerProperty(anoPublicacao);
        this.preco = new SimpleDoubleProperty(preco);
        this.dataCadastro = new SimpleStringProperty(dataCadastro);
    }

    // --- Getters para as Properties ---
    public int getId() { return this.id.get(); }
    public String getTitulo() { return this.titulo.get(); }
    public String getAutor() { return this.autor.get(); }
    public int getAnoPublicacao() { return this.anoPublicacao.get(); }
    public double getPreco() { return this.preco.get(); }
    public String getDataCadastro() { return this.dataCadastro.get(); }
}
