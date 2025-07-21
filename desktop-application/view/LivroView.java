package view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LivroView {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty titulo;
    private final SimpleStringProperty autor;
    private final SimpleStringProperty editora;
    private final SimpleIntegerProperty anoPublicacao;
    private final SimpleDoubleProperty preco;

    public LivroView(int id, String titulo, String autor, String editora, int anoPublicacao, double preco) {
        this.id = new SimpleIntegerProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.editora = new SimpleStringProperty(editora);
        this.anoPublicacao = new SimpleIntegerProperty(anoPublicacao);
        this.preco = new SimpleDoubleProperty(preco);
    }

    public int getId() { return id.get(); }
    public String getTitulo() { return titulo.get(); }
    public String getAutor() { return autor.get(); }
    public String getEditora() { return editora.get(); }
    public int getAnoPublicacao() { return anoPublicacao.get(); }
    public double getPreco() { return preco.get(); }
}