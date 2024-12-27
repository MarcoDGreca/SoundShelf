package control;

import entity.Order;
import entity.OrderDAO;
import entity.OrderDetail;
import entity.OrderDetailDAO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/gestisciCatalogoOrdiniControl")
public class GestisciCatalogoOrdiniControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Order> ordini = orderDAO.getAllOrders();
            Map<Integer, List<OrderDetail>> ordineDetailsMap = new HashMap<>();
            for (Order ordine : ordini) {
                List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(ordine.getNumeroOrdine());
                ordineDetailsMap.put(ordine.getNumeroOrdine(), orderDetails);
            }

            request.setAttribute("ordineDetailsMap", ordineDetailsMap);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/paginaCatalogoOrdini.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore durante il recupero degli ordini.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
