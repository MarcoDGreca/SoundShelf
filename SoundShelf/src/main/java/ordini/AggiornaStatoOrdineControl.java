package ordini;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/aggiornaStatoOrdineControl")
public class AggiornaStatoOrdineControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int numeroOrdine = Integer.parseInt(request.getParameter("numeroOrdine"));
            String nuovoStato = request.getParameter("nuovoStato");

            StatoOrdine statoOrdine = StatoOrdine.fromString(nuovoStato);
            boolean success = orderDAO.updateOrderStatus(numeroOrdine, statoOrdine.name());

            if (success) {
                request.setAttribute("successMessage", "Stato dell'ordine aggiornato con successo.");
            } else {
                request.setAttribute("errorMessage", "Errore durante l'aggiornamento dello stato ordine.");
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/gestisciCatalogoOrdiniControl");
    }
}
