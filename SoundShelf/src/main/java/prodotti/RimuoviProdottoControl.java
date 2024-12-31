package prodotti;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rimuoviProdottoControl")
public class RimuoviProdottoControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productCodeStr = request.getParameter("productId");

        if (productCodeStr != null) {
            try {
                int productCode = Integer.parseInt(productCodeStr);
                Product productExists = productDAO.getProductById(productCode);
                if (productExists != null) {
                    productDAO.deleteProduct(productCode);
                    response.sendRedirect("gestisciCatalogoProdottiControl");
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error removing product");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product code is required");
        }
    }
}
