package br.edu.ifpi.biblioteca;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicação. Na semana 1 abre apenas uma janela vazia;
 * as telas reais serão adicionadas a cada entrega, rastreadas pelos diagramas.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new StackPane(), 960, 600);
        stage.setTitle("Biblioteca Escolar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
