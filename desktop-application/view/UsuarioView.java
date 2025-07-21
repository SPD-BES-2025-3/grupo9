package view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsuarioView {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty email;
    private final SimpleStringProperty telefone;

    public UsuarioView(int id, String nome, String email, String telefone) {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.telefone = new SimpleStringProperty(telefone);
    }

    public int getId() { return id.get(); }
    public String getNome() { return nome.get(); }
    public String getEmail() { return email.get(); }
    public String getTelefone() { return telefone.get(); }
}