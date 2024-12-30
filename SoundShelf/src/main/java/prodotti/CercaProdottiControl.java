package prodotti;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.InputSanitizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchProducts")
public class CercaProdottiControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = InputSanitizer.sanitize(request.getParameter("name"));
        String genreParam = InputSanitizer.sanitize(request.getParameter("genre"));
        String artistParam = InputSanitizer.sanitize(request.getParameter("artist"));

        List<String> genres = (genreParam != null && !genreParam.isEmpty()) 
            ? List.of(genreParam.split(",")) 
            : null;
        List<String> artists = (artistParam != null && !artistParam.isEmpty()) 
            ? List.of(artistParam.split(","))
            : null;

        List<Product> products = null;
        try {
            products = productDAO.searchProducts(name, genres, artists);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore nella ricerca dei prodotti.");
            request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
            return;
        }

        if (products == null || products.isEmpty()) {
            request.setAttribute("errorMessage", "Nessun prodotto trovato.");
            request.getRequestDispatcher("/view/error/messaggioErrore.jsp").forward(request, response);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (Product product : products) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("productCode", product.getProductCode());
                jsonObject.put("name", product.getName());
                jsonObject.put("artists", product.getArtists().stream()
                        .map(artistObj -> artistObj.getStageName() != null 
                                ? artistObj.getStageName() 
                                : artistObj.getFirstName() + " " + artistObj.getLastName())
                        .toArray());
                jsonObject.put("releaseDate", product.getReleaseDate());
                jsonObject.put("description", product.getDescription());
                jsonObject.put("availability", product.getAvailability());
                jsonObject.put("salePrice", product.getSalePrice());
                jsonObject.put("originalPrice", product.getOriginalPrice());
                jsonObject.put("genres", product.getGenres().stream()
                        .map(Genre::getName)
                        .toArray());
                jsonObject.put("image", product.getImage());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonArray.toString());
    }
}
