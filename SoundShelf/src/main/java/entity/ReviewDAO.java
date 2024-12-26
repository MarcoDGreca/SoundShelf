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
    	this.dataSource = DataSource.getInstance();
    }


	public void saveReview(Review review) throws SQLException {
        String query = "INSERT INTO product_reviews (codiceProdotto, emailCliente, votazione, testo, dataRecensione) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, review.getCodiceProdotto());
            statement.setString(2, review.getEmailCliente());
            statement.setInt(3, review.getVotazione());
            statement.setString(4, review.getTesto());
            statement.setDate(5, review.getDataRecensione());
            statement.executeUpdate();
        }
    }

    public void updateReview(Review review) throws SQLException {
        String query = "UPDATE product_reviews SET codiceProdotto = ?, emailCliente = ?, votazione = ?, testo = ?, dataRecensione = ? WHERE codiceRecensione = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, review.getCodiceProdotto());
            statement.setString(2, review.getEmailCliente());
            statement.setInt(3, review.getVotazione());
            statement.setString(4, review.getTesto());
            statement.setDate(5, review.getDataRecensione());
            statement.setInt(6, review.getCodiceRecensione());
            statement.executeUpdate();
        }
    }

    public void deleteReview(int reviewId) throws SQLException {
        String query = "DELETE FROM product_reviews WHERE codiceRecensione = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, reviewId);
            statement.executeUpdate();
        }
    }

    public Review getReviewById(int reviewId) throws SQLException {
        String query = "SELECT * FROM product_reviews WHERE codiceRecensione = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, reviewId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Review(
                            resultSet.getInt("codiceRecensione"),
                            resultSet.getInt("codiceProdotto"),
                            resultSet.getString("emailCliente"),
                            resultSet.getInt("votazione"),
                            resultSet.getString("testo"),
                            resultSet.getDate("dataRecensione")
                    );
                }
            }
        }
        return null;
    }

    public List<Review> getAllReviews() throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM product_reviews";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
        	ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                reviews.add(new Review(
                        resultSet.getInt("codiceRecensione"),
                        resultSet.getInt("codiceProdotto"),
                        resultSet.getString("emailCliente"),
                        resultSet.getInt("votazione"),
                        resultSet.getString("testo"),
                        resultSet.getDate("dataRecensione")
                ));
            }
        }
        return reviews;
    }

    public List<Review> getReviewsByProductId(int productId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM product_reviews WHERE codiceProdotto = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(new Review(
                            resultSet.getInt("codiceRecensione"),
                            resultSet.getInt("codiceProdotto"),
                            resultSet.getString("emailCliente"),
                            resultSet.getInt("votazione"),
                            resultSet.getString("testo"),
                            resultSet.getDate("dataRecensione")
                    ));
                }
            }
        }
        return reviews;
    }
}
