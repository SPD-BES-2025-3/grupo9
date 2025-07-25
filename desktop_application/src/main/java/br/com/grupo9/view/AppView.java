package br.com.grupo9.view;

import br.com.grupo9.model.JPAUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import br.com.grupo9.model.RedisUtil;

import java.net.URL;

/**
 * Interface Gráfica (GUI)
 * Classe principal da aplicação JavaFX.
 * @author stephanymilhomem
 * @version 2.0 (JPA Edition)
 */
public class AppView extends Application {

    private final FXMLLoader loader;
    private final URL url;

    /**
     * Construtor da AppView
     * Inicializa o FXMLLoader e define a localização do arquivo FXML a partir dos recursos.
     */
    public AppView() {
        this.loader = new FXMLLoader();
        this.url = getClass().getResource("/view/app.fxml");

        if (this.url == null) {
            throw new RuntimeException("Falha ao localizar o arquivo FXML: /view/app.fxml. Verifique o caminho em src/main/resources.");
        }
        this.loader.setLocation(this.url);
    }

    /**
     * Método start do JavaFX Application.
     * Carrega o FXML e exibe a janela principal.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle("Sistema de Gerenciamento de Livros - CRUD (JPA)");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(600);
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Erro fatal ao carregar a interface: " + e.getMessage());
            e.printStackTrace();
            // Encerra o JPA se a UI falhar ao iniciar.
            JPAUtil.shutdown();
            System.exit(1);
        }
    }

    /**
     * Método stop do JavaFX Application.
     * É executado quando a aplicação é fechada e é o local ideal para limpar recursos.
     */
    @Override
    public void stop() {
        System.out.println("Encerrando a aplicação...");
        JPAUtil.shutdown();
        RedisUtil.shutdown();
        System.out.println("Recursos liberados. Aplicação encerrada.");
    }

    /**
     * Ponto de entrada principal para executar a aplicação.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
