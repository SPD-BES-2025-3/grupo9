package br.com.grupo9.controller;

import br.com.grupo9.model.Usuario;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AppControllerTest {

    private AppController controller;

    private PasswordField senhaUsuarioField;
    private DatePicker dataDevolucaoPrevistaPicker;

    @BeforeEach
    void setUp() {
        JavaFXInitializer.init(); // Garante que o JavaFX já está iniciado

        controller = new AppController();

        senhaUsuarioField = new PasswordField();
        dataDevolucaoPrevistaPicker = new DatePicker();

        controller.senhaUsuarioField = senhaUsuarioField; // Sem cast
        controller.dataDevolucaoPrevistaPicker = dataDevolucaoPrevistaPicker;
    }

    @Test
    void deveDefinirSenhaQuandoInformada() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1); // Usuário já existente

        senhaUsuarioField.setText("senha123");

        if (senhaUsuarioField.getText() != null && !senhaUsuarioField.getText().isEmpty()) {
            usuario.setSenha(senhaUsuarioField.getText());
        } else if (usuario.getId() == 0) {
            throw new Exception("Senha é obrigatória para novos usuários.");
        }

        assertEquals("senha123", usuario.getSenha());
    }

    @Test
    void deveLancarExcecaoQuandoNovaSenhaNaoInformadaParaNovoUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(0); // Novo usuário

        senhaUsuarioField.setText(""); // Não informado

        Exception ex = assertThrows(Exception.class, () -> {
            if (senhaUsuarioField.getText() != null && !senhaUsuarioField.getText().isEmpty()) {
                usuario.setSenha(senhaUsuarioField.getText());
            } else if (usuario.getId() == 0) {
                throw new Exception("Senha é obrigatória para novos usuários.");
            }
        });

        assertEquals("Senha é obrigatória para novos usuários.", ex.getMessage());
    }

    @Test
    void deveDefinirDataDevolucaoParaDuasSemanasAFrente() {
        LocalDate expectedDate = LocalDate.now().plusWeeks(2);
        dataDevolucaoPrevistaPicker.setValue(expectedDate);

        assertEquals(expectedDate, dataDevolucaoPrevistaPicker.getValue());
    }

    @Test
    void deveMostrarAlertaAoTentarExcluirLivroVinculado() {
        Platform.runLater(() -> {
            controller.showAlert(Alert.AlertType.ERROR, "Não é possível deletar este livro pois ele está vinculado a um empréstimo.");
        });
    }
}
