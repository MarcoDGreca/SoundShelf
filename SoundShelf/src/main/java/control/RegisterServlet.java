package control;

import model.Ruolo;
import model.Utente;
import model.UtenteDAO;
import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UtenteDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UtenteDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = InputSanitizer.sanitize(request.getParameter("email"));
        String nome = InputSanitizer.sanitize(request.getParameter("nome"));
        String password = InputSanitizer.sanitize(request.getParameter("password"));
        String cognome = InputSanitizer.sanitize(request.getParameter("cognome"));
        String via = InputSanitizer.sanitize(request.getParameter("via"));
        String cap = InputSanitizer.sanitize(request.getParameter("cap"));
        String numero = InputSanitizer.sanitize(request.getParameter("numero"));
        String provincia = InputSanitizer.sanitize(request.getParameter("provincia"));
        String citta = InputSanitizer.sanitize(request.getParameter("citta"));
        String telefono = InputSanitizer.sanitize(request.getParameter("telefono"));
        String numeroT = InputSanitizer.sanitize(request.getParameter("numeroT"));
        
        String indirizzo = via + ", " + numero + ", " + cap + ", " + citta + ", " + provincia;

        String hashedPassword = hashPassword(password);

        Utente newUser = new Utente(email, hashedPassword, nome, cognome, indirizzo, telefono, numeroT, Ruolo.UTENTE);

        userDAO.addUser(newUser);

        response.sendRedirect(request.getContextPath() + "/home");
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
