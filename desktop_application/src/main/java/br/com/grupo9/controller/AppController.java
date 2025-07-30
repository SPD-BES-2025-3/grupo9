package br.com.grupo9.controller;

import br.com.grupo9.model.*;
import br.com.grupo9.view.EmprestimoView;
import br.com.grupo9.view.LivroView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private enum FormState {INITIAL, VIEWING, ADDING, EDITING}

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // --- REPOSITÓRIOS ---
    private final LivroRepository livroRepo = new LivroRepository();
    private final AutorRepository autorRepo = new AutorRepository();
    private final EditoraRepository editoraRepo = new EditoraRepository();
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();
    private final EmprestimoRepository emprestimoRepo = new EmprestimoRepository();

    // --- COMPONENTES FXML: EMPRÉSTIMO ---
    @FXML
    private TextField idEmprestimoField;
    @FXML
    private ComboBox<Usuario> emprestimoUsuarioComboBox;
    @FXML
    private ComboBox<Livro> emprestimoLivroComboBox;
    @FXML
    private DatePicker dataEmprestimoPicker;
    @FXML
    private DatePicker dataDevolucaoPrevistaPicker;
    @FXML
    private DatePicker dataDevolucaoRealPicker;
    @FXML
    private Button adicionarEmprestimoButton;
    @FXML
    private Button atualizarEmprestimoButton;
    @FXML
    private Button salvarEmprestimoButton;
    @FXML
    private Button cancelarEmprestimoButton;
    @FXML
    private Button deletarEmprestimoButton;
    @FXML
    private TableView<EmprestimoView> tabelaEmprestimo;
    @FXML
    private TableColumn<EmprestimoView, Integer> idEmprestimoCol;
    @FXML
    private TableColumn<EmprestimoView, String> usuarioEmprestimoCol;
    @FXML
    private TableColumn<EmprestimoView, String> livroEmprestimoCol;
    @FXML
    private TableColumn<EmprestimoView, String> dataEmprestimoCol;
    @FXML
    private TableColumn<EmprestimoView, String> dataDevolucaoPrevistaCol;
    @FXML
    private TableColumn<EmprestimoView, String> dataDevolucaoRealCol;

    // --- COMPONENTES FXML: LIVRO ---
    @FXML
    private TextField idLivroField;
    @FXML
    private TextField tituloLivroField;
    @FXML
    private TextField anoPublicacaoLivroField;
    @FXML
    private TextField precoLivroField;
    @FXML
    private ComboBox<Autor> livroAutorComboBox;
    @FXML
    private ComboBox<Editora> livroEditoraComboBox;
    @FXML
    private Button adicionarLivroButton;
    @FXML
    private Button atualizarLivroButton;
    @FXML
    private Button deletarLivroButton;
    @FXML
    private Button salvarLivroButton;
    @FXML
    private Button cancelarLivroButton;
    @FXML
    private TableView<LivroView> tabelaLivro;
    @FXML
    private TableColumn<LivroView, Integer> idLivroCol;
    @FXML
    private TableColumn<LivroView, String> tituloLivroCol;
    @FXML
    private TableColumn<LivroView, String> autorLivroCol;
    @FXML
    private TableColumn<LivroView, String> editoraLivroCol;
    @FXML
    private TableColumn<LivroView, Integer> anoPublicacaoLivroCol;
    @FXML
    private TableColumn<LivroView, Double> precoLivroCol;

    // --- COMPONENTES FXML: AUTOR ---
    @FXML
    private TextField idAutorField;
    @FXML
    private TextField nomeAutorField;
    @FXML
    private TextField nacionalidadeAutorField;
    @FXML
    private Button adicionarAutorButton;
    @FXML
    private Button atualizarAutorButton;
    @FXML
    private Button deletarAutorButton;
    @FXML
    private Button salvarAutorButton;
    @FXML
    private Button cancelarAutorButton;
    @FXML
    private TableView<Autor> tabelaAutor;
    @FXML
    private TableColumn<Autor, Integer> idAutorCol;
    @FXML
    private TableColumn<Autor, String> nomeAutorCol;
    @FXML
    private TableColumn<Autor, String> nacionalidadeAutorCol;

    // --- COMPONENTES FXML: EDITORA ---
    @FXML
    private TextField idEditoraField;
    @FXML
    private TextField nomeEditoraField;
    @FXML
    private TextField enderecoEditoraField;
    @FXML
    private TextField telefoneEditoraField;
    @FXML
    private Button adicionarEditoraButton;
    @FXML
    private Button atualizarEditoraButton;
    @FXML
    private Button deletarEditoraButton;
    @FXML
    private Button salvarEditoraButton;
    @FXML
    private Button cancelarEditoraButton;
    @FXML
    private TableView<Editora> tabelaEditora;
    @FXML
    private TableColumn<Editora, Integer> idEditoraCol;
    @FXML
    private TableColumn<Editora, String> nomeEditoraCol;
    @FXML
    private TableColumn<Editora, String> enderecoEditoraCol;
    @FXML
    private TableColumn<Editora, String> telefoneEditoraCol;

    // --- COMPONENTES FXML: USUÁRIO ---
    @FXML
    private TextField idUsuarioField;
    @FXML
    private TextField nomeUsuarioField;
    @FXML
    private TextField emailUsuarioField;
    @FXML
    private TextField telefoneUsuarioField;
    @FXML
    private PasswordField senhaUsuarioField;
    @FXML
    private Button adicionarUsuarioButton;
    @FXML
    private Button atualizarUsuarioButton;
    @FXML
    private Button deletarUsuarioButton;
    @FXML
    private Button salvarUsuarioButton;
    @FXML
    private Button cancelarUsuarioButton;
    @FXML
    private TableView<Usuario> tabelaUsuario;
    @FXML
    private TableColumn<Usuario, Integer> idUsuarioCol;
    @FXML
    private TableColumn<Usuario, String> nomeUsuarioCol;
    @FXML
    private TableColumn<Usuario, String> emailUsuarioCol;
    @FXML
    private TableColumn<Usuario, String> telefoneUsuarioCol;
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

    @FXML
    private void handleAdicionarEmprestimo() {
        clearEmprestimoForm();
        setEmprestimoFormState(FormState.ADDING);
        dataEmprestimoPicker.setValue(LocalDate.now());
        dataDevolucaoPrevistaPicker.setValue(LocalDate.now().plusWeeks(2));
    }

    @FXML
    private void handleAtualizarEmprestimo() {
        if (tabelaEmprestimo.getSelectionModel().getSelectedItem() != null) {
            setEmprestimoFormState(FormState.EDITING);
        }
    }

    @FXML
    private void handleSalvarEmprestimo() {
        try {
            if (emprestimoUsuarioComboBox.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Por favor, selecione um usuário.");
                return;
            }
            if (emprestimoLivroComboBox.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Por favor, selecione um livro.");
                return;
            }
            if (dataEmprestimoPicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Por favor, informe a data do empréstimo.");
                return;
            }
            if (dataDevolucaoPrevistaPicker.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Por favor, informe a data de devolução prevista.");
                return;
            }

            Emprestimo emprestimo;
            boolean isNew = idEmprestimoField.getText().isEmpty();

            if (isNew) {
                emprestimo = new Emprestimo();
            } else {
                emprestimo = emprestimoRepo.loadById(Integer.parseInt(idEmprestimoField.getText()));
                if (emprestimo == null)
                    throw new IllegalStateException("Empréstimo não encontrado para o ID fornecido.");
            }

            emprestimo.setUsuario(emprestimoUsuarioComboBox.getValue());
            emprestimo.setLivro(emprestimoLivroComboBox.getValue());
            emprestimo.setDataEmprestimo(localDateToDate(dataEmprestimoPicker.getValue()));
            emprestimo.setDataDevolucaoPrevista(localDateToDate(dataDevolucaoPrevistaPicker.getValue()));
            emprestimo.setDataDevolucaoReal(localDateToDate(dataDevolucaoRealPicker.getValue()));

            Emprestimo emprestimoSalvo;
            if (isNew) {
                emprestimoSalvo = emprestimoRepo.create(emprestimo);
                showAlert(Alert.AlertType.INFORMATION, "Empréstimo registrado!");
            } else {
                emprestimoRepo.update(emprestimo);
                emprestimoSalvo = emprestimo;
                showAlert(Alert.AlertType.INFORMATION, "Empréstimo atualizado!");
            }

            if (emprestimoSalvo != null) {
                loadEmprestimosData();
                tabelaEmprestimo.getSelectionModel().select(emprestimoModelToView(emprestimoSalvo));
            } else {
                showAlert(Alert.AlertType.ERROR, "O empréstimo não pôde ser salvo no banco de dados.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ocorreu um erro ao salvar:\n" + e.toString());
        }
    }

    @FXML
    private void handleCancelarEmprestimo() {
        handleEmprestimoSelected(tabelaEmprestimo.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeletarEmprestimo() {
        EmprestimoView selected = tabelaEmprestimo.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Emprestimo emprestimoParaDeletar = emprestimoRepo.loadById(selected.getId());
            if (emprestimoParaDeletar != null) {
                emprestimoRepo.delete(emprestimoParaDeletar);
                showAlert(Alert.AlertType.INFORMATION, "Registro de empréstimo excluído.");
                loadEmprestimosData();
            }
        }
    }

    private void setEmprestimoFormState(FormState state) {
        boolean disabled = (state == FormState.INITIAL || state == FormState.VIEWING);
        boolean isViewing = state == FormState.VIEWING;

        emprestimoUsuarioComboBox.setDisable(disabled);
        emprestimoLivroComboBox.setDisable(disabled);
        dataEmprestimoPicker.setDisable(disabled);
        dataDevolucaoPrevistaPicker.setDisable(disabled);
        dataDevolucaoRealPicker.setDisable(disabled);
        adicionarEmprestimoButton.setDisable(!disabled);
        atualizarEmprestimoButton.setDisable(!isViewing);
        deletarEmprestimoButton.setDisable(!isViewing);
        salvarEmprestimoButton.setDisable(disabled);
        cancelarEmprestimoButton.setDisable(disabled);
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
        livroAutorComboBox.setItems(FXCollections.observableArrayList(autorRepo.loadAll()));
        livroEditoraComboBox.setItems(FXCollections.observableArrayList(editoraRepo.loadAll()));
        tabelaLivro.setItems(FXCollections.observableArrayList(
                livroRepo.loadAll().stream().map(this::livroModelToView).collect(Collectors.toList())
        ));
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

    @FXML
    private void handleAdicionarLivro() {
        clearLivroForm();
        setLivroFormState(FormState.ADDING);
    }

    @FXML
    private void handleAtualizarLivro() {
        if (tabelaLivro.getSelectionModel().getSelectedItem() != null) setLivroFormState(FormState.EDITING);
    }

    @FXML
    private void handleSalvarLivro() {
        try {
            Livro livro;
            if (idLivroField.getText().isEmpty()) {
                livro = new Livro();
            } else {
                livro = livroRepo.loadById(Integer.parseInt(idLivroField.getText()));
                if (livro == null) throw new IllegalStateException("Livro não encontrado para o ID fornecido.");
            }

            livro.setTitulo(tituloLivroField.getText());
            livro.setAnoPublicacao(Integer.parseInt(anoPublicacaoLivroField.getText()));
            livro.setPreco(Double.parseDouble(precoLivroField.getText().replace(",", ".")));
            livro.setAutor(livroAutorComboBox.getValue());
            livro.setEditora(livroEditoraComboBox.getValue());

            Livro livroSalvo;
            if (livro.getId() == 0) {
                livroSalvo = livroRepo.create(livro);
                showAlert(Alert.AlertType.INFORMATION, "Livro salvo!");
            } else {
                livroRepo.update(livro);
                livroSalvo = livro;
                showAlert(Alert.AlertType.INFORMATION, "Livro atualizado!");
            }

            if (livroSalvo != null) {
                loadLivrosData();
                loadEmprestimosData();
                tabelaLivro.getSelectionModel().select(livroModelToView(livroSalvo));
            } else {
                showAlert(Alert.AlertType.ERROR, "O livro não pôde ser salvo no banco de dados.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ocorreu um erro ao salvar o livro:\n" + e.toString());
        }
    }

    @FXML
    private void handleCancelarLivro() {
        handleLivroSelected(tabelaLivro.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeletarLivro() {
        LivroView selected = tabelaLivro.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Livro livroParaDeletar = livroRepo.loadById(selected.getId());
            if (livroParaDeletar != null) {
                try {
                    livroRepo.delete(livroParaDeletar);
                    showAlert(Alert.AlertType.INFORMATION, "Livro deletado.");
                    loadLivrosData();
                    loadEmprestimosData();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Não é possível deletar este livro pois ele está vinculado a um empréstimo.");
                }
            }
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

    @FXML
    private void handleAdicionarAutor() {
        clearAutorForm();
        setAutorFormState(FormState.ADDING);
    }

    @FXML
    private void handleAtualizarAutor() {
        if (tabelaAutor.getSelectionModel().getSelectedItem() != null) setAutorFormState(FormState.EDITING);
    }

    @FXML
    private void handleSalvarAutor() {
        try {
            Autor autor;
            if (idAutorField.getText().isEmpty()) {
                autor = new Autor();
            } else {
                autor = autorRepo.loadById(Integer.parseInt(idAutorField.getText()));
                if (autor == null) throw new IllegalStateException("Autor não encontrado para o ID fornecido.");
            }
            autor.setNome(nomeAutorField.getText());
            autor.setNacionalidade(nacionalidadeAutorField.getText());

            Autor autorSalvo;
            if (autor.getId() == 0) {
                autorSalvo = autorRepo.create(autor);
                showAlert(Alert.AlertType.INFORMATION, "Autor salvo!");
            } else {
                autorRepo.update(autor);
                autorSalvo = autor;
                showAlert(Alert.AlertType.INFORMATION, "Autor atualizado!");
            }

            if (autorSalvo != null) {
                loadAutoresData();
                loadLivrosData();
                tabelaAutor.getSelectionModel().select(autorSalvo);
            } else {
                showAlert(Alert.AlertType.ERROR, "O autor não pôde ser salvo no banco de dados.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ocorreu um erro ao salvar o autor:\n" + e.toString());
        }
    }

    @FXML
    private void handleCancelarAutor() {
        handleAutorSelected(tabelaAutor.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeletarAutor() {
        Autor selected = tabelaAutor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                autorRepo.delete(selected);
                showAlert(Alert.AlertType.INFORMATION, "Autor deletado.");
                loadAutoresData();
                loadLivrosData();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Não é possível deletar este autor pois ele está vinculado a um ou mais livros.");
            }
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

    @FXML
    private void handleAdicionarEditora() {
        clearEditoraForm();
        setEditoraFormState(FormState.ADDING);
    }

    @FXML
    private void handleAtualizarEditora() {
        if (tabelaEditora.getSelectionModel().getSelectedItem() != null) setEditoraFormState(FormState.EDITING);
    }

    @FXML
    private void handleSalvarEditora() {
        try {
            Editora editora;
            if (idEditoraField.getText().isEmpty()) {
                editora = new Editora();
            } else {
                editora = editoraRepo.loadById(Integer.parseInt(idEditoraField.getText()));
                if (editora == null) throw new IllegalStateException("Editora não encontrada para o ID fornecido.");
            }
            editora.setNome(nomeEditoraField.getText());
            editora.setEndereco(enderecoEditoraField.getText());
            editora.setTelefone(telefoneEditoraField.getText());

            Editora editoraSalva;
            if (editora.getId() == 0) {
                editoraSalva = editoraRepo.create(editora);
                showAlert(Alert.AlertType.INFORMATION, "Editora salva!");
            } else {
                editoraRepo.update(editora);
                editoraSalva = editora;
                showAlert(Alert.AlertType.INFORMATION, "Editora atualizada!");
            }

            if (editoraSalva != null) {
                loadEditorasData();
                loadLivrosData();
                tabelaEditora.getSelectionModel().select(editoraSalva);
            } else {
                showAlert(Alert.AlertType.ERROR, "A editora não pôde ser salva no banco de dados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ocorreu um erro ao salvar a editora:\n" + e.toString());
        }
    }

    @FXML
    private void handleCancelarEditora() {
        handleEditoraSelected(tabelaEditora.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeletarEditora() {
        Editora selected = tabelaEditora.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                editoraRepo.delete(selected);
                showAlert(Alert.AlertType.INFORMATION, "Editora deletada.");
                loadEditorasData();
                loadLivrosData();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Não é possível deletar esta editora pois ela está vinculada a um ou mais livros.");
            }
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

    @FXML
    private void handleAdicionarUsuario() {
        clearUsuarioForm();
        setUsuarioFormState(FormState.ADDING);
    }

    @FXML
    private void handleAtualizarUsuario() {
        if (tabelaUsuario.getSelectionModel().getSelectedItem() != null) setUsuarioFormState(FormState.EDITING);
    }

    @FXML
    private void handleSalvarUsuario() {
        try {
            Usuario usuario;
            if (idUsuarioField.getText().isEmpty()) {
                usuario = new Usuario();
            } else {
                usuario = usuarioRepo.loadById(Integer.parseInt(idUsuarioField.getText()));
                if (usuario == null) throw new IllegalStateException("Usuário não encontrado para o ID fornecido.");
            }
            usuario.setNome(nomeUsuarioField.getText());
            usuario.setEmail(emailUsuarioField.getText());
            usuario.setTelefone(telefoneUsuarioField.getText());

            if (senhaUsuarioField.getText() != null && !senhaUsuarioField.getText().isEmpty()) {
                usuario.setSenha(senhaUsuarioField.getText()); // Idealmente, usar hash
            } else if (usuario.getId() == 0) {
                throw new Exception("Senha é obrigatória para novos usuários.");
            }

            Usuario usuarioSalvo;
            if (usuario.getId() == 0) {
                usuarioSalvo = usuarioRepo.create(usuario);
                showAlert(Alert.AlertType.INFORMATION, "Usuário salvo!");
            } else {
                usuarioRepo.update(usuario);
                usuarioSalvo = usuario;
                showAlert(Alert.AlertType.INFORMATION, "Usuário atualizado!");
            }

            if (usuarioSalvo != null) {
                loadUsuariosData();
                loadEmprestimosData();
                tabelaUsuario.getSelectionModel().select(usuarioSalvo);
            } else {
                showAlert(Alert.AlertType.ERROR, "O usuário não pôde ser salvo no banco de dados.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ocorreu um erro ao salvar o usuário:\n" + e.toString());
        }
    }

    @FXML
    private void handleCancelarUsuario() {
        handleUsuarioSelected(tabelaUsuario.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleDeletarUsuario() {
        Usuario selected = tabelaUsuario.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                usuarioRepo.delete(selected);
                showAlert(Alert.AlertType.INFORMATION, "Usuário deletado.");
                loadUsuariosData();
                loadEmprestimosData();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Não é possível deletar este usuário pois ele está vinculado a um ou mais empréstimos.");
            }
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
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate().format(formatter);
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(formatter);
    }

    private LocalDate dateToLocalDate(Date date) {
        if (date == null) return null;
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date localDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return java.sql.Date.valueOf(localDate);
    }

    @FXML
    private void onSairAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onSobreAction(ActionEvent event) {
        showAlert(Alert.AlertType.INFORMATION, "Sistema de Gerenciamento de Biblioteca v2.1 - JPA Edition");
    }
//endregion
}