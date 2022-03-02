package DAO;

import Factory.ConnectionFactory;
import Models.Especialidade;
import Models.Paciente;

import java.sql.*;
import java.time.LocalDateTime;

public class EspecialidadeDAO {
    private String tableName = "especialidades";
    private Connection connection = new ConnectionFactory().getConnection();

    public Especialidade criar(Especialidade especialidade) {
        if (especialidade != null) {
            String sql = "INSERT INTO especialidades (" +
                    "nome, cadastro) " +
                    "VALUES (?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                especialidade.setCadastro(LocalDateTime.now());

                statement.setString(1, especialidade.getNome());
                statement.setTimestamp(2, Timestamp.valueOf(especialidade.getCadastro()));

                statement.execute();

                ResultSet resultSet = statement.getGeneratedKeys();

                while(resultSet.next()){
                    especialidade.setId(resultSet.getLong("id"));
                }
                return especialidade;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
