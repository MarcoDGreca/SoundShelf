package control;

import entity.SupportRequest;
import entity.SupportRequestDAO;
import entity.StatoSupporto;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/richiestaSupporto")
public class RichiestaSupportoControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private SupportRequestDAO supportRequestDAO;

    @Override
    public void init() throws ServletException {
        supportRequestDAO = new SupportRequestDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String description = request.getParameter("description");
        
        java.util.Date date = new java.util.Date();
        Date dataInvio = new Date(date.getTime());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String orarioInvio = timeFormat.format(date);

        StatoSupporto stato = StatoSupporto.IN_LAVORAZIONE;

        SupportRequest supportRequest = new SupportRequest(name, email, description, dataInvio, orarioInvio, stato);

        try {
            supportRequestDAO.saveSupportRequest(supportRequest);
            request.setAttribute("message", "La tua richiesta di supporto è stata inviata con successo.");
        } catch (Exception e) {
            request.setAttribute("message", "Si è verificato un errore nell'invio della richiesta. Riprova.");
            e.printStackTrace();
        }
        request.getRequestDispatcher("/paginaConfermaRichiesta.jsp").forward(request, response);
    }
}
