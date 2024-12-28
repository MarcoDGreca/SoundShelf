package supporto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/gestisciRichiestaSupportoControl")
public class GestisciRichiestaSupportoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SupportRequestDAO supportRequestDAO;

    @Override
    public void init() throws ServletException {
        supportRequestDAO = new SupportRequestDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<SupportRequest> richieste = supportRequestDAO.getAllSupportRequests();
            request.setAttribute("richieste", richieste);
            request.getRequestDispatcher("/supportoInterface/catalogoRichiesteSupporto.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Errore nel recupero delle richieste di supporto.");
            request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("richiediInformazioni".equals(action)) {
            String nomeRichiesta = request.getParameter("name");

            if (nomeRichiesta == null || nomeRichiesta.isEmpty()) {
                request.setAttribute("message", "Nome della richiesta non valido.");
                request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                return;
            }

            try {
                SupportRequest supportRequest = supportRequestDAO.getSupportRequestById(Integer.parseInt(nomeRichiesta));

                if (supportRequest != null) {
                    request.setAttribute("richiesta", supportRequest);
                    request.getRequestDispatcher("supportoInterface/richiestaSupportoView.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "La richiesta di supporto non esiste.");
                    request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("message", "Errore nel recupero della richiesta.");
                request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
            }
        }
        else if ("aggiornaStato".equals(action)) {
            String idRichiesta = request.getParameter("id");
            String nuovoStato = request.getParameter("nuovoStato");

            if (idRichiesta == null || idRichiesta.isEmpty() || nuovoStato == null || nuovoStato.isEmpty()) {
                request.setAttribute("message", "ID richiesta o stato non valido.");
                request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                return;
            }

            try {
                SupportRequest supportRequest = supportRequestDAO.getSupportRequestById(Integer.parseInt(idRichiesta));

                if (supportRequest != null) {
                    StatoSupporto statoEnum = StatoSupporto.fromString(nuovoStato);

                    if (statoEnum != null) {
                        supportRequest.setStato(statoEnum);
                        supportRequestDAO.updateSupportRequest(supportRequest);
                        request.setAttribute("message", "Lo stato della richiesta è stato aggiornato a: " + nuovoStato);
                    } else {
                        request.setAttribute("message", "Stato non valido.");
                        request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("message", "La richiesta di supporto non esiste.");
                }
            } catch (SQLException e) {
                request.setAttribute("message", "Errore nell'aggiornamento dello stato della richiesta.");
            }
            request.getRequestDispatcher("/supportoInterface/catalogoRichiesteSupporto.jsp").forward(request, response);
        }
        else if ("salvaRichiesta".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            String stato = request.getParameter("stato");

            if (name == null || email == null || description == null || stato == null) {
                request.setAttribute("message", "Dati della richiesta incompleti.");
                request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
                return;
            }

            SupportRequest newRequest = new SupportRequest(name, email, description, new java.sql.Date(System.currentTimeMillis()), 
                                                          "12:00", StatoSupporto.fromString(stato));

            try {
                supportRequestDAO.saveSupportRequest(newRequest);
                request.setAttribute("message", "Richiesta di supporto inviata con successo.");
                request.getRequestDispatcher("/supportoInterface/catalogoRichiesteSupporto.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("message", "Errore nel salvataggio della richiesta di supporto.");
                request.getRequestDispatcher("/error/MessaggioErrore.jsp").forward(request, response);
            }
        }
    }
}
