package recensione;

import ordini.OrderDAO;
import prodotti.Product;
import utente.Utente;
import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/addReview")
public class RecensioniControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("utenteInterface/loginForm.jsp");
            return;
        }

        List<Product> purchasedProduct = orderDAO.getPurchasedProductsByEmail(user.getEmail());
        request.setAttribute("purchasedProducts", purchasedProduct);
        request.getRequestDispatcher("/recensioneInterface/recensioneForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("utenteInterface/loginForm.jsp");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = InputSanitizer.sanitize(request.getParameter("comment"));

        Review review = new Review();
        review.setCodiceProdotto(productId);
        review.setEmailCliente(user.getEmail());
        review.setVotazione(rating);
        review.setTesto(comment);
        review.setDataRecensione(new Date(System.currentTimeMillis()));

        try {
			reviewDAO.saveReview(review);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        response.sendRedirect("control/home");
    }
}
