package br.escola.biblioteca.ui;

import br.escola.biblioteca.dados.UsuarioRepository;
import br.escola.biblioteca.dados.UsuarioRepository.EmailJaCadastradoException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class TelaCadastro {

    private static final Pattern FORMATO_EMAIL =
            Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private final Stage stage;
    private final UsuarioRepository usuarios;

    public TelaCadastro(Stage stage, UsuarioRepository usuarios) {
        this.stage = stage;
        this.usuarios = usuarios;
    }

    public void exibir() {
        Label titulo = new Label("Criar conta");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome completo");
        campoNome.setMaxWidth(250);

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("E-mail");
        campoEmail.setMaxWidth(250);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setMaxWidth(250);

        DatePicker campoDataNascimento = new DatePicker();
        campoDataNascimento.setPromptText("Data de nascimento");
        campoDataNascimento.setMaxWidth(250);

        Button botaoCadastrar = new Button("Cadastrar");
        botaoCadastrar.setPrefWidth(250);
        Button botaoVoltar = new Button("Voltar para o login");
        botaoVoltar.setPrefWidth(250);

        botaoCadastrar.setOnAction(e -> {
            if (campoNome.getText().trim().isEmpty()
                    || campoEmail.getText().trim().isEmpty()
                    || campoSenha.getText().isEmpty()
                    || campoDataNascimento.getValue() == null) {
                avisar(Alert.AlertType.WARNING, "Preencha todos os campos.");
                return;
            }
            String email = campoEmail.getText().trim();
            if (!FORMATO_EMAIL.matcher(email).matches()) {
                avisar(Alert.AlertType.WARNING, "Informe um e-mail válido.");
                return;
        
            }
            LocalDate dataNascimento = campoDataNascimento.getValue();
            if (dataNascimento.isAfter(LocalDate.now())) {
                avisar(Alert.AlertType.WARNING, "A data de nascimento não pode estar no futuro.");
                return;
            }
            try {
                usuarios.cadastrar(campoNome.getText(), email, campoSenha.getText().toCharArray(),
                        dataNascimento);
                avisar(Alert.AlertType.INFORMATION, "Conta criada com sucesso. Faça login para continuar.");
                new TelaLogin(stage, usuarios, email).exibir();
            } catch (EmailJaCadastradoException erro) {
                avisar(Alert.AlertType.WARNING, erro.getMessage());
            } catch (SQLException | IllegalStateException erro) {
                avisar(Alert.AlertType.ERROR, "Não foi possível criar a conta: " + erro.getMessage());
            }
        });
        botaoVoltar.setOnAction(e -> new TelaLogin(stage, usuarios).exibir());

        VBox raiz = new VBox(15, titulo, campoNome, campoEmail, campoSenha, campoDataNascimento,
                botaoCadastrar, botaoVoltar);
        raiz.setAlignment(Pos.CENTER);
        raiz.setPadding(new Insets(20));

        stage.setScene(new Scene(raiz, 1000, 700));
        stage.show();
    } 
    private void avisar(Alert.AlertType tipo, String mensagem) {
        Alert alerta = new Alert(tipo, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
