package prodotti;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DataSource;

public class ProductDAO {

    private DataSource dataSource;

    public ProductDAO() {
    	this.dataSource = DataSource.getInstance();
    }

    public Product getProductById(int productId) throws SQLException {
        String query = "SELECT * FROM Prodotto WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getInt("disponibilita") > 0,
                        rs.getDouble("prezzoVendita"),
                        rs.getDouble("prezzoOriginale"),
                        rs.getString("formato"),
                        rs.getString("immagine"),
                        rs.getString("dataPubblicazione"),
                        rs.getBoolean("isDeleted")
                    );
                }
                return null;
            }
        }
    }

    public List<Product> getAllProducts() throws SQLException {
        String query = "SELECT * FROM Prodotto";
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descrizione"),
                    rs.getInt("disponibilita") > 0,
                    rs.getDouble("prezzoVendita"),
                    rs.getDouble("prezzoOriginale"),
                    rs.getString("formato"),
                    rs.getString("immagine"),
                    rs.getString("dataPubblicazione"),
                    rs.getBoolean("isDeleted")
                ));
            }
        }
        return products;
    }

    public void insertProduct(Product product) throws SQLException {
        String query = "INSERT INTO Prodotto (nome, descrizione, disponibilita, prezzoVendita, prezzoOriginale, formato, immagine, dataPubblicazione, isDeleted) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.isAvailability() ? 1 : 0);
            ps.setDouble(4, product.getSalePrice());
            ps.setDouble(5, product.getOriginalPrice());
            ps.setString(6, product.getSupportedDevice());
            ps.setString(7, product.getImage());
            ps.setString(8, product.getReleaseDate());
            ps.setBoolean(9, product.isDeleted());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductCode(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void updateProduct(Product product) throws SQLException {
        String query = "UPDATE Prodotto SET nome = ?, descrizione = ?, disponibilita = ?, prezzoVendita = ?, prezzoOriginale = ?, formato = ?, immagine = ?, dataPubblicazione = ?, isDeleted = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.isAvailability() ? 1 : 0);
            ps.setDouble(4, product.getSalePrice());
            ps.setDouble(5, product.getOriginalPrice());
            ps.setString(6, product.getSupportedDevice());
            ps.setString(7, product.getImage());
            ps.setString(8, product.getReleaseDate());
            ps.setBoolean(9, product.isDeleted());
            ps.setInt(10, product.getProductCode());
            ps.executeUpdate();
        }
    }

    public void deleteProduct(int productId) throws SQLException {
        String query = "UPDATE Prodotto SET isDeleted = TRUE WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            ps.executeUpdate();
        }
    }
    public List<Product> searchProducts(String name, List<String> genres, List<String> artists) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Prodotto WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            queryBuilder.append(" AND nome LIKE ?");
        }
        if (genres != null && !genres.isEmpty()) {
            queryBuilder.append(" AND id IN (SELECT product_id FROM prodotto_genere WHERE genere_id IN (SELECT id FROM genere WHERE nome IN (?)))");
        }
        if (artists != null && !artists.isEmpty()) {
            queryBuilder.append(" AND id IN (SELECT product_id FROM prodotto_artista WHERE artista_id IN (SELECT id FROM artista WHERE nome IN (?)))");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(queryBuilder.toString())) {
            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (genres != null && !genres.isEmpty()) {
                ps.setString(paramIndex++, String.join(",", genres));
            }
            if (artists != null && !artists.isEmpty()) {
                ps.setString(paramIndex++, String.join(",", artists));
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getInt("disponibilita") > 0,
                        rs.getDouble("prezzoVendita"),
                        rs.getDouble("prezzoOriginale"),
                        rs.getString("formato"),
                        rs.getString("immagine"),
                        rs.getString("dataPubblicazione"),
                        rs.getBoolean("isDeleted")
                    );
                    products.add(product);
                }
                return products;
            }
        }
    }

    public Artist findArtistByName(String name) throws SQLException {
        String query = "SELECT * FROM Artista WHERE nome = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Artist(rs.getString("nome"), rs.getString("cognome"), rs.getString("nomeArtistico"));
                }
                return null;
            }
        }
    }

    public Genre findGenreByName(String name) throws SQLException {
        String query = "SELECT * FROM Genere WHERE nome = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Genre(rs.getString("nome"));
                }
                return null;
            }
        }
    }
    
    public String getImageByProductId(int productId) throws SQLException {
        String query = "SELECT immagine FROM Prodotto WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("immagine");
                }
                return null;
            }
        }
    }
}