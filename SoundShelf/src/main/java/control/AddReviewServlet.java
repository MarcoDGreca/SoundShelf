package control;

import model.Review;
import model.ReviewDAO;
import model.Utente;
import util.InputSanitizer;
import model.Event;
import model.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Event> purchasedEvents = orderDAO.getPurchasedEventsByUser(user.getEmail());
        request.setAttribute("purchasedEvents", purchasedEvents);
        request.getRequestDispatcher("/addReview.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int eventId = Integer.parseInt(request.getParameter("eventId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = InputSanitizer.sanitize(request.getParameter("comment"));

        Review review = new Review();
        review.setCodiceEvento(eventId);
        review.setEmailCliente(user.getEmail());
        review.setVotazione(rating);
        review.setTesto(comment);
        review.setDataRecensione(new Date(System.currentTimeMillis()));

        reviewDAO.addReview(review);
        response.sendRedirect("home");
    }
}
