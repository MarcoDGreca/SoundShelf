package ordini;

import prodotti.Product;
import prodotti.ProductDAO;
import utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/orderDetails")
public class ListaOrdiniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private OrderDetailDAO orderDetailDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String emailCliente = user.getEmail();
        List<Order> orders = orderDAO.getOrdersByEmail(emailCliente);
        request.setAttribute("orders", orders);

        for (Order order : orders) {
            List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(order.getNumeroOrdine());
            for (OrderDetail detail : orderDetails) {
                Product product = null;
				try {
					product = productDAO.getProductById(detail.getCodiceBiglietto());
				} catch (SQLException e) {
					e.printStackTrace();
				}
                request.setAttribute("orderDetail_" + detail.getCodiceBiglietto(), detail);
                request.setAttribute("product_" + detail.getCodiceBiglietto(), product);
            }
        }

        request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
    }
}

