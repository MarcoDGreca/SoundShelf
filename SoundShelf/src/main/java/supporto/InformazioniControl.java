package supporto;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/informazioniControl")
public class InformazioniControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private SupportRequestDAO supportRequestDAO;

    @Override
    public void init() throws ServletException {
        supportRequestDAO = new SupportRequestDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeRichiesta = request.getParameter("name");
        String informazioniAggiuntive = request.getParameter("informazioniAggiuntive");
        String rispostaUtente = request.getParameter("rispostaUtente");

        if (nomeRichiesta == null || nomeRichiesta.isEmpty()) {
            request.setAttribute("message", "Nome della richiesta non valido.");
            request.getRequestDispatcher("view/error/messaggioErrore.jsp").forward(request, response);
            return;
        }

        try {
            SupportRequest supportRequest = supportRequestDAO.getSupportRequestById(Integer.parseInt(nomeRichiesta));

            if (supportRequest != null) {
                supportRequest.setInformazioniAggiuntive(informazioniAggiuntive);

                if (rispostaUtente != null && !rispostaUtente.isEmpty()) {
                    supportRequest.setRispostaUtente(rispostaUtente);
                }

                supportRequestDAO.updateSupportRequest(supportRequest);

                supportRequest.setStato(StatoSupporto.IN_LAVORAZIONE);
                supportRequestDAO.updateSupportRequest(supportRequest);

                request.setAttribute("message", "Le informazioni sono state inviate con successo. La richiesta è ora in elaborazione.");
            } else {
                request.setAttribute("message", "La richiesta di supporto non esiste.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Errore nell'aggiornamento della richiesta.");
        } catch (NumberFormatException e) {
            request.setAttribute("message", "ID richiesta non valido.");
        }

        request.getRequestDispatcher("view/supportoInterface/richiestaSupportoView.jsp").forward(request, response);
    }
}
