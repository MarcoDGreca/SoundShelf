package control;

import entity.Cart;
import entity.Product;
import entity.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cart")
public class CarrelloControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
    	productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        if (action != null) {
            int productId = Integer.parseInt(request.getParameter("productId"));
			Product product = null;
			try {
				product = productDAO.getProductbyId(productId);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			switch (action) {
			    case "add":
			        cart.addProduct(product);
			        break;
			    case "remove":
			        cart.removeProduct(product);
			        break;
			    case "update":
			        int quantity = Integer.parseInt(request.getParameter("quantity"));
			        cart.updateQuantity(product, quantity);
			        break;
			    case "clear":
			        cart.clear();
			        break;
			}
        }

        response.sendRedirect("cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
