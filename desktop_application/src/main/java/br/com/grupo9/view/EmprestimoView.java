package br.com.grupo9.view;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmprestimoView {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty usuario;
    private final SimpleStringProperty livro;
    private final SimpleStringProperty dataEmprestimo;
    private final SimpleStringProperty dataDevolucaoPrevista;
    private final SimpleStringProperty dataDevolucaoReal;

    public EmprestimoView(int id, String usuario, String livro, String dataEmprestimo, String dataDevolucaoPrevista, String dataDevolucaoReal) {
        this.id = new SimpleIntegerProperty(id);
        this.usuario = new SimpleStringProperty(usuario);
        this.livro = new SimpleStringProperty(livro);
        this.dataEmprestimo = new SimpleStringProperty(dataEmprestimo);
        this.dataDevolucaoPrevista = new SimpleStringProperty(dataDevolucaoPrevista);
        this.dataDevolucaoReal = new SimpleStringProperty(dataDevolucaoReal);
    }

    public int getId() { return id.get(); }
    public String getUsuario() { return usuario.get(); }
    public String getLivro() { return livro.get(); }
    public String getDataEmprestimo() { return dataEmprestimo.get(); }
    public String getDataDevolucaoPrevista() { return dataDevolucaoPrevista.get(); }
    public String getDataDevolucaoReal() { return dataDevolucaoReal.get(); }
}