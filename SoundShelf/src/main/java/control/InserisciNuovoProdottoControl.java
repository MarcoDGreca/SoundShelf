package control;

import entity.Product;
import entity.ProductDAO;
import entity.Artist;
import entity.Genre;
import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/InserisciNuovoProdottoControl")
public class InserisciNuovoProdottoControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productCode = Integer.parseInt(request.getParameter("productCode"));
        String name = InputSanitizer.sanitize(request.getParameter("name"));
        String description = InputSanitizer.sanitize(request.getParameter("description"));
        double salePrice = Double.parseDouble(request.getParameter("salePrice"));
        double originalPrice = Double.parseDouble(request.getParameter("originalPrice"));
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));
        String releaseDate = InputSanitizer.sanitize(request.getParameter("releaseDate"));
        String image = InputSanitizer.sanitize(request.getParameter("image"));

        String[] artistNames = request.getParameterValues("artists");
        String[] genreNames = request.getParameterValues("genres");

        Product newProduct = new Product();
        newProduct.setProductCode(productCode);
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setSalePrice(salePrice);
        newProduct.setOriginalPrice(originalPrice);
        newProduct.setAvailability(availability);
        newProduct.setReleaseDate(releaseDate);
        newProduct.setImage(image);

        if (artistNames != null) {
            for (String artistName : artistNames) {
                Artist artist = null;
				try {
					artist = productDAO.findArtistByName(artistName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
                if (artist != null) {
                    newProduct.addArtist(artist);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Artista " + artistName + " non trovato.");
                    return;
                }
            }
        }

        if (genreNames != null) {
            for (String genreName : genreNames) {
                Genre genre = null;
				try {
					genre = productDAO.findGenreByName(genreName);
				} catch (SQLException e) {
					e.printStackTrace();
				}
                if (genre != null) {
                    newProduct.addGenre(genre);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Genere " + genreName + " non trovato.");
                    return;
                }
            }
        }

        try {
			productDAO.saveProduct(newProduct);
		} catch (SQLException e) {
			e.printStackTrace();
		}
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Prodotto inserito con successo.");
        }
}
