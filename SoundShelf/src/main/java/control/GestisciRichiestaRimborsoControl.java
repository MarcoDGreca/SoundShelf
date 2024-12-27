package control;

import entity.RefoundRequest;
import entity.RefoundRequestDAO;
import entity.StatoRimborso;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/manageRefund")
public class GestisciRichiestaRimborsoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RefoundRequestDAO refoundRequestDAO;

    @Override
    public void init() throws ServletException {
        refoundRequestDAO = new RefoundRequestDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productCode = Integer.parseInt(request.getParameter("productCode"));
        String action = request.getParameter("action");

        try {
            RefoundRequest refoundRequest = refoundRequestDAO.getRefoundRequest(productCode);

            if (refoundRequest != null) {
                if ("accept".equalsIgnoreCase(action)) {
                    refoundRequest.setStato(StatoRimborso.ACCETTATO);
                } else if ("reject".equalsIgnoreCase(action)) {
                    refoundRequest.setStato(StatoRimborso.RIFIUTATO);
                } else {
                    response.sendRedirect("refundManagement.jsp?error=invalidaction");
                    return;
                }

                refoundRequestDAO.updateRefoundRequest(refoundRequest);
                response.sendRedirect("refundManagement.jsp?success=" + action);
            } else {
                response.sendRedirect("refundManagement.jsp?error=notfound");
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Errore nella gestione del rimborso.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
