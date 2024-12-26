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
        String query = "INSERT INTO products (name, artists, release_date, description, availability, sale_price, original_price, supported_device, genres, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setString(9, serializeGenres(product.getGenres()));
            statement.setString(10, product.getImage());
            statement.executeUpdate();
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET artists = ?, release_date = ?, description = ?, availability = ?, sale_price = ?, original_price = ?, supported_device = ?, genres = ?, image = ? WHERE productCode = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, serializeArtists(product.getArtists()));
            statement.setString(2, product.getReleaseDate());
            statement.setString(3, product.getDescription());
            statement.setBoolean(4, product.isAvailability());
            statement.setDouble(5, product.getSalePrice());
            statement.setDouble(6, product.getOriginalPrice());
            statement.setString(7, product.getSupportedDevice());
            statement.setString(8, serializeGenres(product.getGenres()));
            statement.setString(9, product.getImage());
            statement.setInt(10, product.getProductCode());
            statement.executeUpdate();
        }
    }

    public void deleteProduct(int productCode) throws SQLException {
        String query = "DELETE FROM products WHERE productCode = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productCode);
            statement.executeUpdate();
        }
    }

    public Product getProductById(int productCode) throws SQLException {
        String query = "SELECT * FROM products WHERE productCode = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productCode);
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
                            deserializeGenres(resultSet.getString("genres")),
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
                        deserializeGenres(resultSet.getString("genres")),
                        resultSet.getString("image")
                ));
            }
        }
        return products;
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
                artists.add(new Artist("", "", artistName.trim()));
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
    
    public Artist findArtistByName(String artistName) throws SQLException {
        String query = "SELECT * FROM artists WHERE stage_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, artistName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Artist(
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("stage_name")
                    );
                }
            }
        }
        return null;
    }

    public Genre findGenreByName(String genreName) throws SQLException {
        String query = "SELECT * FROM genres WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, genreName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Genre(resultSet.getString("name"));
                }
            }
        }
        return null; 
    }
    public List<Product> searchProducts(String name, String genre, String artist) {
        List<Product> products = new ArrayList<>();

        String query = "SELECT p.productCode, p.name, p.releaseDate, p.description, p.availability, p.salePrice, p.originalPrice, p.supportedDevice, p.image " +
                "FROM Prodotto p " +
                "JOIN Prodotto_Genere pg ON p.productCode = pg.productCode " +
                "JOIN Genere g ON pg.genreCode = g.genreCode " +
                "JOIN Prodotto_Artista pa ON p.productCode = pa.productCode " +
                "JOIN Artista a ON pa.artistCode = a.artistCode " +
                "WHERE (p.name LIKE ? OR ? IS NULL) " +
                "AND (g.name LIKE ? OR ? IS NULL) " +
                "AND (a.stageName LIKE ? OR ? IS NULL)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            statement.setString(2, name != null ? "%" + name + "%" : null);
            statement.setString(3, genre);
            statement.setString(4, genre != null ? "%" + genre + "%" : null);
            statement.setString(5, artist);
            statement.setString(6, artist != null ? "%" + artist + "%" : null);
            statement.setString(7, artist != null ? "%" + artist + "%" : null);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductCode(resultSet.getInt("product_code"));
                product.setName(resultSet.getString("name"));
                product.setReleaseDate(resultSet.getString("release_date"));
                product.setDescription(resultSet.getString("description"));
                product.setAvailability(resultSet.getBoolean("availability"));
                product.setSalePrice(resultSet.getDouble("sale_price"));
                product.setOriginalPrice(resultSet.getDouble("original_price"));
                product.setImage(resultSet.getString("image"));

                product.setArtists(getArtistsByProductCode(product.getProductCode(), connection));
                product.setGenres(getGenresByProductCode(product.getProductCode(), connection));

                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    private List<Artist> getArtistsByProductCode(int productCode, Connection connection) throws SQLException {
        List<Artist> artists = new ArrayList<>();
        String query = "SELECT * FROM artists a " +
                       "JOIN product_artists pa ON a.artist_id = pa.artist_id " +
                       "WHERE pa.product_code = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                artists.add(new Artist(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("stage_name")
                ));
            }
        }
        return artists;
    }
    
    private List<Genre> getGenresByProductCode(int productCode, Connection connection) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        String query = "SELECT * FROM genres g " +
                       "JOIN product_genres pg ON g.genre_id = pg.genre_id " +
                       "WHERE pg.product_code = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                genres.add(new Genre(resultSet.getString("name")));
            }
        }
        return genres;
    }
    
    

}
