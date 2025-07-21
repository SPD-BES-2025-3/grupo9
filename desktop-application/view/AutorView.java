package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Classe AutorView representa a visualização de um autor no sistema.
 * Utiliza propriedades JavaFX para integração com a interface gráfica.
 */

public class AutorView {
    
    private SimpleIntegerProperty id;
    private SimpleStringProperty nome;
    private SimpleStringProperty nacionalidade;
    private SimpleStringProperty dataNascimento;
    
    public AutorView(int id, String nome, String nacionalidade, String dataNascimento) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.nacionalidade = new SimpleStringProperty(nacionalidade);
        this.dataNascimento = new SimpleStringProperty(dataNascimento);
    }

    // Getters e Setters para Properties JavaFX
    
    public int getId() {
        return this.id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getNome() {
        return this.nome.get();
    }
    
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    
    public String getNacionalidade() {
        return this.nacionalidade.get();
    }
    
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade.set(nacionalidade);
    }
    
    public String getDataNascimento() {
        return this.dataNascimento.get();
    }
    
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento.set(dataNascimento);
    }
}
