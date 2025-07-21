package controller;

import Model.Autor;
import Model.AutorRepository;
import Model.Database;
import Model.Livro;
import Model.LivroRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import view.LivroView;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    // --- COMPONENTES FXML PARA LIVROS ---
    @FXML private TableView<LivroView> tabela;
    @FXML private TableColumn<LivroView, Integer> idCol;
    @FXML private TableColumn<LivroView, String> tituloCol;
    @FXML private TableColumn<LivroView, String> autorCol;
    @FXML private TableColumn<LivroView, Integer> anoPublicacaoCol;
    @FXML private TableColumn<LivroView, Double> precoCol;
    @FXML private TableColumn<LivroView, String> dataCadastroCol;
    @FXML private TextField idField;
    @FXML private TextField tituloField;
    @FXML private ComboBox<Autor> autorComboBox;
    @FXML private TextField anoPublicacaoField;
    @FXML private TextField precoField;
    @FXML private TextField dataCadastroField;
    @FXML private Button adicionarButton;
    @FXML private Button atualizarButton;
    @FXML private Button deletarButton;
    @FXML private Button cancelarButton;
    @FXML private Button salvarButton;

    // --- COMPONENTES FXML PARA AUTORES ---
    @FXML private TableView<Autor> tabelaAutor;
    @FXML private TableColumn<Autor, Integer> idAutorCol;
    @FXML private TableColumn<Autor, String> nomeAutorCol;
    @FXML private TableColumn<Autor, String> nacionalidadeAutorCol;
    @FXML private TextField idAutorField;
    @FXML private TextField nomeAutorField;
    @FXML private TextField nacionalidadeAutorField;
    @FXML private Button adicionarAutorButton;
    @FXML private Button atualizarAutorButton;
    @FXML private Button deletarAutorButton;
    @FXML private Button cancelarAutorButton;
    @FXML private Button salvarAutorButton;
    
    // --- REPOSITÓRIOS E BANCO DE DADOS ---
    private static final Database database = new Database("livraria.sqlite");
    private static final LivroRepository livroRepo = new LivroRepository(database);
    private static final AutorRepository autorRepo = new AutorRepository(database);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // --- INICIALIZAÇÃO DA ABA DE LIVROS ---
        setupLivrosTab();
        
        // --- INICIALIZAÇÃO DA ABA DE AUTORES ---
        setupAutoresTab();

        // Carregar dados iniciais
        tabela.setItems(loadAllLivrosView());
        tabelaAutor.setItems(loadAllAutores());
        loadAutoresIntoComboBox();
    }
    
    private void setupLivrosTab() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        anoPublicacaoCol.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
        dataCadastroCol.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));

        tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleLivroSelected(newValue));
        
        setLivroFormState(FormState.INITIAL);
    }
    
    private void setupAutoresTab() {
        idAutorCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeAutorCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nacionalidadeAutorCol.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        
        tabelaAutor.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleAutorSelected(newValue));
                
        setAutorFormState(FormState.INITIAL);
    }

    //region CRUD Livros

    @FXML
    public void onAdicionarButtonAction() {
        setLivroFormState(FormState.ADDING);
        limparCampos();
    }

    @FXML
    public void onSalvarButtonAction() {
        try {
            Livro livro = new Livro();
            // Se o ID estiver preenchido, é uma atualização
            if (!idField.getText().isEmpty()) {
                livro.setId(Integer.parseInt(idField.getText()));
            }

            livro.setTitulo(tituloField.getText());
            livro.setAutor(autorComboBox.getSelectionModel().getSelectedItem());
            livro.setAnoPublicacao(Integer.parseInt(anoPublicacaoField.getText()));
            String precoTexto = precoField.getText().replace(",", ".");
            livro.setPreco(Double.parseDouble(precoTexto));
            livro.setDataCadastro(parseDate(dataCadastroField.getText()));
            
            if (livro.getAutor() == null) {
                showAlert(AlertType.ERROR, "Selecione um autor.");
                return;
            }

            if (livro.getId() == 0) {
                livroRepo.create(livro);
                showAlert(AlertType.INFORMATION, "Livro salvo com sucesso!");
            } else {
                livroRepo.update(livro);
                showAlert(AlertType.INFORMATION, "Livro atualizado com sucesso!");
            }
            
            tabela.setItems(loadAllLivrosView());
            tabela.getSelectionModel().select(modelToView(livro));
            setLivroFormState(FormState.VIEWING);
            
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erro de formato numérico: Verifique 'Ano' e 'Preço'.");
        } catch (ParseException e) {
            showAlert(AlertType.ERROR, "Formato de data inválido. Use DD/MM/AAAA.");
        }
    }
    
    @FXML
    public void onAtualizarButtonAction() {
        setLivroFormState(FormState.EDITING);
    }
    
    @FXML
    public void onDeletarButtonAction() {
        LivroView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Livro livroToDelete = livroRepo.loadFromId(selected.getId());
            if (livroToDelete != null) {
                livroRepo.delete(livroToDelete);
                tabela.getItems().remove(selected);
                limparCampos();
                setLivroFormState(FormState.INITIAL);
                showAlert(AlertType.INFORMATION, "Livro deletado com sucesso.");
            }
        } else {
            showAlert(AlertType.WARNING, "Selecione um livro para deletar.");
        }
    }
    
    @FXML
    public void onCancelarButtonAction() {
        LivroView selected = tabela.getSelectionModel().getSelectedItem();
        if (selected != null) {
            handleLivroSelected(selected);
            setLivroFormState(FormState.VIEWING);
        } else {
            limparCampos();
            setLivroFormState(FormState.INITIAL);
        }
    }
    
    private void handleLivroSelected(LivroView newSelection) {
        if (newSelection != null) {
            idField.setText(String.valueOf(newSelection.getId()));
            tituloField.setText(newSelection.getTitulo());
            anoPublicacaoField.setText(String.valueOf(newSelection.getAnoPublicacao()));
            precoField.setText(String.format("%.2f", newSelection.getPreco()).replace(".", ","));
            dataCadastroField.setText(newSelection.getDataCadastro());
            
            // Seleciona o autor correspondente no ComboBox
            Autor autorDoLivro = livroRepo.loadFromId(newSelection.getId()).getAutor();
            autorComboBox.getSelectionModel().select(autorDoLivro);
            
            setLivroFormState(FormState.VIEWING);
        } else {
            limparCampos();
            setLivroFormState(FormState.INITIAL);
        }
    }
    
    //endregion
    
    //region CRUD Autores

    @FXML
    private void onAdicionarAutorAction() {
        setAutorFormState(FormState.ADDING);
        limparCamposAutor();
    }
    
    @FXML
    private void onSalvarAutorAction() {
        String nome = nomeAutorField.getText();
        String nacionalidade = nacionalidadeAutorField.getText();
        if (nome == null || nome.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "O nome do autor não pode ser vazio.");
            return;
        }

        Autor autor;
        if (idAutorField.getText().isEmpty()) {
            autor = new Autor(nome, nacionalidade);
            autorRepo.create(autor);
            showAlert(AlertType.INFORMATION, "Autor salvo com sucesso!");
        } else {
            autor = tabelaAutor.getSelectionModel().getSelectedItem();
            autor.setNome(nome);
            autor.setNacionalidade(nacionalidade);
            autorRepo.update(autor);
            showAlert(AlertType.INFORMATION, "Autor atualizado com sucesso!");
        }

        tabelaAutor.setItems(loadAllAutores());
        loadAutoresIntoComboBox();
        tabelaAutor.getSelectionModel().select(autor);
        setAutorFormState(FormState.VIEWING);
    }

    @FXML
    private void onAtualizarAutorAction() {
        setAutorFormState(FormState.EDITING);
    }
    
    @FXML
    private void onDeletarAutorAction() {
        Autor selected = tabelaAutor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // VERIFICAÇÃO: Não permitir deletar autor se ele estiver em uso.
            long count = livroRepo.loadAll().stream().filter(l -> l.getAutor().getId() == selected.getId()).count();
            if (count > 0) {
                showAlert(AlertType.ERROR, "Não é possível deletar este autor, pois ele está associado a " + count + " livro(s).");
                return;
            }
            autorRepo.delete(selected);
            tabelaAutor.getItems().remove(selected);
            loadAutoresIntoComboBox();
            limparCamposAutor();
            setAutorFormState(FormState.INITIAL);
            showAlert(AlertType.INFORMATION, "Autor deletado com sucesso.");
        } else {
            showAlert(AlertType.WARNING, "Selecione um autor para deletar.");
        }
    }
    
    @FXML
    private void onCancelarAutorAction() {
        Autor selected = tabelaAutor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            handleAutorSelected(selected);
            setAutorFormState(FormState.VIEWING);
        } else {
            limparCamposAutor();
            setAutorFormState(FormState.INITIAL);
        }
    }
    
    private void handleAutorSelected(Autor autor) {
        if (autor != null) {
            idAutorField.setText(String.valueOf(autor.getId()));
            nomeAutorField.setText(autor.getNome());
            nacionalidadeAutorField.setText(autor.getNacionalidade());
            setAutorFormState(FormState.VIEWING);
        } else {
            limparCamposAutor();
            setAutorFormState(FormState.INITIAL);
        }
    }
    
    //endregion

    //region Utilitários e Helpers

    private enum FormState { INITIAL, VIEWING, ADDING, EDITING }

    private void setLivroFormState(FormState state) {
        boolean fieldsDisabled = state == FormState.INITIAL || state == FormState.VIEWING;
        tituloField.setDisable(fieldsDisabled);
        autorComboBox.setDisable(fieldsDisabled);
        anoPublicacaoField.setDisable(fieldsDisabled);
        precoField.setDisable(fieldsDisabled);
        dataCadastroField.setDisable(fieldsDisabled);

        adicionarButton.setDisable(state == FormState.ADDING || state == FormState.EDITING);
        atualizarButton.setDisable(state != FormState.VIEWING);
        deletarButton.setDisable(state != FormState.VIEWING);
        salvarButton.setDisable(state == FormState.INITIAL || state == FormState.VIEWING);
        cancelarButton.setDisable(state == FormState.INITIAL || state == FormState.VIEWING);
    }
    
    private void setAutorFormState(FormState state) {
        boolean fieldsDisabled = state == FormState.INITIAL || state == FormState.VIEWING;
        nomeAutorField.setDisable(fieldsDisabled);
        nacionalidadeAutorField.setDisable(fieldsDisabled);

        adicionarAutorButton.setDisable(state == FormState.ADDING || state == FormState.EDITING);
        atualizarAutorButton.setDisable(state != FormState.VIEWING);
        deletarAutorButton.setDisable(state != FormState.VIEWING);
        salvarAutorButton.setDisable(state == FormState.INITIAL || state == FormState.VIEWING);
        cancelarAutorButton.setDisable(state == FormState.INITIAL || state == FormState.VIEWING);
    }

    private ObservableList<LivroView> loadAllLivrosView() {
        ObservableList<LivroView> lista = FXCollections.observableArrayList();
        List<Livro> listaFromDb = livroRepo.loadAll();
        for (Livro livro : listaFromDb) {
            lista.add(modelToView(livro));
        }
        return lista;
    }
    
    private ObservableList<Autor> loadAllAutores() {
        return FXCollections.observableArrayList(autorRepo.loadAll());
    }

    private void loadAutoresIntoComboBox() {
        autorComboBox.setItems(loadAllAutores());
    }

    private LivroView modelToView(Livro livro) {
        String nomeAutor = (livro.getAutor() != null) ? livro.getAutor().getNome() : "Autor Desconhecido";
        return new LivroView(
                livro.getId(),
                livro.getTitulo(),
                nomeAutor,
                livro.getAnoPublicacao(),
                livro.getPreco(),
                livro.printDataCadastro()
        );
    }
    
    private Date parseDate(String dateString) throws ParseException {
        if (dateString == null || dateString.trim().isEmpty()) {
            return new Date();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        return dateFormat.parse(dateString);
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Informação do Sistema");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void limparCampos() {
        tabela.getSelectionModel().clearSelection();
        idField.clear();
        tituloField.clear();
        autorComboBox.getSelectionModel().clearSelection();
        anoPublicacaoField.clear();
        precoField.clear();
        dataCadastroField.clear();
    }
    
    private void limparCamposAutor() {
        tabelaAutor.getSelectionModel().clearSelection();
        idAutorField.clear();
        nomeAutorField.clear();
        nacionalidadeAutorField.clear();
    }

    //endregion

    //region Métodos do Menu (Importar/Exportar, etc.)

    @FXML private void onNovoDBAction(ActionEvent event) { showAlert(AlertType.INFORMATION, "Funcionalidade não implementada."); }
    @FXML private void onAbrirDBAction(ActionEvent event) { showAlert(AlertType.INFORMATION, "Funcionalidade não implementada."); }
    @FXML private void onSairAction(ActionEvent event) { System.exit(0); }
    @FXML private void onLimparCamposAction(ActionEvent event) { onCancelarButtonAction(); }
    
    @FXML
    private void onAtualizarTabelaAction(ActionEvent event) {
        tabela.setItems(loadAllLivrosView());
        tabelaAutor.setItems(loadAllAutores());
        loadAutoresIntoComboBox();
        showAlert(AlertType.INFORMATION, "Tabelas atualizadas!");
    }

    @FXML
    private void onSobreAction(ActionEvent event) {
        showAlert(AlertType.INFORMATION, "Sistema de Gerenciamento de Livros v1.0\nDesenvolvido por [Seu Nome]");
    }
    
    @FXML
    private void onExportarJSONAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar para JSON");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos JSON", "*.json"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            if (livroRepo.dumpFile("JSON", file)) {
                showAlert(AlertType.INFORMATION, "Dados exportados com sucesso!");
            } else {
                showAlert(AlertType.ERROR, "Erro ao exportar dados.");
            }
        }
    }
    
    @FXML
    private void onExportarXMLAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar para XML");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos XML", "*.xml"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            if (livroRepo.dumpFile("XML", file)) {
                showAlert(AlertType.INFORMATION, "Dados exportados com sucesso!");
            } else {
                showAlert(AlertType.ERROR, "Erro ao exportar dados.");
            }
        }
    }
    
    @FXML
    private void onImportarJSONAction(ActionEvent event) {
        showAlert(AlertType.WARNING, "A importação ainda não suporta a relação com autores.");
    }

    @FXML
    private void onImportarXMLAction(ActionEvent event) {
        showAlert(AlertType.WARNING, "A importação ainda não suporta a relação com autores.");
    }
    
    //endregion
}
