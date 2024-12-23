package control;

import model.Utente;
import model.UtenteDAO;
import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UtenteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String nome = InputSanitizer.sanitize(request.getParameter("nome"));
        String cognome = InputSanitizer.sanitize(request.getParameter("cognome"));
        String indirizzo = InputSanitizer.sanitize(request.getParameter("indirizzo"));
        String telefono = InputSanitizer.sanitize(request.getParameter("telefono"));

        user.setNome(nome);
        user.setCognome(cognome);
        user.setIndirizzo(indirizzo);
        user.setTelefono(telefono);

        userDAO.updateUser(user);
        session.setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/profile");
    }
}
