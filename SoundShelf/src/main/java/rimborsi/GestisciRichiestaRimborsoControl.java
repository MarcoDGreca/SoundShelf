package rimborsi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/gestisciRichiesteRimborsoControl")
public class GestisciRichiestaRimborsoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RefoundRequestDAO refoundRequestDAO;

    @Override
    public void init() throws ServletException {
        refoundRequestDAO = new RefoundRequestDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RefoundRequest> richieste = refoundRequestDAO.getAllRefoundRequests();
            request.setAttribute("richieste", richieste);
            request.getRequestDispatcher("/rimborsoInterface/catalogoRichiesteRimborso.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Errore nel recupero delle richieste di rimborso.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RefoundRequest> richieste = refoundRequestDAO.getAllRefoundRequests();

            for (RefoundRequest richiesta : richieste) {
                String newState = request.getParameter("newState_" + richiesta.getProductCode());
                if (newState != null) {
                    try {
                        StatoRimborso stato = StatoRimborso.fromString(newState);
                        richiesta.setStato(stato);
                        refoundRequestDAO.updateRefoundRequest(richiesta);
                    } catch (IllegalArgumentException e) {
                        request.setAttribute("message", "Stato non valido: " + newState);
                        request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                        return;
                    }
                }
            }

            response.sendRedirect("rimborso/gestisciRichiesteRimborsoControl?success=modifiche salvate");
        } catch (SQLException e) {
            request.setAttribute("message", "Errore durante il salvataggio delle modifiche.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "Errore inaspettato.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
