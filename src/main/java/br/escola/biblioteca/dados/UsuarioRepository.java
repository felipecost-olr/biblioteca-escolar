package br.escola.biblioteca.dados;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class UsuarioRepository {

    private static final int SALT_TAMANHO = 16;
    private static final int HASH_TAMANHO = 32;
    private static final int ITERACOES_HASH = 210_000;
    private static final SecureRandom ALEATORIO = new SecureRandom();

    private final String urlBanco;

    public UsuarioRepository() throws IOException, SQLException {
        Path diretorio = Path.of(System.getProperty("user.home"), ".biblioteca-escolar");
        Files.createDirectories(diretorio);
        urlBanco = "jdbc:sqlite:" + diretorio.resolve("biblioteca.db").toAbsolutePath();

        try (Connection conexao = conectar();
             Statement comando = conexao.createStatement()) {
            comando.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome TEXT NOT NULL,
                        email TEXT NOT NULL COLLATE NOCASE UNIQUE,
                        data_nascimento TEXT NOT NULL,
                        senha_salt BLOB NOT NULL,
                        senha_hash BLOB NOT NULL
                    )
                    """);
        }
    }

    public void cadastrar(String nome, String email, char[] senha, LocalDate dataNascimento)
            throws SQLException, EmailJaCadastradoException {
        try {
            byte[] salt = new byte[SALT_TAMANHO];
            ALEATORIO.nextBytes(salt);
            byte[] hash = gerarHash(senha, salt);

            try (Connection conexao = conectar();
                 PreparedStatement comando = conexao.prepareStatement("""
                         INSERT INTO usuarios (nome, email, data_nascimento, senha_salt, senha_hash)
                         VALUES (?, ?, ?, ?, ?)
                         """)) {
                comando.setString(1, nome.trim());
                comando.setString(2, normalizarEmail(email));
                comando.setString(3, dataNascimento.toString());
                comando.setBytes(4, salt);
                comando.setBytes(5, hash);
                comando.executeUpdate();
            } catch (SQLException erro) {
                if (erro.getErrorCode() == 19) {
                    throw new EmailJaCadastradoException();
                }
                throw erro;
            } finally {
                Arrays.fill(hash, (byte) 0);
                Arrays.fill(salt, (byte) 0);
            }
        } finally {
            Arrays.fill(senha, '\0');
        }
    }

    public ResultadoLogin autenticar(String email, char[] senha) throws SQLException {
        try (Connection conexao = conectar();
             PreparedStatement comando = conexao.prepareStatement("""
                     SELECT nome, senha_salt, senha_hash
                     FROM usuarios
                     WHERE email = ?
                     """)) {
            comando.setString(1, normalizarEmail(email));
            try (ResultSet resultado = comando.executeQuery()) {
                if (!resultado.next()) {
                    return new ResultadoLogin(StatusLogin.USUARIO_INEXISTENTE, null);
                }

                byte[] salt = resultado.getBytes("senha_salt");
                byte[] hashSalvo = resultado.getBytes("senha_hash");
                byte[] hashInformado = gerarHash(senha, salt);
                try {
                    if (!MessageDigest.isEqual(hashSalvo, hashInformado)) {
                        return new ResultadoLogin(StatusLogin.SENHA_INCORRETA, null);
                    }
                    return new ResultadoLogin(StatusLogin.AUTENTICADO, resultado.getString("nome"));
                } finally {
                    Arrays.fill(salt, (byte) 0);
                    Arrays.fill(hashSalvo, (byte) 0);
                    Arrays.fill(hashInformado, (byte) 0);
                }
            }
        } finally {
            Arrays.fill(senha, '\0');
        }
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(urlBanco);
    }

    private static String normalizarEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private static byte[] gerarHash(char[] senha, byte[] salt) {
        PBEKeySpec especificacao = new PBEKeySpec(senha, salt, ITERACOES_HASH, HASH_TAMANHO * 8);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(especificacao)
                    .getEncoded();
        } catch (GeneralSecurityException erro) {
            throw new IllegalStateException("Não foi possível proteger a senha.", erro);
        } finally {
            especificacao.clearPassword();
        }
    }

    public enum StatusLogin {
        AUTENTICADO,
        USUARIO_INEXISTENTE,
        SENHA_INCORRETA
    }

    public record ResultadoLogin(StatusLogin status, String nome) {
    }

    public static final class EmailJaCadastradoException extends Exception {
        private static final long serialVersionUID = 1L;

        private EmailJaCadastradoException() {
            super("Já existe uma conta cadastrada com esse e-mail.");
        }
    }
}
