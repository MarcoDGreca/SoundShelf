package control;

import entity.Product;
import entity.ProductDAO;
import entity.Review;
import entity.ReviewDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/home")
public class HomeControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = null;
        try {
            products = productDAO.getAllProducts();
        } catch (SQLException e) {
            request.setAttribute("message", "Errore nel recupero dei prodotti.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
            return;
        }  
        request.setAttribute("events", products);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
