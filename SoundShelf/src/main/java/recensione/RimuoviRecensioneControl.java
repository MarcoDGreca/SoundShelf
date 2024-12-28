package recensione;

import utente.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteReview")
public class RimuoviRecensioneControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        Review review = null;
		try {
			review = reviewDAO.getReviewById(reviewId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        if (review != null && user.getEmail().equals(review.getEmailCliente())) {
            try {
				reviewDAO.deleteReview(reviewId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }

        response.sendRedirect("home");
    }
}
