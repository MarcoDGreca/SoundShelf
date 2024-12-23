package control;

import model.OrderDAO;
import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/refund")
public class RefundServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        if (user != null) {
            try {
                orderDAO.requestRefund(orderId, user.getEmail());
                response.sendRedirect("refundConfirmation.jsp");
            } catch (SQLException e) {
                throw new ServletException("Errore nella richiesta di rimborso", e);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}

