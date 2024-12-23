package entity;

import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
    private DataSource dataSource;

    public UtenteDAO() {
        this.dataSource = DataSource.getInstance();
    }

    public synchronized Utente getUserByEmail(String email) {
        String query = "SELECT * FROM Utente WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Utente(
                        resultSet.getString("email"),
                        resultSet.getString("passwordUser"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("indirizzo"),
                        resultSet.getString("telefono"),
                        resultSet.getString("numero"),
                        Ruolo.fromString(resultSet.getString("ruolo"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized void addUser(Utente utente) {
        String query = "INSERT INTO Utente (email, passwordUser, nome, cognome, indirizzo, telefono, numero, ruolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utente.getEmail());
            statement.setString(2, utente.getPasswordUser());
            statement.setString(3, utente.getNome());
            statement.setString(4, utente.getCognome());
            statement.setString(5, utente.getIndirizzo());
            statement.setString(6, utente.getTelefono());
            statement.setString(7, utente.getNumero());
            statement.setString(8, utente.getRuolo().getRuolo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateUser(Utente utente) {
        String query = "UPDATE Utente SET passwordUser = ?, nome = ?, cognome = ?, indirizzo = ?, telefono = ?, numero = ?, ruolo = ? WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utente.getPasswordUser());
            statement.setString(2, utente.getNome());
            statement.setString(3, utente.getCognome());
            statement.setString(4, utente.getIndirizzo());
            statement.setString(5, utente.getTelefono());
            statement.setString(6, utente.getNumero());
            statement.setString(7, utente.getRuolo().getRuolo());
            statement.setString(8, utente.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteUser(String email) {
        String query = "DELETE FROM Utente WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Utente> getAllUsers() {
        List<Utente> users = new ArrayList<>();
        String query = "SELECT * FROM Utente";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Utente utente = new Utente(
                    resultSet.getString("email"),
                    resultSet.getString("passwordUser"),
                    resultSet.getString("nome"),
                    resultSet.getString("cognome"),
                    resultSet.getString("indirizzo"),
                    resultSet.getString("telefono"),
                    resultSet.getString("numero"),
                    Ruolo.fromString(resultSet.getString("ruolo"))
                );
                users.add(utente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public synchronized Utente authenticate(String email, String password) {
        String query = "SELECT * FROM Utente WHERE email = ? AND passwordUser = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Utente user = new Utente();
                    user.setEmail(resultSet.getString("email"));
                    user.setPasswordUser(resultSet.getString("passwordUser"));
                    user.setNome(resultSet.getString("nome"));
                    user.setCognome(resultSet.getString("cognome"));
                    user.setIndirizzo(resultSet.getString("indirizzo"));
                    user.setTelefono(resultSet.getString("telefono"));
                    user.setNumero(resultSet.getString("numero"));
                    user.setRuolo(Ruolo.fromString(resultSet.getString("ruolo")));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void promoteToAdmin(String email) {
        String sql = "UPDATE Utente SET ruolo = 'Admin' WHERE email = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
