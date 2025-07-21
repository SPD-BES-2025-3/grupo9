package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EditoraView {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty endereco;
    private final SimpleStringProperty telefone;

    public EditoraView(int id, String nome, String endereco, String telefone) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.endereco = new SimpleStringProperty(endereco);
        this.telefone = new SimpleStringProperty(telefone);
    }

    public int getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public String getEndereco() { return endereco.get(); }
    public String getTelefone() { return telefone.get(); }
}