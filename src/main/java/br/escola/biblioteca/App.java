package br.escola.biblioteca;

import br.escola.biblioteca.dados.UsuarioRepository;
import br.escola.biblioteca.ui.TelaLogin;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sistema de Biblioteca Escolar");
        try {
            new TelaLogin(stage, new UsuarioRepository()).exibir();
        } catch (IOException | SQLException erro) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro ao iniciar");
            alerta.setHeaderText("Não foi possível abrir o banco de dados.");
            alerta.setContentText(erro.getMessage());
            alerta.showAndWait();
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}