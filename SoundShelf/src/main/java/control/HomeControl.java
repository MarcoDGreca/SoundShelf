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
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
    	productDAO = new ProductDAO();
        reviewDAO = new ReviewDAO();
        System.out.println("HomeServlet initialized.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HomeServlet doGet called.");
        List<Product> products = null;
		try {
			products = productDAO.getAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        List<Review> reviews = null;
		try {
			reviews = reviewDAO.getAllReviews();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        request.setAttribute("events", products);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}

