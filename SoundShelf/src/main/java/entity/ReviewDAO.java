package entity;

import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private DataSource dataSource;

    public ReviewDAO() {
        this.dataSource = DataSource.getInstance();;
    }

    public synchronized Review getReviewById(int reviewId) {
        String query = "SELECT * FROM Recensione WHERE codiceRecensione = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reviewId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Review(
                        resultSet.getInt("codiceRecensione"),
                        resultSet.getInt("codiceEvento"),
                        resultSet.getString("emailCliente"),
                        resultSet.getInt("votazione"),
                        resultSet.getString("testo"),
                        resultSet.getDate("dataRecensione")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addReview(Review review) {
        String sql = "INSERT INTO Recensione (codiceEvento, emailCliente, votazione, testo, dataRecensione) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, review.getCodiceEvento());
            ps.setString(2, review.getEmailCliente());
            ps.setInt(3, review.getVotazione());
            ps.setString(4, review.getTesto());
            ps.setDate(5, review.getDataRecensione());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateReview(Review review) {
        String query = "UPDATE Recensione SET codiceEvento = ?, emailCliente = ?, votazione = ?, testo = ?, dataRecensione = ? WHERE codiceRecensione = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getCodiceEvento());
            statement.setString(2, review.getEmailCliente());
            statement.setInt(3, review.getVotazione());
            statement.setString(4, review.getTesto());
            statement.setDate(5, review.getDataRecensione());
            statement.setInt(6, review.getCodiceRecensione());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteReview(int reviewId) {
        String query = "DELETE FROM Recensione WHERE codiceRecensione = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reviewId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM Recensione";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Review review = new Review(
                    resultSet.getInt("codiceRecensione"),
                    resultSet.getInt("codiceEvento"),
                    resultSet.getString("emailCliente"),
                    resultSet.getInt("votazione"),
                    resultSet.getString("testo"),
                    resultSet.getDate("dataRecensione")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    
    public List<Review> getReviewsByEventId(int eventId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM Recensione WHERE codiceEvento = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review();
                    review.setCodiceRecensione(rs.getInt("codiceRecensione"));
                    review.setCodiceEvento(rs.getInt("codiceEvento"));
                    review.setEmailCliente(rs.getString("emailCliente"));
                    review.setVotazione(rs.getInt("votazione"));
                    review.setTesto(rs.getString("testo"));
                    review.setDataRecensione(rs.getDate("dataRecensione"));
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
