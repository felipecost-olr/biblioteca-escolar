package br.escola.biblioteca.ui;

import br.escola.biblioteca.dados.UsuarioRepository;
import br.escola.biblioteca.dados.UsuarioRepository.ResultadoLogin;
import br.escola.biblioteca.dados.UsuarioRepository.StatusLogin;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.sql.SQLException;

public class TelaLogin {

    private static final String VERDE = "#2e7d32";
    private static final String VERDE_ESCURO = "#1b5e20";

    private final Stage stage;
    private final UsuarioRepository usuarios;
    private final String emailInicial;

    public TelaLogin(Stage stage, UsuarioRepository usuarios) {
        this(stage, usuarios, "");
    }

    public TelaLogin(Stage stage, UsuarioRepository usuarios, String emailInicial) {
        this.stage = stage;
        this.usuarios = usuarios;
        this.emailInicial = emailInicial;
    }

    public void exibir() {
        Label marca = new Label("Biblioteca Escolar");
        marca.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: " + VERDE_ESCURO + ";");
        marca.setMaxWidth(Double.MAX_VALUE);
        marca.setAlignment(Pos.CENTER);

        Label titulo = new Label("Login");
        titulo.setStyle("-fx-font-size: 20px;");
        titulo.setMaxWidth(Double.MAX_VALUE);
        titulo.setAlignment(Pos.CENTER);

        Hyperlink linkCriar = new Hyperlink("Criar conta");
        linkCriar.setOnAction(e -> new TelaCadastro(stage, usuarios).exibir());
        HBox linhaConta = new HBox(4, new Label("Não tem conta?"), linkCriar);
        linhaConta.setAlignment(Pos.CENTER);

        Label rotuloEmail = new Label("E-mail");
        rotuloEmail.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        TextField campoEmail = new TextField(emailInicial);
        campoEmail.setPromptText("seu@email.com");
        campoEmail.setMaxWidth(Double.MAX_VALUE);

        Label rotuloSenha = new Label("Senha");
        rotuloSenha.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        PasswordField campoSenha = new PasswordField();
        campoSenha.setMaxWidth(Double.MAX_VALUE);

        Button botaoEntrar = new Button("Entrar");
        botaoEntrar.setMaxWidth(Double.MAX_VALUE);
        botaoEntrar.setStyle("-fx-background-color: " + VERDE
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 0 8 0;");

        Label erro = new Label();
        erro.setStyle("-fx-text-fill: #c62828;");
        erro.setWrapText(true);
        erro.setMaxWidth(Double.MAX_VALUE);
        erro.setAlignment(Pos.CENTER);
        erro.setMinHeight(20);

        botaoEntrar.setOnAction(e -> {
            String email = campoEmail.getText().trim();
            if (email.isEmpty() || campoSenha.getText().isEmpty()) {
                erro.setText("Informe e-mail e senha.");
                return;
            }

            try {
                ResultadoLogin resultado = usuarios.autenticar(email, campoSenha.getText().toCharArray());
                if (resultado.status() == StatusLogin.USUARIO_INEXISTENTE) {
                    erro.setText("Usuário inexistente. Confira o e-mail ou crie uma conta.");
                    campoSenha.clear();
                    return;
                }
                if (resultado.status() == StatusLogin.SENHA_INCORRETA) {
                    erro.setText("E-mail ou senha incorretos.");
                    campoSenha.clear();
                    return;
                }

                new TelaInicial(stage, usuarios, resultado.nome()).exibir();
            } catch (SQLException | IllegalStateException excecao) {
                erro.setText("Não foi possível verificar o login: " + excecao.getMessage());
            } finally {
                campoSenha.clear();
            }
        });
        campoSenha.setOnAction(e -> botaoEntrar.fire());

        VBox formulario = new VBox(10, marca, titulo, linhaConta,
                rotuloEmail, campoEmail, rotuloSenha, campoSenha, botaoEntrar, erro);
        formulario.setAlignment(Pos.CENTER);
        formulario.setPadding(new Insets(40));
        formulario.setPrefWidth(400);
        formulario.setMinWidth(400);
        formulario.setMaxWidth(400);
        formulario.setStyle("-fx-background-color: white;");

        StackPane lateral = criarLateral();
        HBox.setHgrow(lateral, Priority.ALWAYS);

        stage.setScene(new Scene(new HBox(formulario, lateral), 1000, 700));
        stage.show();
    }

    private StackPane criarLateral() {
        StackPane lateral = new StackPane();
        lateral.setMaxWidth(Double.MAX_VALUE);
        Image imagem = carregarImagem();
        if (imagem != null) {
            lateral.setBackground(new Background(new BackgroundImage(imagem,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, true))));
        } else {
            lateral.setStyle("-fx-background-color: linear-gradient(to bottom right, "
                    + VERDE_ESCURO + ", #66bb6a);");
            Label texto = new Label("Sistema de Biblioteca Escolar");
            texto.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");
            lateral.getChildren().add(texto);
        }
        return lateral;
    }

    private Image carregarImagem() {
        String[] nomes = {"/imagens/login.png", "/imagens/login.jpg", "/imagens/login.jpeg"};
        for (String nome : nomes) {
            InputStream in = getClass().getResourceAsStream(nome);
            if (in != null) {
                return new Image(in);
            }
        }
        return null;
    }
}
