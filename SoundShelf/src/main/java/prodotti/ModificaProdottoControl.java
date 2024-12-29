package prodotti;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ModificaProdottoControl")
public class ModificaProdottoControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productCodeStr = request.getParameter("productCode");
        if (productCodeStr != null) {
            try {
                int productCode = Integer.parseInt(productCodeStr);
                Product product = productDAO.getProductById(productCode);
                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("view/prodottiInterface/modificaProdottoForm.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching product");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product code is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productCodeStr = request.getParameter("productCode");
        String name = request.getParameter("name");
        String releaseDate = request.getParameter("releaseDate");
        String description = request.getParameter("description");
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));
        double salePrice = Double.parseDouble(request.getParameter("salePrice"));
        double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        String supportedDevice = request.getParameter("supportedDevice");
        String image = request.getParameter("image");

        try {
            int productCode = Integer.parseInt(productCodeStr);
            Product product = productDAO.getProductById(productCode);
            if (product != null) {
                product.setName(name);
                product.setReleaseDate(releaseDate);
                product.setDescription(description);
                product.setAvailability(availability);
                product.setSalePrice(salePrice);
                product.setOriginalPrice(originalPrice);
                product.setSupportedDevice(supportedDevice);
                product.setImage(image);
                
                productDAO.updateProduct(product);

                response.sendRedirect("/gestisciCatalogoProdottiControl");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating product");
        }
    }
}
