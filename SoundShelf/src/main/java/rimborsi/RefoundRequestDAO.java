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
        String query = "INSERT INTO refound_requests (product_code, reason, iban, stato) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, refoundRequest.getProductCode());
            statement.setString(2, refoundRequest.getReason());
            statement.setString(3, refoundRequest.getIban());
            statement.setString(4, refoundRequest.getStato().getStato());
            statement.executeUpdate();
        }
    }

    public void updateRefoundRequest(RefoundRequest refoundRequest) throws SQLException {
        String query = "UPDATE refound_requests SET reason = ?, iban = ?, stato = ? WHERE product_code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, refoundRequest.getReason());
            statement.setString(2, refoundRequest.getIban());
            statement.setString(3, refoundRequest.getStato().getStato());
            statement.setInt(4, refoundRequest.getProductCode());
            statement.executeUpdate();
        }
    }

    public void deleteRefoundRequest(int productCode) throws SQLException {
        String query = "DELETE FROM refound_requests WHERE product_code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productCode);
            statement.executeUpdate();
        }
    }

    public RefoundRequest getRefoundRequest(int productCode) throws SQLException {
        String query = "SELECT * FROM refound_requests WHERE product_code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new RefoundRequest(
                            resultSet.getInt("product_code"),
                            resultSet.getString("reason"),
                            resultSet.getString("iban"),
                            StatoRimborso.fromString(resultSet.getString("stato"))
                    );
                }
            }
        }
        return null;
    }

    public List<RefoundRequest> getAllRefoundRequests() throws SQLException {
        List<RefoundRequest> refoundRequests = new ArrayList<>();
        String query = "SELECT * FROM refound_requests";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                refoundRequests.add(new RefoundRequest(
                        resultSet.getInt("product_code"),
                        resultSet.getString("reason"),
                        resultSet.getString("iban"),
                        StatoRimborso.fromString(resultSet.getString("stato"))
                ));
            }
        }
        return refoundRequests;
    }
}
