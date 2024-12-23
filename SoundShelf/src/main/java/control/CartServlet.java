package control;

import model.Cart;
import model.Ticket;
import model.TicketDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TicketDAO ticketDAO;

    @Override
    public void init() throws ServletException {
        ticketDAO = new TicketDAO();
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
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
			Ticket ticket = ticketDAO.getTicketById(ticketId);

			switch (action) {
			    case "add":
			        cart.addTicket(ticket);
			        break;
			    case "remove":
			        cart.removeTicket(ticket);
			        break;
			    case "update":
			        int quantity = Integer.parseInt(request.getParameter("quantity"));
			        cart.updateQuantity(ticket, quantity);
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
