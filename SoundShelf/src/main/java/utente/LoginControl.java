package utente;

import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtenteRegistratoDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UtenteRegistratoDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = InputSanitizer.sanitize(request.getParameter("email"));
        String password = InputSanitizer.sanitize(request.getParameter("password"));

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errorMessage", "Email e password sono obbligatori.");
            request.getRequestDispatcher("/utenteInterface/loginForm.jsp").forward(request, response);
            return;
        }

        String hashedPassword = hashPassword(password);
        UtenteRegistrato user = userDAO.authenticate(email, hashedPassword);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/control/home");
        } else {
            request.setAttribute("errorMessage", "Email o password non validi.");
            request.getRequestDispatcher("/utenteInterface/loginForm.jsp").forward(request, response);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

