package br.com.grupo9.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AutorView {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty nacionalidade;

    public AutorView(int id, String nome, String nacionalidade) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.nacionalidade = new SimpleStringProperty(nacionalidade);
    }

    // Getters para as propriedades
    public int getId() {
        return this.id.get();
    }

    public String getNome() {
        return this.nome.get();
    }

    public String getNacionalidade() {
        return this.nacionalidade.get();
    }
}
