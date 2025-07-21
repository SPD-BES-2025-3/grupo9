package controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import view.*;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AppController implements Initializable {

    //region FXML Components & Repositories
    // --- GERAL ---
    private enum FormState { INITIAL, VIEWING, ADDING, EDITING, EDITING_DEVOLUCAO }
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- REPOSITÓRIOS ---
    private final Database database = new Database("livraria.sqlite");
    private final LivroRepository livroRepo = new LivroRepository(database);
    private final AutorRepository autorRepo = new AutorRepository(database);
    private final EditoraRepository editoraRepo = new EditoraRepository(database);
    private final UsuarioRepository usuarioRepo = new UsuarioRepository(database);
    private final EmprestimoRepository emprestimoRepo = new EmprestimoRepository(database);

    // --- COMPONENTES FXML: EMPRÉSTIMO ---
    @FXML private TextField idEmprestimoField;
    @FXML private ComboBox<Usuario> emprestimoUsuarioComboBox;
    @FXML private ComboBox<Livro> emprestimoLivroComboBox;
    @FXML private DatePicker dataEmprestimoPicker;
    @FXML private DatePicker dataDevolucaoPrevistaPicker;
    @FXML private DatePicker dataDevolucaoRealPicker;
    @FXML private Button adicionarEmprestimoButton;
    @FXML private Button salvarEmprestimoButton;
    @FXML private Button cancelarEmprestimoButton;
    @FXML private Button registrarDevolucaoButton;
    @FXML private Button deletarEmprestimoButton;
    @FXML private TableView<EmprestimoView> tabelaEmprestimo;
    @FXML private TableColumn<EmprestimoView, Integer> idEmprestimoCol;
    @FXML private TableColumn<EmprestimoView, String> usuarioEmprestimoCol;
    @FXML private TableColumn<EmprestimoView, String> livroEmprestimoCol;
    @FXML private TableColumn<EmprestimoView, String> dataEmprestimoCol;
    @FXML private TableColumn<EmprestimoView, String> dataDevolucaoPrevistaCol;
    @FXML private TableColumn<EmprestimoView, String> dataDevolucaoRealCol;

    // --- COMPONENTES FXML: LIVRO ---
    @FXML private TextField idLivroField;
    @FXML private TextField tituloLivroField;
    @FXML private TextField anoPublicacaoLivroField;
    @FXML private TextField precoLivroField;
    @FXML private ComboBox<Autor> livroAutorComboBox;
    @FXML private ComboBox<Editora> livroEditoraComboBox;
    @FXML private Button adicionarLivroButton;
    @FXML private Button atualizarLivroButton;
    @FXML private Button deletarLivroButton;
    @FXML private Button salvarLivroButton;
    @FXML private Button cancelarLivroButton;
    @FXML private TableView<LivroView> tabelaLivro;
    @FXML private TableColumn<LivroView, Integer> idLivroCol;
    @FXML private TableColumn<LivroView, String> tituloLivroCol;
    @FXML private TableColumn<LivroView, String> autorLivroCol;
    @FXML private TableColumn<LivroView, String> editoraLivroCol;
    @FXML private TableColumn<LivroView, Integer> anoPublicacaoLivroCol;
    @FXML private TableColumn<LivroView, Double> precoLivroCol;

    // --- COMPONENTES FXML: AUTOR ---
    @FXML private TextField idAutorField;
    @FXML private TextField nomeAutorField;
    @FXML private TextField nacionalidadeAutorField;
    @FXML private Button adicionarAutorButton;
    @FXML private Button atualizarAutorButton;
    @FXML private Button deletarAutorButton;
    @FXML private Button salvarAutorButton;
    @FXML private Button cancelarAutorButton;
    @FXML private TableView<Autor> tabelaAutor;
    @FXML private TableColumn<Autor, Integer> idAutorCol;
    @FXML private TableColumn<Autor, String> nomeAutorCol;
    @FXML private TableColumn<Autor, String> nacionalidadeAutorCol;

    // --- COMPONENTES FXML: EDITORA ---
    @FXML private TextField idEditoraField;
    @FXML private TextField nomeEditoraField;
    @FXML private TextField enderecoEditoraField;
    @FXML private TextField telefoneEditoraField;
    @FXML private Button adicionarEditoraButton;
    @FXML private Button atualizarEditoraButton;
    @FXML private Button deletarEditoraButton;
    @FXML private Button salvarEditoraButton;
    @FXML private Button cancelarEditoraButton;
    @FXML private TableView<Editora> tabelaEditora;
    @FXML private TableColumn<Editora, Integer> idEditoraCol;
    @FXML private TableColumn<Editora, String> nomeEditoraCol;
    @FXML private TableColumn<Editora, String> enderecoEditoraCol;
    @FXML private TableColumn<Editora, String> telefoneEditoraCol;

    // --- COMPONENTES FXML: USUÁRIO ---
    @FXML private TextField idUsuarioField;
    @FXML private TextField nomeUsuarioField;
    @FXML private TextField emailUsuarioField;
    @FXML private TextField telefoneUsuarioField;
    @FXML private PasswordField senhaUsuarioField;
    @FXML private Button adicionarUsuarioButton;
    @FXML private Button atualizarUsuarioButton;
    @FXML private Button deletarUsuarioButton;
    @FXML private Button salvarUsuarioButton;
    @FXML private Button cancelarUsuarioButton;
    @FXML private TableView<Usuario> tabelaUsuario;
    @FXML private TableColumn<Usuario, Integer> idUsuarioCol;
    @FXML private TableColumn<Usuario, String> nomeUsuarioCol;
    @FXML private TableColumn<Usuario, String> emailUsuarioCol;
    @FXML private TableColumn<Usuario, String> telefoneUsuarioCol;
    //endregion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEmprestimosTab();
        setupLivrosTab();
        setupAutoresTab();
        setupEditorasTab();
        setupUsuariosTab();
    }

    //region Empréstimos
    private void setupEmprestimosTab() {
        idEmprestimoCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usuarioEmprestimoCol.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        livroEmprestimoCol.setCellValueFactory(new PropertyValueFactory<>("livro"));
        dataEmprestimoCol.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        dataDevolucaoPrevistaCol.setCellValueFactory(new PropertyValueFactory<>("dataDevolucaoPrevista"));
        dataDevolucaoRealCol.setCellValueFactory(new PropertyValueFactory<>("dataDevolucaoReal"));

        tabelaEmprestimo.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> handleEmprestimoSelected(newV));
        loadEmprestimosData();
        setEmprestimoFormState(FormState.INITIAL);
    }

    private void loadEmprestimosData() {
        tabelaEmprestimo.setItems(FXCollections.observableArrayList(
            emprestimoRepo.loadAll().stream().map(this::emprestimoModelToView).collect(Collectors.toList())
        ));
        emprestimoUsuarioComboBox.setItems(FXCollections.observableArrayList(usuarioRepo.loadAll()));
        emprestimoLivroComboBox.setItems(FXCollections.observableArrayList(livroRepo.loadAll()));
    }

    private void handleEmprestimoSelected(EmprestimoView view) {
        if (view != null) {
            Emprestimo emprestimo = emprestimoRepo.loadById(view.getId());
            if (emprestimo != null) {
                idEmprestimoField.setText(String.valueOf(emprestimo.getId()));
                emprestimoUsuarioComboBox.setValue(emprestimo.getUsuario());
                emprestimoLivroComboBox.setValue(emprestimo.getLivro());
                dataEmprestimoPicker.setValue(dateToLocalDate(emprestimo.getDataEmprestimo()));
                dataDevolucaoPrevistaPicker.setValue(dateToLocalDate(emprestimo.getDataDevolucaoPrevista()));
                dataDevolucaoRealPicker.setValue(dateToLocalDate(emprestimo.getDataDevolucaoReal()));
                setEmprestimoFormState(FormState.VIEWING);
            }
        } else {
            clearEmprestimoForm();
            setEmprestimoFormState(FormState.INITIAL);
        }
    }

    @FXML private void handleAdicionarEmprestimo() {
        clearEmprestimoForm();
        setEmprestimoFormState(FormState.ADDING);
        dataEmprestimoPicker.setValue(LocalDate.now());
        dataDevolucaoPrevistaPicker.setValue(LocalDate.now().plusWeeks(2));
    }

    @FXML private void handleRegistrarDevolucao() {
        if (tabelaEmprestimo.getSelectionModel().getSelectedItem() != null) {
            setEmprestimoFormState(FormState.EDITING_DEVOLUCAO);
            dataDevolucaoRealPicker.setValue(LocalDate.now());
        }
    }

    @FXML private void handleSalvarEmprestimo() {
        try {
            Emprestimo emprestimo;
            if (idEmprestimoField.getText().isEmpty()) {
                emprestimo = new Emprestimo();
            } else {
                emprestimo = emprestimoRepo.loadById(Integer.parseInt(idEmprestimoField.getText()));
            }

            emprestimo.setUsuario(emprestimoUsuarioComboBox.getValue());
            emprestimo.setLivro(emprestimoLivroComboBox.getValue());
            emprestimo.setDataEmprestimo(localDateToDate(dataEmprestimoPicker.getValue()));
            emprestimo.setDataDevolucaoPrevista(localDateToDate(dataDevolucaoPrevistaPicker.getValue()));
            emprestimo.setDataDevolucaoReal(localDateToDate(dataDevolucaoRealPicker.getValue()));

            if (emprestimo.getId() == 0) {
                emprestimoRepo.create(emprestimo);
                showAlert(Alert.AlertType.INFORMATION, "Empréstimo registrado!");
            } else {
                emprestimoRepo.update(emprestimo);
                showAlert(Alert.AlertType.INFORMATION, "Empréstimo atualizado!");
            }
            loadEmprestimosData();
            tabelaEmprestimo.getSelectionModel().select(emprestimoModelToView(emprestimo));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML private void handleCancelarEmprestimo() {
        handleEmprestimoSelected(tabelaEmprestimo.getSelectionModel().getSelectedItem());
    }

    @FXML private void handleDeletarEmprestimo() {
        EmprestimoView selected = tabelaEmprestimo.getSelectionModel().getSelectedItem();
        if (selected != null) {
            emprestimoRepo.delete(emprestimoRepo.loadById(selected.getId()));
            showAlert(Alert.AlertType.INFORMATION, "Registro de empréstimo excluído.");
            loadEmprestimosData();
        }
    }

    private void setEmprestimoFormState(FormState state) {
        boolean isViewing = (state == FormState.INITIAL || state == FormState.VIEWING);
        emprestimoUsuarioComboBox.setDisable(state != FormState.ADDING);
        emprestimoLivroComboBox.setDisable(state != FormState.ADDING);
        dataEmprestimoPicker.setDisable(state != FormState.ADDING);
        dataDevolucaoPrevistaPicker.setDisable(state != FormState.ADDING);
        dataDevolucaoRealPicker.setDisable(state != FormState.EDITING_DEVOLUCAO);

        adicionarEmprestimoButton.setDisable(!isViewing);
        salvarEmprestimoButton.setDisable(isViewing);
        cancelarEmprestimoButton.setDisable(isViewing);
        deletarEmprestimoButton.setDisable(state != FormState.VIEWING);
        
        boolean devolvido = dataDevolucaoRealPicker.getValue() != null;
        registrarDevolucaoButton.setDisable(state != FormState.VIEWING || devolvido);
    }

    private void clearEmprestimoForm() {
        idEmprestimoField.clear();
        emprestimoUsuarioComboBox.setValue(null);
        emprestimoLivroComboBox.setValue(null);
        dataEmprestimoPicker.setValue(null);
        dataDevolucaoPrevistaPicker.setValue(null);
        dataDevolucaoRealPicker.setValue(null);
    }
    //endregion

    //region Livros
    private void setupLivrosTab() {
        idLivroCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloLivroCol.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorLivroCol.setCellValueFactory(new PropertyValueFactory<>("autor"));
        editoraLivroCol.setCellValueFactory(new PropertyValueFactory<>("editora"));
        anoPublicacaoLivroCol.setCellValueFactory(new PropertyValueFactory<>("anoPublicacao"));
        precoLivroCol.setCellValueFactory(new PropertyValueFactory<>("preco"));

        tabelaLivro.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> handleLivroSelected(newV));
        loadLivrosData();
        setLivroFormState(FormState.INITIAL);
    }

    private void loadLivrosData() {
        tabelaLivro.setItems(FXCollections.observableArrayList(
            livroRepo.loadAll().stream().map(this::livroModelToView).collect(Collectors.toList())
        ));
        livroAutorComboBox.setItems(FXCollections.observableArrayList(autorRepo.loadAll()));
        livroEditoraComboBox.setItems(FXCollections.observableArrayList(editoraRepo.loadAll()));
    }

    private void handleLivroSelected(LivroView view) {
        if (view != null) {
            Livro livro = livroRepo.loadById(view.getId());
            if (livro != null) {
                idLivroField.setText(String.valueOf(livro.getId()));
                tituloLivroField.setText(livro.getTitulo());
                anoPublicacaoLivroField.setText(String.valueOf(livro.getAnoPublicacao()));
                precoLivroField.setText(String.format("%.2f", livro.getPreco()).replace(".", ","));
                livroAutorComboBox.setValue(livro.getAutor());
                livroEditoraComboBox.setValue(livro.getEditora());
                setLivroFormState(FormState.VIEWING);
            }
        } else {
            clearLivroForm();
            setLivroFormState(FormState.INITIAL);
        }
    }

    @FXML private void handleAdicionarLivro() {
        clearLivroForm();
        setLivroFormState(FormState.ADDING);
    }

    @FXML private void handleAtualizarLivro() {
        if (tabelaLivro.getSelectionModel().getSelectedItem() != null) setLivroFormState(FormState.EDITING);
    }

    @FXML private void handleSalvarLivro() {
        try {
            Livro livro;
            if (idLivroField.getText().isEmpty()) {
                livro = new Livro();
            } else {
                livro = livroRepo.loadById(Integer.parseInt(idLivroField.getText()));
            }
            
            livro.setTitulo(tituloLivroField.getText());
            livro.setAnoPublicacao(Integer.parseInt(anoPublicacaoLivroField.getText()));
            livro.setPreco(Double.parseDouble(precoLivroField.getText().replace(",", ".")));
            livro.setAutor(livroAutorComboBox.getValue());
            livro.setEditora(livroEditoraComboBox.getValue());

            if (livro.getId() == 0) {
                livroRepo.create(livro);
                showAlert(Alert.AlertType.INFORMATION, "Livro salvo!");
            } else {
                livroRepo.update(livro);
                showAlert(Alert.AlertType.INFORMATION, "Livro atualizado!");
            }
            loadLivrosData();
            tabelaLivro.getSelectionModel().select(livroModelToView(livro));
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao salvar livro: " + e.getMessage());
        }
    }

    @FXML private void handleCancelarLivro() {
        handleLivroSelected(tabelaLivro.getSelectionModel().getSelectedItem());
    }

    @FXML private void handleDeletarLivro() {
        LivroView selected = tabelaLivro.getSelectionModel().getSelectedItem();
        if (selected != null) {
            livroRepo.delete(livroRepo.loadById(selected.getId()));
            showAlert(Alert.AlertType.INFORMATION, "Livro deletado.");
            loadLivrosData();
        }
    }

    private void setLivroFormState(FormState state) {
        boolean disabled = state == FormState.INITIAL || state == FormState.VIEWING;
        tituloLivroField.setDisable(disabled);
        anoPublicacaoLivroField.setDisable(disabled);
        precoLivroField.setDisable(disabled);
        livroAutorComboBox.setDisable(disabled);
        livroEditoraComboBox.setDisable(disabled);
        
        adicionarLivroButton.setDisable(!disabled);
        salvarLivroButton.setDisable(disabled);
        cancelarLivroButton.setDisable(disabled);
        atualizarLivroButton.setDisable(state != FormState.VIEWING);
        deletarLivroButton.setDisable(state != FormState.VIEWING);
    }

    private void clearLivroForm() {
        idLivroField.clear();
        tituloLivroField.clear();
        anoPublicacaoLivroField.clear();
        precoLivroField.clear();
        livroAutorComboBox.setValue(null);
        livroEditoraComboBox.setValue(null);
    }
    //endregion

    //region Autores
    private void setupAutoresTab() {
        idAutorCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeAutorCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nacionalidadeAutorCol.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        tabelaAutor.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> handleAutorSelected(newV));
        loadAutoresData();
        setAutorFormState(FormState.INITIAL);
    }
    
    private void loadAutoresData() {
        tabelaAutor.setItems(FXCollections.observableArrayList(autorRepo.loadAll()));
    }

    private void handleAutorSelected(Autor autor) {
        if (autor != null) {
            idAutorField.setText(String.valueOf(autor.getId()));
            nomeAutorField.setText(autor.getNome());
            nacionalidadeAutorField.setText(autor.getNacionalidade());
            setAutorFormState(FormState.VIEWING);
        } else {
            clearAutorForm();
            setAutorFormState(FormState.INITIAL);
        }
    }

    @FXML private void handleAdicionarAutor() {
        clearAutorForm();
        setAutorFormState(FormState.ADDING);
    }

    @FXML private void handleAtualizarAutor() {
        if (tabelaAutor.getSelectionModel().getSelectedItem() != null) setAutorFormState(FormState.EDITING);
    }

    @FXML private void handleSalvarAutor() {
        try {
            Autor autor;
            if (idAutorField.getText().isEmpty()) {
                autor = new Autor();
            } else {
                autor = autorRepo.loadById(Integer.parseInt(idAutorField.getText()));
            }
            autor.setNome(nomeAutorField.getText());
            autor.setNacionalidade(nacionalidadeAutorField.getText());

            if (autor.getId() == 0) {
                autorRepo.create(autor);
                showAlert(Alert.AlertType.INFORMATION, "Autor salvo!");
            } else {
                autorRepo.update(autor);
                showAlert(Alert.AlertType.INFORMATION, "Autor atualizado!");
            }
            loadAutoresData();
            tabelaAutor.getSelectionModel().select(autor);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao salvar autor: " + e.getMessage());
        }
    }

    @FXML private void handleCancelarAutor() {
        handleAutorSelected(tabelaAutor.getSelectionModel().getSelectedItem());
    }

    @FXML private void handleDeletarAutor() {
        Autor selected = tabelaAutor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            autorRepo.delete(selected);
            showAlert(Alert.AlertType.INFORMATION, "Autor deletado.");
            loadAutoresData();
        }
    }

    private void setAutorFormState(FormState state) {
        boolean disabled = state == FormState.INITIAL || state == FormState.VIEWING;
        nomeAutorField.setDisable(disabled);
        nacionalidadeAutorField.setDisable(disabled);
        adicionarAutorButton.setDisable(!disabled);
        salvarAutorButton.setDisable(disabled);
        cancelarAutorButton.setDisable(disabled);
        atualizarAutorButton.setDisable(state != FormState.VIEWING);
        deletarAutorButton.setDisable(state != FormState.VIEWING);
    }

    private void clearAutorForm() {
        idAutorField.clear();
        nomeAutorField.clear();
        nacionalidadeAutorField.clear();
    }
    //endregion

    //region Editoras
    private void setupEditorasTab() {
        idEditoraCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeEditoraCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        enderecoEditoraCol.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneEditoraCol.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tabelaEditora.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> handleEditoraSelected(newV));
        loadEditorasData();
        setEditoraFormState(FormState.INITIAL);
    }

    private void loadEditorasData() {
        tabelaEditora.setItems(FXCollections.observableArrayList(editoraRepo.loadAll()));
    }

    private void handleEditoraSelected(Editora editora) {
        if (editora != null) {
            idEditoraField.setText(String.valueOf(editora.getId()));
            nomeEditoraField.setText(editora.getNome());
            enderecoEditoraField.setText(editora.getEndereco());
            telefoneEditoraField.setText(editora.getTelefone());
            setEditoraFormState(FormState.VIEWING);
        } else {
            clearEditoraForm();
            setEditoraFormState(FormState.INITIAL);
        }
    }

    @FXML private void handleAdicionarEditora() {
        clearEditoraForm();
        setEditoraFormState(FormState.ADDING);
    }

    @FXML private void handleAtualizarEditora() {
        if (tabelaEditora.getSelectionModel().getSelectedItem() != null) setEditoraFormState(FormState.EDITING);
    }

    @FXML private void handleSalvarEditora() {
        try {
            Editora editora;
            if (idEditoraField.getText().isEmpty()) {
                editora = new Editora();
            } else {
                editora = editoraRepo.loadById(Integer.parseInt(idEditoraField.getText()));
            }
            editora.setNome(nomeEditoraField.getText());
            editora.setEndereco(enderecoEditoraField.getText());
            editora.setTelefone(telefoneEditoraField.getText());

            if (editora.getId() == 0) {
                editoraRepo.create(editora);
                showAlert(Alert.AlertType.INFORMATION, "Editora salva!");
            } else {
                editoraRepo.update(editora);
                showAlert(Alert.AlertType.INFORMATION, "Editora atualizada!");
            }
            loadEditorasData();
            tabelaEditora.getSelectionModel().select(editora);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao salvar editora: " + e.getMessage());
        }
    }

    @FXML private void handleCancelarEditora() {
        handleEditoraSelected(tabelaEditora.getSelectionModel().getSelectedItem());
    }

    @FXML private void handleDeletarEditora() {
        Editora selected = tabelaEditora.getSelectionModel().getSelectedItem();
        if (selected != null) {
            editoraRepo.delete(selected);
            showAlert(Alert.AlertType.INFORMATION, "Editora deletada.");
            loadEditorasData();
        }
    }

    private void setEditoraFormState(FormState state) {
        boolean disabled = state == FormState.INITIAL || state == FormState.VIEWING;
        nomeEditoraField.setDisable(disabled);
        enderecoEditoraField.setDisable(disabled);
        telefoneEditoraField.setDisable(disabled);
        adicionarEditoraButton.setDisable(!disabled);
        salvarEditoraButton.setDisable(disabled);
        cancelarEditoraButton.setDisable(disabled);
        atualizarEditoraButton.setDisable(state != FormState.VIEWING);
        deletarEditoraButton.setDisable(state != FormState.VIEWING);
    }

    private void clearEditoraForm() {
        idEditoraField.clear();
        nomeEditoraField.clear();
        enderecoEditoraField.clear();
        telefoneEditoraField.clear();
    }
    //endregion

    //region Usuários
    private void setupUsuariosTab() {
        idUsuarioCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomeUsuarioCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailUsuarioCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        telefoneUsuarioCol.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tabelaUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> handleUsuarioSelected(newV));
        loadUsuariosData();
        setUsuarioFormState(FormState.INITIAL);
    }

    private void loadUsuariosData() {
        tabelaUsuario.setItems(FXCollections.observableArrayList(usuarioRepo.loadAll()));
    }

    private void handleUsuarioSelected(Usuario usuario) {
        if (usuario != null) {
            idUsuarioField.setText(String.valueOf(usuario.getId()));
            nomeUsuarioField.setText(usuario.getNome());
            emailUsuarioField.setText(usuario.getEmail());
            telefoneUsuarioField.setText(usuario.getTelefone());
            senhaUsuarioField.clear();
            setUsuarioFormState(FormState.VIEWING);
        } else {
            clearUsuarioForm();
            setUsuarioFormState(FormState.INITIAL);
        }
    }

    @FXML private void handleAdicionarUsuario() {
        clearUsuarioForm();
        setUsuarioFormState(FormState.ADDING);
    }

    @FXML private void handleAtualizarUsuario() {
        if (tabelaUsuario.getSelectionModel().getSelectedItem() != null) setUsuarioFormState(FormState.EDITING);
    }

    @FXML private void handleSalvarUsuario() {
        try {
            Usuario usuario;
            if (idUsuarioField.getText().isEmpty()) {
                usuario = new Usuario();
            } else {
                usuario = usuarioRepo.loadById(Integer.parseInt(idUsuarioField.getText()));
            }
            usuario.setNome(nomeUsuarioField.getText());
            usuario.setEmail(emailUsuarioField.getText());
            usuario.setTelefone(telefoneUsuarioField.getText());

            if (senhaUsuarioField.getText() != null && !senhaUsuarioField.getText().isEmpty()) {
                usuario.setSenha(senhaUsuarioField.getText()); // Idealmente, usar hash
            } else if (usuario.getId() == 0) {
                throw new Exception("Senha é obrigatória para novos usuários.");
            }

            if (usuario.getId() == 0) {
                usuarioRepo.create(usuario);
                showAlert(Alert.AlertType.INFORMATION, "Usuário salvo!");
            } else {
                usuarioRepo.update(usuario);
                showAlert(Alert.AlertType.INFORMATION, "Usuário atualizado!");
            }
            loadUsuariosData();
            tabelaUsuario.getSelectionModel().select(usuario);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @FXML private void handleCancelarUsuario() {
        handleUsuarioSelected(tabelaUsuario.getSelectionModel().getSelectedItem());
    }

    @FXML private void handleDeletarUsuario() {
        Usuario selected = tabelaUsuario.getSelectionModel().getSelectedItem();
        if (selected != null) {
            usuarioRepo.delete(selected);
            showAlert(Alert.AlertType.INFORMATION, "Usuário deletado.");
            loadUsuariosData();
        }
    }

    private void setUsuarioFormState(FormState state) {
        boolean disabled = state == FormState.INITIAL || state == FormState.VIEWING;
        nomeUsuarioField.setDisable(disabled);
        emailUsuarioField.setDisable(disabled);
        telefoneUsuarioField.setDisable(disabled);
        senhaUsuarioField.setDisable(disabled);
        adicionarUsuarioButton.setDisable(!disabled);
        salvarUsuarioButton.setDisable(disabled);
        cancelarUsuarioButton.setDisable(disabled);
        atualizarUsuarioButton.setDisable(state != FormState.VIEWING);
        deletarUsuarioButton.setDisable(state != FormState.VIEWING);
    }

    private void clearUsuarioForm() {
        idUsuarioField.clear();
        nomeUsuarioField.clear();
        emailUsuarioField.clear();
        telefoneUsuarioField.clear();
        senhaUsuarioField.clear();
    }
    //endregion

    //region Helpers & Menu Actions
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Informação do Sistema");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private LivroView livroModelToView(Livro livro) {
        String autorNome = livro.getAutor() != null ? livro.getAutor().getNome() : "N/D";
        String editoraNome = livro.getEditora() != null ? livro.getEditora().getNome() : "N/D";
        return new LivroView(livro.getId(), livro.getTitulo(), autorNome, editoraNome, livro.getAnoPublicacao(), livro.getPreco());
    }

    private EmprestimoView emprestimoModelToView(Emprestimo e) {
        return new EmprestimoView(
            e.getId(),
            e.getUsuario() != null ? e.getUsuario().getNome() : "N/D",
            e.getLivro() != null ? e.getLivro().getTitulo() : "N/D",
            formatDate(e.getDataEmprestimo()),
            formatDate(e.getDataDevolucaoPrevista()),
            formatDate(e.getDataDevolucaoReal())
        );
    }

    private String formatDate(Date date) {
        if (date == null) return "";
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
    }

    private LocalDate dateToLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date localDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    private void onSairAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onSobreAction(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Sistema de Gerenciamento de Biblioteca v2.0");
    }
    //endregion
}
