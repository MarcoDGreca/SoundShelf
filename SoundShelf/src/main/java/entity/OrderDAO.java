package entity;

import util.DataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private DataSource dataSource;

    public OrderDAO() {
        this.dataSource = DataSource.getInstance();
    }

    public int addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO Ordine (emailCliente, prezzoTotale, dataAcquisto, stato) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getEmailCliente());
            ps.setDouble(2, order.getPrezzoTotale());
            ps.setDate(3, order.getDataAcquisto());
            ps.setString(4, order.getStato().getStato());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creazione ordine fallita.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creazione ordine fallita.");
                }
            }
        }
    }

    public synchronized Order getOrderById(int orderId) {
        String query = "SELECT * FROM Ordine WHERE codiceOrdine = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Order(
                        resultSet.getInt("codiceOrdine"),
                        resultSet.getString("emailCliente"),
                        resultSet.getDouble("prezzoTotale"),
                        resultSet.getDate("dataAcquisto"),
                        Stato.fromString(resultSet.getString("stato"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Ordine";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order(
                    resultSet.getInt("codiceOrdine"),
                    resultSet.getString("emailCliente"),
                    resultSet.getDouble("prezzoTotale"),
                    resultSet.getDate("dataAcquisto"),
                    Stato.fromString(resultSet.getString("stato"))
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
    
    public void updateStatoOrder(Order order) {
        String sql = "UPDATE Ordine SET stato = ? WHERE codiceOrdine = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, order.getStato().name().replace("_", " "));
            ps.setInt(2, order.getCodiceOrdine());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateOrder(Order order) {
        String query = "UPDATE Ordine SET emailCliente = ?, prezzoTotale = ?, dataAcquisto = ?, stato = ? WHERE codiceOrdine = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, order.getEmailCliente());
            statement.setDouble(2, order.getPrezzoTotale());
            statement.setDate(3, order.getDataAcquisto());
            statement.setString(4, order.getStato().getStato());
            statement.setInt(5, order.getCodiceOrdine());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteOrder(int orderId) {
        String query = "DELETE FROM Ordine WHERE codiceOrdine = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized List<Order> getOrdersByEmail(String emailCliente) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Ordine WHERE emailCliente = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, emailCliente);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = new Order();
                    order.setCodiceOrdine(resultSet.getInt("codiceOrdine"));
                    order.setEmailCliente(resultSet.getString("emailCliente"));
                    order.setPrezzoTotale(resultSet.getDouble("prezzoTotale"));
                    order.setDataAcquisto(resultSet.getDate("dataAcquisto"));
                    order.setStato(Stato.valueOf(resultSet.getString("stato").toUpperCase().replace(" ", "_")));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public void requestRefund(int orderId, String emailCliente) throws SQLException {
        String sql = "UPDATE Ordine SET stato = 'Richiesto Rimborso' WHERE codiceOrdine = ? AND emailCliente = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setString(2, emailCliente);
            ps.executeUpdate();
        }
    }
   
    
    public List<Event> getPurchasedEventsByUser(String email) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.* FROM Evento e " +
                     "JOIN Biglietto b ON e.codiceEvento = b.codiceEvento " +
                     "JOIN DettaglioOrdine do ON b.codiceBiglietto = do.codiceBiglietto " +
                     "JOIN Ordine o ON do.codiceOrdine = o.codiceOrdine " +
                     "WHERE o.emailCliente = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setCodiceEvento(rs.getInt("codiceEvento"));
                    event.setNome(rs.getString("nome"));
                    event.setOrario(rs.getString("orario"));
                    event.setLuogo(rs.getString("luogo"));
                    event.setDataEvento(rs.getDate("dataEvento"));
                    event.setTipo(TipoEvento.fromString(rs.getString("tipo")));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
    
    public List<Order> filterOrders(String emailCliente, Date dataInizio, Date dataFine) {
        List<Order> orders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Ordine WHERE 1=1");
        
        if (emailCliente != null && !emailCliente.isEmpty()) {
            sql.append(" AND emailCliente = ?");
        }
        if (dataInizio != null) {
            sql.append(" AND dataAcquisto >= ?");
        }
        if (dataFine != null) {
            sql.append(" AND dataAcquisto <= ?");
        }
        
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            
            if (emailCliente != null && !emailCliente.isEmpty()) {
                ps.setString(paramIndex++, emailCliente);
            }
            if (dataInizio != null) {
                ps.setDate(paramIndex++, dataInizio);
            }
            if (dataFine != null) {
                ps.setDate(paramIndex++, dataFine);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setCodiceOrdine(rs.getInt("codiceOrdine"));
                    order.setEmailCliente(rs.getString("emailCliente"));
                    order.setPrezzoTotale(rs.getDouble("prezzoTotale"));
                    order.setDataAcquisto(rs.getDate("dataAcquisto"));
                    order.setStato(Stato.fromString(rs.getString("stato")));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

}

