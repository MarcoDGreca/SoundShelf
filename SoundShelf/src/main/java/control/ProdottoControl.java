package control;

import entity.Product;
import entity.ProductDAO;
import entity.Review;
import entity.ReviewDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/prodotto")
public class ProdottoControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productCode = Integer.parseInt(request.getParameter("productCode"));
        
        Product product = null;
		try {
			product = productDAO.getProductById(productCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        List<Review> reviews = null;
		try {
			reviews = reviewDAO.getReviewsByProductId(productCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/ProductView.jsp").forward(request, response);
    }
}
