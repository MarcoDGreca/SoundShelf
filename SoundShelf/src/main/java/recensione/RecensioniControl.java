package recensione;

import ordini.ElementoOrdineDAO;
import ordini.OrderDAO;
import prodotti.Product;
import prodotti.ProductDAO;
import utente.UtenteRegistrato;
import utente.UtenteRegistratoDAO;
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
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAO();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("view/utenteInterface/loginForm.jsp");
            return;
        }
        
        int productId = Integer.parseInt(request.getParameter("productCode"));

        Product purchasedProduct = null;;
		try {
			purchasedProduct = productDAO.getProductById(productId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        request.setAttribute("purchasedProduct", purchasedProduct);
        request.getRequestDispatcher("view/recensioneInterface/recensioneForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UtenteRegistrato user = (UtenteRegistrato) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("view/utenteInterface/loginForm.jsp");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = InputSanitizer.sanitize(request.getParameter("comment"));

        Review review = new Review();
        review.setIdProdotto(productId);
        review.setEmailCliente(user.getEmail());
        review.setVoto(rating);
        review.setDescrizione(comment);
        review.setDataRecensione(new Date(System.currentTimeMillis()));

        try {
			reviewDAO.saveReview(review);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        response.sendRedirect("listaOrdiniUtente");
    }
}
