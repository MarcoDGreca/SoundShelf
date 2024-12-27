package control;

import entity.Order;
import entity.OrderDAO;
import entity.StatoOrdine;
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
public class GestioneOrdiniControl extends HttpServlet {
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

        try {
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
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore durante la gestione degli ordini.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            try {
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
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Errore durante l'aggiornamento o la cancellazione dell'ordine.");
                request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
                return;
            }
        }
        doGet(request, response);
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderDAO.getAllOrders();
        if (orders == null) {
            request.setAttribute("errorMessage", "Impossibile recuperare gli ordini.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
            return;
        }
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
        if (orders == null) {
            request.setAttribute("errorMessage", "Impossibile filtrare gli ordini.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
            return;
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/admin/manageOrders.jsp").forward(request, response);
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String stato = InputSanitizer.sanitize(request.getParameter("stato"));

        try {
            Order order = orderDAO.getOrderById(orderId);
            if (order != null) {
                order.setStato(StatoOrdine.fromString(stato));
                orderDAO.updateStatoOrder(order);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore nell'aggiornamento dello stato dell'ordine.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            orderDAO.deleteOrder(orderId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore nella cancellazione dell'ordine.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
