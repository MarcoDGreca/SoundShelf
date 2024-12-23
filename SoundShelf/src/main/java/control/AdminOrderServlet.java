package control;

import model.Order;
import model.OrderDAO;
import model.Stato;
import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/manageOrders")
public class AdminOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listOrders(request, response);
                break;
            case "filter":
                filterOrders(request, response);
                break;
            default:
                listOrders(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "update":
                    updateOrder(request, response);
                    break;
                case "delete":
                    deleteOrder(request, response);
                    break;
                default:
                    break;
            }
        }
        doGet(request, response);
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin/manageOrders.jsp").forward(request, response);
    }

    private void filterOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailCliente = InputSanitizer.sanitize(request.getParameter("emailCliente"));
        String dataInizioStr = InputSanitizer.sanitize(request.getParameter("dataInizio"));
        String dataFineStr = InputSanitizer.sanitize(request.getParameter("dataFine"));

        Date dataInizio = dataInizioStr != null && !dataInizioStr.isEmpty() ? Date.valueOf(dataInizioStr) : null;
        Date dataFine = dataFineStr != null && !dataFineStr.isEmpty() ? Date.valueOf(dataFineStr) : null;

        List<Order> orders = orderDAO.filterOrders(emailCliente, dataInizio, dataFine);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin/manageOrders.jsp").forward(request, response);
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String stato = InputSanitizer.sanitize(request.getParameter("stato"));

        Order order = orderDAO.getOrderById(orderId);
        if (order != null) {
            order.setStato(Stato.fromString(stato));
            orderDAO.updateStatoOrder(order);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        orderDAO.deleteOrder(orderId);
    }
}
