package DAO;

import Factory.ConnectionFactory;
import Models.Paciente;

import java.sql.*;
import java.time.LocalDateTime;

public class PessoaDAO {

    private String tableName = "pessoas";
    private Connection connection = new ConnectionFactory().getConnection();

    public void criarTabela() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS lectures_id_seq;";

        sql += "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                    "id BIGSERIAL PRIMARY KEY, " +
                    "nome VARCHAR(50), " +
                    "telefone VARCHAR(12), " +
                    "nacionalidade VARCHAR(50), " +
                    "cpf VARCHAR(10), " +
                    "rg VARCHAR(10), " +
                    "email VARCHAR(50), " +
                    "login VARCHAR(50), " +
                    "senha VARCHAR(100), " +
                    "sexo VARCHAR(2), " +
                    "cadastro TIMESTAMP NOT NULL, " +
                    "atualizado TIMESTAMP NOT NULL, " +
                    "excluido TIMESTAMP NOT NULL " +
                ");";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Paciente criar(Paciente paciente) {
        if (paciente != null) {
            String sql = "INSERT INTO pessoas (" +
                    "nome, telefone, nacionalidade, " +
                    "cpf, rg, email, login, senha, sexo, cadastro) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                paciente.setCadastro(LocalDateTime.now());

                statement.setString(1, paciente.getNome());
                statement.setString(2, paciente.getTelefone());
                statement.setString(3, paciente.getNacionalidade());
                statement.setString(5, paciente.getCpf());
                statement.setString(6, paciente.getRg());
                statement.setString(7, paciente.getEmail());
                statement.setString(8, paciente.getLogin());
                statement.setString(9, paciente.getSenha());
                statement.setTimestamp(10, Timestamp.valueOf(paciente.getCadastro()));

                statement.execute();

                ResultSet resultSet = statement.getGeneratedKeys();

                while(resultSet.next()){
                    paciente.setId(resultSet.getLong("id"));
                }
                return paciente;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public Paciente editar(Paciente paciente) {
        if (paciente != null) {
            String sql = "UPDATE pessoas SET " +
                    "nome = ?, telefone = ?, nacionalidade = ?, " +
                    "cpf = ?, rg = ?, email = ?, login = ?, senha = ?, sexo = ?, cadastro = ?, " +
                    "atualizado = ? ";

            try {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                paciente.setAtualizado(LocalDateTime.now());

                statement.setString(1, paciente.getNome());
                statement.setString(2, paciente.getTelefone());
                statement.setString(3, paciente.getNacionalidade());
                statement.setString(5, paciente.getCpf());
                statement.setString(6, paciente.getRg());
                statement.setString(7, paciente.getEmail());
                statement.setString(8, paciente.getLogin());
                statement.setString(9, paciente.getSenha());
                statement.setTimestamp(10, Timestamp.valueOf(paciente.getAtualizado()));

                statement.execute();
                return paciente;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public Paciente desativar(Paciente paciente) {
        if (paciente != null) {
            String sql = "UPDATE pessoas SET " +
                    "atualizado = ? ";

            try {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                paciente.setExcluido(LocalDateTime.now());

                statement.setTimestamp(1, Timestamp.valueOf(paciente.getAtualizado()));

                statement.execute();
                return paciente;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
