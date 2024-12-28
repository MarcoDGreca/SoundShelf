package prodotti;

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
        try {
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
                    Artist artist = productDAO.findArtistByName(artistName);
                    if (artist != null) {
                        newProduct.addArtist(artist);
                    } else {
                        request.setAttribute("errorMessage", "Artista " + artistName + " non trovato.");
                        request.getRequestDispatcher("/error/MessageError.jsp").forward(request, response);
                        return;
                    }
                }
            }

            if (genreNames != null) {
                for (String genreName : genreNames) {
                    Genre genre = productDAO.findGenreByName(genreName);
                    if (genre != null) {
                        newProduct.addGenre(genre);
                    } else {
                        request.setAttribute("errorMessage", "Genere " + genreName + " non trovato.");
                        request.getRequestDispatcher("/error/MessageError.jsp").forward(request, response);
                        return;
                    }
                }
            }

            productDAO.saveProduct(newProduct);

            response.sendRedirect("prodotti/gestisciCatalogoProdottiControl");
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Formato non valido per uno dei campi numerici.");
            request.getRequestDispatcher("/error/MessageError.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Errore durante il salvataggio del prodotto nel database.");
            request.getRequestDispatcher("/error/MessageError.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Si è verificato un errore inaspettato.");
            request.getRequestDispatcher("/error/MessageError.jsp").forward(request, response);
        }
    }
}
