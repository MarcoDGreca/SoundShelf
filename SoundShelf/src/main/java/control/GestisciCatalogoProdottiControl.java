package control;

import entity.Product;
import entity.ProductDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.List;

public class GestisciCatalogoProdottiControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        this.productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products;
        try {
            products = productDAO.getAllProducts();
        } catch (SQLException e) {
            throw new ServletException("Errore durante il recupero dei prodotti", e);
        }
        request.setAttribute("products", products);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/catalogoProdotti.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }
}
