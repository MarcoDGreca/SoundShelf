package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DataSource;

public class SupportRequestDAO {

    private DataSource dataSource;

    public SupportRequestDAO() {
        this.dataSource = DataSource.getInstance();
    }


    public void saveSupportRequest(SupportRequest supportRequest) throws SQLException {
        String query = "INSERT INTO support_requests (name, email, description, data_invio, orario_invio, stato, informazioni_aggiuntive) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, supportRequest.getName());
            statement.setString(2, supportRequest.getEmail());
            statement.setString(3, supportRequest.getDescription());
            statement.setDate(4, supportRequest.getDataInvio());
            statement.setString(5, supportRequest.getOrarioInvio());
            statement.setString(6, supportRequest.getStato().getStato());
            statement.setString(7, supportRequest.getInformazioniAggiuntive());
            statement.executeUpdate();
        }
    }

    public void updateSupportRequest(SupportRequest supportRequest) throws SQLException {
        String query = "UPDATE support_requests SET email = ?, description = ?, data_invio = ?, orario_invio = ?, stato = ?, informazioni_aggiuntive = ? WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, supportRequest.getEmail());
            statement.setString(2, supportRequest.getDescription());
            statement.setDate(3, supportRequest.getDataInvio());
            statement.setString(4, supportRequest.getOrarioInvio());
            statement.setString(5, supportRequest.getStato().getStato());
            statement.setString(6, supportRequest.getInformazioniAggiuntive());
            statement.setString(7, supportRequest.getName());
            statement.executeUpdate();
        }
    }

    public SupportRequest getSupportRequest(String name) throws SQLException {
        String query = "SELECT * FROM support_requests WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new SupportRequest(
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("description"),
                            resultSet.getDate("data_invio"),
                            resultSet.getString("orario_invio"),
                            StatoSupporto.fromString(resultSet.getString("stato")),
                            resultSet.getString("informazioni_aggiuntive")
                    );
                }
            }
        }
        return null;
    }

    // Metodo aggiornato per recuperare tutte le richieste di supporto
    public List<SupportRequest> getAllSupportRequests() throws SQLException {
        List<SupportRequest> supportRequests = new ArrayList<>();
        String query = "SELECT * FROM support_requests";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                supportRequests.add(new SupportRequest(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("description"),
                        resultSet.getDate("data_invio"),
                        resultSet.getString("orario_invio"),
                        StatoSupporto.fromString(resultSet.getString("stato")),
                        resultSet.getString("informazioni_aggiuntive")
                ));
            }
        }
        return supportRequests;
    }
}
