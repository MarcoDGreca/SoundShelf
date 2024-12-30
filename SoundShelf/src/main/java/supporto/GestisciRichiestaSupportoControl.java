package supporto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utente.UtenteRegistratoDAO;

@WebServlet("/gestisciRichiestaSupportoControl")
public class GestisciRichiestaSupportoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SupportRequestDAO supportRequestDAO;
    private UtenteRegistratoDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        supportRequestDAO = new SupportRequestDAO();
        clienteDAO = new UtenteRegistratoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<SupportRequest> richieste = supportRequestDAO.getAllSupportRequests();
            request.setAttribute("richieste", richieste);
            request.getRequestDispatcher("view/supportoInterface/catalogoRichiesteSupporto.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Errore nel recupero delle richieste di supporto.");
            request.getRequestDispatcher("view/error/MessaggioErrore.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("richiediInformazioni".equals(action)) {
        	String idRichiesta = request.getParameter("idRichiesta");

            if (idRichiesta == null || idRichiesta.isEmpty()) {
                request.setAttribute("message", "Nome della richiesta non valido.");
                request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
                return;
            }

            try {
                SupportRequest supportRequest = supportRequestDAO.getSupportRequestById(Integer.parseInt(idRichiesta));

                if (supportRequest != null) {
                    request.setAttribute("richiesta", supportRequest);
                    request.getRequestDispatcher("view/supportoInterface/richiestaSupportoView.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "La richiesta di supporto non esiste.");
                    request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                request.setAttribute("message", "Errore nel recupero della richiesta.");
                request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
            }
        }
        else if ("aggiornaStato".equals(action)) {
            String idRichiesta = request.getParameter("id");
            String nuovoStato = request.getParameter("nuovoStato");

            if (idRichiesta == null || idRichiesta.isEmpty() || nuovoStato == null || nuovoStato.isEmpty()) {
                request.setAttribute("message", "ID richiesta o stato non valido.");
                request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
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
                        request.getRequestDispatcher("view/error/MessaggioErrore.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("message", "La richiesta di supporto non esiste.");
                }
            } catch (SQLException e) {
                request.setAttribute("message", "Errore nell'aggiornamento dello stato della richiesta.");
            }
            request.getRequestDispatcher("view/supportoInterface/catalogoRichiesteSupporto.jsp").forward(request, response);
        }
        else if ("salvaRichiesta".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            String stato = request.getParameter("stato");

            if (name == null || email == null || description == null || stato == null) {
                request.setAttribute("message", "Dati della richiesta incompleti.");
                request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
                return;
            }
            
            int idCliente = clienteDAO.getUserIdByEmail(email);
            SupportRequest newRequest = new SupportRequest(description, new java.sql.Date(System.currentTimeMillis()), 
                                                          "12:00", StatoSupporto.fromString(stato), idCliente);

            try {
                supportRequestDAO.saveSupportRequest(newRequest);
                request.setAttribute("message", "Richiesta di supporto inviata con successo.");
                request.getRequestDispatcher("view/supportoInterface/catalogoRichiesteSupporto.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("message", "Errore nel salvataggio della richiesta di supporto.");
                request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
            }
        }
    }
}
