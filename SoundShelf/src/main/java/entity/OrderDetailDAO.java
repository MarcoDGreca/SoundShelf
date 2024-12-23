package entity;

import util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private DataSource dataSource;

    public OrderDetailDAO() {
        this.dataSource = DataSource.getInstance();
    }

    public void addOrderDetail(int orderId, int ticketId, int quantity) throws SQLException {
        String sql = "INSERT INTO DettaglioOrdine (codiceOrdine, codiceBiglietto, quantita) VALUES (?, ?, ?)";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, ticketId);
            ps.setInt(3, quantity);
            ps.executeUpdate();
        }
    }

    public synchronized List<OrderDetail> getOrderDetailsByOrderId(int ordineId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM DettaglioOrdine WHERE codiceOrdine = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ordineId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrderDetail orderDetail = new OrderDetail(
                        resultSet.getInt("codiceOrdine"),
                        resultSet.getInt("codiceBiglietto"),
                        resultSet.getInt("quantita")
                    );
                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public synchronized void updateOrderDetail(OrderDetail orderDetail) {
        String query = "UPDATE DettaglioOrdine SET quantita = ? WHERE codiceOrdine = ? AND codiceBiglietto = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderDetail.getQuantita());
            statement.setInt(2, orderDetail.getCodiceOrdine());
            statement.setInt(3, orderDetail.getCodiceBiglietto());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void deleteOrderDetail(int ordineId, int bigliettoId) {
        String query = "DELETE FROM DettaglioOrdine WHERE codiceOrdine = ? AND codiceBiglietto = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ordineId);
            statement.setInt(2, bigliettoId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM DettaglioOrdine";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setCodiceOrdine(rs.getInt("codiceOrdine"));
                orderDetail.setCodiceBiglietto(rs.getInt("codiceBiglietto"));
                orderDetail.setQuantita(rs.getInt("quantita"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDetails;
    }
    
    public List<OrderDetail> getOrderDetailsByUser(String emailCliente) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT do.* FROM DettaglioOrdine do " +
                     "JOIN Ordine o ON do.codiceOrdine = o.codiceOrdine " +
                     "WHERE o.emailCliente = ?";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emailCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setCodiceOrdine(rs.getInt("codiceOrdine"));
                    orderDetail.setCodiceBiglietto(rs.getInt("codiceBiglietto"));
                    orderDetail.setQuantita(rs.getInt("quantita"));
                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderDetails;
    }
}

