package br.escola.biblioteca.ui;

import br.escola.biblioteca.dados.UsuarioRepository;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaInicial {

    private static final String VERDE = "#2e7d32";
    private static final String VERDE_ESCURO = "#1b5e20";

    private final Stage stage;
    private final UsuarioRepository usuarios;
    private final String usuario;

    public TelaInicial(Stage stage, UsuarioRepository usuarios, String usuario) {
        this.stage = stage;
        this.usuarios = usuarios;
        this.usuario = usuario;
    }

    public void exibir() {
        VBox conteudo = new VBox();
        conteudo.setStyle("-fx-background-color: #f4f6f4;");

        VBox banner = criarBanner();
        VBox servicos = criarServicos();
        VBox avisos = criarAvisos();
        VBox sobre = criarSobre();
        VBox horarios = criarHorarios();
        VBox contato = criarContato();
        VBox rodape = criarRodape();
        conteudo.getChildren().addAll(banner, servicos, avisos, sobre, horarios, contato, rodape);

        ScrollPane rolagem = new ScrollPane(conteudo);
        rolagem.setFitToWidth(true);

        BorderPane raiz = new BorderPane();
        raiz.setTop(new VBox(criarBarraTopo(), criarCabecalho(),
                criarMenu(rolagem, conteudo, banner, servicos, avisos, sobre, horarios, contato)));
        raiz.setCenter(rolagem);

        stage.setScene(new Scene(raiz, 1000, 700));
        stage.show();
    }

    private HBox criarBarraTopo() {
        Label rotulo = new Label("Biblioteca Escolar");
        rotulo.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        Label rotuloUsuario = new Label("Usuário: " + usuario);
        rotuloUsuario.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        Button sair = new Button("Sair");
        sair.setStyle("-fx-background-color: transparent; -fx-text-fill: green;");
        sair.setOnAction(e -> new TelaLogin(stage, usuarios).exibir());

        HBox barra = new HBox(15, rotulo, espaco, rotuloUsuario, sair);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(6, 20, 6, 20));
        barra.setStyle("-fx-background-color: " + VERDE_ESCURO + ";");
        return barra;
    }

    private HBox criarCabecalho() {
        Label titulo = new Label("Sistema de Biblioteca Escolar");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + VERDE_ESCURO + ";");
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        TextField busca = new TextField();
        busca.setPromptText("Buscar no acervo (título, autor ou categoria)");
        busca.setPrefWidth(320);
        Button buscar = new Button("Buscar");
        buscar.setOnAction(e -> informar("A consulta ao acervo será implementada nas próximas entregas."));

        HBox cabecalho = new HBox(10, titulo, espaco, busca, buscar);
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.setPadding(new Insets(12, 20, 12, 20));
        cabecalho.setStyle("-fx-background-color: white;");
        return cabecalho;
    }

    private HBox criarMenu(ScrollPane rolagem, VBox conteudo, Node banner, Node servicos, Node avisos,
                           Node sobre, Node horarios, Node contato) {
        HBox menu = new HBox(5);
        menu.setPadding(new Insets(0, 20, 0, 20));
        menu.setStyle("-fx-background-color: " + VERDE + ";");
        menu.getChildren().addAll(
                itemMenu("Início", rolagem, conteudo, banner),
                itemMenu("Serviços", rolagem, conteudo, servicos),
                itemMenu("Avisos", rolagem, conteudo, avisos),
                itemMenu("Sobre", rolagem, conteudo, sobre),
                itemMenu("Horários e regras", rolagem, conteudo, horarios),
                itemMenu("Contato", rolagem, conteudo, contato));
        return menu;
    }

    private Button itemMenu(String texto, ScrollPane rolagem, VBox conteudo, Node alvo) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px;");
        botao.setOnAction(e -> {
            double extra = conteudo.getHeight() - rolagem.getViewportBounds().getHeight();
            if (extra <= 0) {
                return;
            }
            double y = alvo.getBoundsInParent().getMinY();
            rolagem.setVvalue(Math.min(1, Math.max(0, y / extra)));
        });
        return botao;
    }

    private VBox criarBanner() {
        Label titulo = new Label("Bem-vindo à Biblioteca Escolar");
        titulo.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label subtitulo = new Label("Consulte o acervo, acompanhe seus empréstimos, renove e reserve livros.");
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        VBox banner = new VBox(10, titulo, subtitulo);
        banner.setAlignment(Pos.CENTER);
        banner.setPadding(new Insets(50, 20, 50, 20));
        banner.setStyle("-fx-background-color: " + VERDE + ";");
        return banner;
    }

    private VBox criarServicos() {
        FlowPane cartoes = new FlowPane(15, 15);
        cartoes.getChildren().addAll(
                cartaoServico("Consultar acervo", "Pesquise livros por título, autor ou categoria."),
                cartaoServico("Meus empréstimos", "Veja prazos, situação e multas."),
                cartaoServico("Renovar empréstimo", "Prorrogue o prazo de devolução."),
                cartaoServico("Reservar livro", "Entre na fila de um livro sem exemplar disponível."),
                cartaoServico("Sugerir aquisição", "Indique um livro para ser comprado."),
                cartaoServico("Relatórios", "Empréstimos, atrasos e livros mais emprestados."));
        return secao("Serviços", cartoes);
    }

    private VBox cartaoServico(String titulo, String descricao) {
        Label t = new Label(titulo);
        t.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + VERDE_ESCURO + ";");
        Label d = new Label(descricao);
        d.setWrapText(true);
        Button abrir = new Button("Acessar");
        abrir.setOnAction(e -> informar("\"" + titulo + "\" será implementado nas próximas entregas."));
        VBox cartao = new VBox(8, t, d, abrir);
        cartao.setPrefWidth(270);
        cartao.setMinHeight(130);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("-fx-background-color: white; -fx-border-color: #cfd8cf; -fx-border-radius: 4;"
                + " -fx-background-radius: 4;");
        return cartao;
    }

    private VBox criarAvisos() {
        FlowPane cartoes = new FlowPane(15, 15);
        cartoes.getChildren().addAll(
                cartaoTexto("Novos livros no acervo",
                        "Chegaram novos títulos de literatura e didáticos. Consulte o acervo."),
                cartaoTexto("Devolução em dia",
                        "Devolva no prazo para evitar multa por atraso e manter o cadastro liberado."),
                cartaoTexto("Sugestões de professores",
                        "Professores podem sugerir a aquisição de livros pelo sistema."));
        return secao("Avisos", cartoes);
    }

    private VBox criarSobre() {
        Label texto = new Label("A Biblioteca Escolar reúne o acervo de livros da escola e atende alunos, "
                + "professores e funcionários. O sistema controla acervo, leitores, empréstimos, "
                + "devoluções, renovações, reservas e multas por atraso.");
        texto.setWrapText(true);
        return secao("Sobre a biblioteca", texto);
    }

    private VBox criarHorarios() {
        Label texto = new Label("Atendimento: segunda a sexta-feira, em horário a definir pela escola.\n"
                + "Limite de empréstimos: 3 livros para alunos e 5 para professores.\n"
                + "Prazo de empréstimo: 7 dias para alunos e 14 dias para professores.\n"
                + "Renovação: uma vez por empréstimo, por mais 7 dias, se não houver reserva.\n"
                + "Multa por atraso: R$ 0,50 por dia, por exemplar.");
        texto.setWrapText(true);
        return secao("Horários e regras", texto);
    }

    private VBox criarContato() {
        Label texto = new Label("E-mail: biblioteca@escola.edu.br\n"
                + "Telefone: (00) 0000-0000\n"
                + "Endereço: endereço da escola");
        texto.setWrapText(true);
        return secao("Contato", texto);
    }

    private VBox criarRodape() {
        Label texto = new Label("Sistema de Biblioteca Escolar — Projeto da disciplina Análise e Projeto de Sistemas");
        texto.setStyle("-fx-text-fill: white;");
        VBox rodape = new VBox(texto);
        rodape.setAlignment(Pos.CENTER);
        rodape.setPadding(new Insets(20));
        rodape.setStyle("-fx-background-color: " + VERDE_ESCURO + ";");
        return rodape;
    }

    private VBox cartaoTexto(String titulo, String texto) {
        Label t = new Label(titulo);
        t.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + VERDE_ESCURO + ";");
        Label d = new Label(texto);
        d.setWrapText(true);
        VBox cartao = new VBox(8, t, d);
        cartao.setPrefWidth(270);
        cartao.setPadding(new Insets(15));
        cartao.setStyle("-fx-background-color: white; -fx-border-color: #cfd8cf; -fx-border-radius: 4;"
                + " -fx-background-radius: 4;");
        return cartao;
    }

    private VBox secao(String titulo, Node corpo) {
        Label t = new Label(titulo);
        t.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + VERDE_ESCURO + ";");
        VBox secao = new VBox(12, t, corpo);
        secao.setPadding(new Insets(25, 30, 25, 30));
        return secao;
    }

    private void informar(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensagem);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
