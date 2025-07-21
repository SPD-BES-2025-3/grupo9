package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;

/**
 * Interface Gráfica (GUI) do CRUD para Livros
 * Classe principal da aplicação JavaFX
 * * @author stephanymilhomem
 * @version 1.0
 */
public class AppView extends Application {

    private FXMLLoader loader;
    private URL url;
    private Stage primaryStage;

    /**
     * Construtor da AppView
     * Inicializa o FXMLLoader e define a localização do arquivo FXML
     */
    public AppView() {
        this.loader = new FXMLLoader();
        try {
            this.url = new File("view/app.fxml").toURI().toURL(); 
        } catch (Exception e) {
            System.out.println("Erro na carga do FXML: " + e);
        }
        this.loader.setLocation(this.url);
    }

    /**
     * Método start do JavaFX Application
     * Carrega o FXML e exibe a janela principal
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            VBox root = loader.<VBox>load();
            Scene scene = new Scene(root);

            this.primaryStage = primaryStage;
            primaryStage.setTitle("Sistema de Gerenciamento de Livros - CRUD");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(600);
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Erro ao carregar a interface: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); 
        }
    }

    /**
     * Método stop do JavaFX Application
     * Executado quando a aplicação é fechada
     */
    @Override
    public void stop() {
        System.out.println("Aplicação encerrada.");
        System.exit(0);
    }

    /**
     * Método para executar a aplicação
     */
    public void run(String[] args) {
        Application.launch(args);
    }
}