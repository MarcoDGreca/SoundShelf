package rimborsi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DataSource;

public class RefoundRequestDAO {

    private DataSource dataSource;

    public RefoundRequestDAO() {
        this.dataSource = DataSource.getInstance();
    }

    public void saveRefoundRequest(RefoundRequest refoundRequest) throws SQLException {
        String query = "INSERT INTO RichiestaRimborso (motivoRimborso, dataRichiesta, stato, ibanCliente, idOrdine, idProdotto) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, refoundRequest.getReason());
            statement.setDate(2, Date.valueOf(java.time.LocalDate.now())); 
            statement.setString(3, refoundRequest.getStato().getStato());
            statement.setString(4, refoundRequest.getIban());
            statement.setInt(5, refoundRequest.getOrderCode());
            statement.setInt(6, refoundRequest.getProductCode());
            statement.executeUpdate();
        }
    }

    public void updateRefoundRequest(RefoundRequest refoundRequest) throws SQLException {
        String query = "UPDATE RichiestaRimborso SET motivoRimborso = ?, stato = ?, ibanCliente = ? WHERE idOrdine = ? AND idProdotto = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, refoundRequest.getReason());
            statement.setString(2, refoundRequest.getStato().getStato());
            statement.setString(3, refoundRequest.getIban());
            statement.setInt(4, refoundRequest.getOrderCode());
            statement.setInt(5, refoundRequest.getProductCode());
            statement.executeUpdate();
        }
    }

    public void deleteRefoundRequest(int orderCode, int productCode) throws SQLException {
        String query = "DELETE FROM RichiestaRimborso WHERE idOrdine = ? AND idProdotto = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderCode);
            statement.setInt(2, productCode);
            statement.executeUpdate();
        }
    }

    public RefoundRequest getRefoundRequest(int orderCode, int productCode) throws SQLException {
        String query = "SELECT * FROM RichiestaRimborso WHERE idOrdine = ? AND idProdotto = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderCode);
            statement.setInt(2, productCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new RefoundRequest(
                            resultSet.getInt("id"),
                            resultSet.getString("motivoRimborso"),
                            resultSet.getString("ibanCliente"),
                            StatoRimborso.fromString(resultSet.getString("stato")),
                            resultSet.getInt("idOrdine"),
                            resultSet.getInt("idProdotto")
                    );
                }
            }
        }
        return null;
    }

    public List<RefoundRequest> getAllRefoundRequests() throws SQLException {
        List<RefoundRequest> refoundRequests = new ArrayList<>();
        String query = "SELECT * FROM RichiestaRimborso";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                refoundRequests.add(new RefoundRequest(
                        resultSet.getInt("id"),
                        resultSet.getString("motivoRimborso"),
                        resultSet.getString("ibanCliente"),
                        StatoRimborso.fromString(resultSet.getString("stato")),
                        resultSet.getInt("idOrdine"),
                        resultSet.getInt("idProdotto")
                ));
            }
        }
        return refoundRequests;
    }
}
