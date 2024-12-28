package rimborsi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/richiestaRimborsoControl")
public class RichiestaRimborsoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RefoundRequestDAO refoundRequestDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        refoundRequestDAO = new RefoundRequestDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<RefoundRequest> refoundRequests = refoundRequestDAO.getAllRichiesteRimborso();
            request.setAttribute("refoundRequests", refoundRequests);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Si è verificato un errore durante il recupero delle richieste di rimborso.");
        }
        request.getRequestDispatcher("/rimborsoInterface/richiestaRimborsoForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderCode = Integer.parseInt(request.getParameter("orderCode"));
            int productCode = Integer.parseInt(request.getParameter("productCode"));
            String reason = request.getParameter("reason");
            String iban = request.getParameter("iban");

            RefoundRequest refoundRequest = new RefoundRequest(0, reason, iban, StatoRimborso.IN_LAVORAZIONE, orderCode, productCode);
            refoundRequestDAO.saveRichiestaRimborso(refoundRequest);
            request.setAttribute("message", "Richiesta di rimborso inviata con successo.");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("error", "Si è verificato un errore durante l'invio della richiesta di rimborso.");
        }
        doGet(request, response);
    }
}
