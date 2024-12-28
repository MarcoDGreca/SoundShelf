package ordini;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OrdineRicevutoControl")
public class OrdineRicevutoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO ordineDAO;

    public OrdineRicevutoControl() {
        ordineDAO = new OrderDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ordineId = request.getParameter("ordineId");
        String conferma = request.getParameter("confermaRicezione");

        Order ordine = ordineDAO.getOrderById(Integer.parseInt(ordineId));

        if (ordine != null) {
            if ("si".equals(conferma)) {
                ordine.setStato(StatoOrdine.COMPLETATO);
                ordineDAO.updateOrder(ordine);
                request.setAttribute("messaggio", "Ordine confermato come ricevuto.");
            } else {
                request.setAttribute("messaggio", "Conferma della ricezione annullata.");
            }
        } else {
            request.setAttribute("messaggio", "Ordine non trovato.");
        }
        request.getRequestDispatcher("storicoOrdini.jsp").forward(request, response);
    }
}
