package ordini;

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
    private ElementoOrdineDAO orderDetailDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        orderDetailDAO = new ElementoOrdineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Order> ordini = orderDAO.getAllOrders();
            Map<Integer, List<ElementoOrdine>> ordineDetailsMap = new HashMap<>();
            for (Order ordine : ordini) {
                List<ElementoOrdine> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(ordine.getNumeroOrdine());
                ordineDetailsMap.put(ordine.getNumeroOrdine(), orderDetails);
            }

            request.setAttribute("ordineDetailsMap", ordineDetailsMap);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("ordiniInterface/catalogoOrdini.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore durante il recupero degli ordini.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
