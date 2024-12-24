package entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DataSource;

public class ProductDAO {

    private DataSource dataSource;

    public ProductDAO(Connection connection) {
        this.dataSource = DataSource.getInstance();
    }

    public ProductDAO() {
    }

    public void saveProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, artists, release_date, description, availability, sale_price, original_price, supported_device, genre, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setString(2, serializeArtists(product.getArtists()));
            statement.setString(3, product.getReleaseDate());
            statement.setString(4, product.getDescription());
            statement.setBoolean(5, product.isAvailability());
            statement.setDouble(6, product.getSalePrice());
            statement.setDouble(7, product.getOriginalPrice());
            statement.setString(8, product.getSupportedDevice());
            statement.setString(9, serializeGenres(product.getGenre()));
            statement.setString(10, product.getImage());
            statement.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET artists = ?, release_date = ?, description = ?, availability = ?, sale_price = ?, original_price = ?, supported_device = ?, genre = ?, image = ? WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, serializeArtists(product.getArtists()));
            statement.setString(2, product.getReleaseDate());
            statement.setString(3, product.getDescription());
            statement.setBoolean(4, product.isAvailability());
            statement.setDouble(5, product.getSalePrice());
            statement.setDouble(6, product.getOriginalPrice());
            statement.setString(7, product.getSupportedDevice());
            statement.setString(8, serializeGenres(product.getGenre()));
            statement.setString(9, product.getImage());
            statement.setString(10, product.getName());
            statement.executeUpdate();
        }
    }

    public void deleteProduct(String name) throws SQLException {
        String query = "DELETE FROM products WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    public Product getProduct(String name) throws SQLException {
        String query = "SELECT * FROM products WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                            resultSet.getInt("productCode"),
                            resultSet.getString("name"),
                            deserializeArtists(resultSet.getString("artists")),
                            resultSet.getString("release_date"),
                            resultSet.getString("description"),
                            resultSet.getBoolean("availability"),
                            resultSet.getDouble("sale_price"),
                            resultSet.getDouble("original_price"),
                            resultSet.getString("supported_device"),
                            deserializeGenres(resultSet.getString("genre")),
                            resultSet.getString("image")
                    );
                }
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("productCode"),
                        resultSet.getString("name"),
                        deserializeArtists(resultSet.getString("artists")),
                        resultSet.getString("release_date"),
                        resultSet.getString("description"),
                        resultSet.getBoolean("availability"),
                        resultSet.getDouble("sale_price"),
                        resultSet.getDouble("original_price"),
                        resultSet.getString("supported_device"),
                        deserializeGenres(resultSet.getString("genre")),
                        resultSet.getString("image")
                ));
            }
        }
        return products;
    }

    public String getImage(int productCode) throws SQLException {
        String query = "SELECT image FROM products WHERE productCode = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("image");
                }
            }
        }
        return null;
    }

    private String serializeArtists(List<Artist> artists) {
        StringBuilder sb = new StringBuilder();
        for (Artist artist : artists) {
            sb.append(artist.getStageName()).append(",");
        }
        return sb.toString();
    }

    private String serializeGenres(List<Genre> genres) {
        StringBuilder sb = new StringBuilder();
        for (Genre genre : genres) {
            sb.append(genre.getName()).append(",");
        }
        return sb.toString();
    }

    private List<Artist> deserializeArtists(String artistsString) {
        List<Artist> artists = new ArrayList<>();
        for (String artistName : artistsString.split(",")) {
            if (!artistName.trim().isEmpty()) {
                artists.add(new Artist(artistName.trim(), "", "")); 
            }
        }
        return artists;
    }

    private List<Genre> deserializeGenres(String genresString) {
        List<Genre> genres = new ArrayList<>();
        for (String genreName : genresString.split(",")) {
            if (!genreName.trim().isEmpty()) {
                genres.add(new Genre(genreName.trim())); 
            }
        }
        return genres;
    }
}
