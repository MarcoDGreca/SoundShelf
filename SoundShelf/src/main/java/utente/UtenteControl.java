package utente;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/utenteControl")
public class UtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDAO utenteDAO;

    @Override
    public void init() throws ServletException {
        utenteDAO = new UtenteDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email != null && !email.isEmpty()) {
            Utente utente = utenteDAO.getUserByEmail(email);
            if (utente != null) {
                request.setAttribute("utente", utente);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/visualizzaUtente.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("errore.jsp");
            }
        } else {
            response.sendRedirect("errore.jsp");
        }
    }
}
