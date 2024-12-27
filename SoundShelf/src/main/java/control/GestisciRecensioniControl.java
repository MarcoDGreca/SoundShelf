package control;

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

@WebServlet("/gestisciRecensioniControl")
public class GestisciRecensioniControl extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Review> recensioni = reviewDAO.getAllReviews();
            request.setAttribute("recensioni", recensioni);
            request.getRequestDispatcher("/paginaRecensioni.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
           
            request.setAttribute("message", "Errore nel recupero delle recensioni.");
            request.getRequestDispatcher("/MessaggioErrore.jsp").forward(request, response);
        }
    }
}
