package utente;

import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profileControl")
public class ModificaDatiUtenteControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteRegistratoDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UtenteRegistratoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/utenteInterface/loginForm.jsp");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/utenteInterface/profileView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/utenteInterface/loginForm.jsp");
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
        response.sendRedirect(request.getContextPath() + "/utente/profileControl");
    }
}
