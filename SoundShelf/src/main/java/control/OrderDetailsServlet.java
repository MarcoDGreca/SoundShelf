package control;

import model.Order;
import model.OrderDAO;
import model.OrderDetail;
import model.OrderDetailDAO;
import model.Ticket;
import model.TicketDAO;
import model.Event;
import model.EventDAO;
import model.Utente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/orderDetails")
public class OrderDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private TicketDAO ticketDAO;
    private EventDAO eventDAO;
    private OrderDetailDAO orderDetailDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAO();
        ticketDAO = new TicketDAO();
        eventDAO = new EventDAO();
        orderDetailDAO = new OrderDetailDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente user = (Utente) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String emailCliente = user.getEmail();
        List<Order> orders = orderDAO.getOrdersByEmail(emailCliente);
        request.setAttribute("orders", orders);

        for (Order order : orders) {
            List<OrderDetail> orderDetails = orderDetailDAO.getOrderDetailsByOrderId(order.getCodiceOrdine());
            for (OrderDetail detail : orderDetails) {
                Ticket ticket = ticketDAO.getTicketByIdDeleted(detail.getCodiceBiglietto());
                Event event = eventDAO.getEventByIdDeleted(ticket.getCodiceEvento());
                request.setAttribute("orderDetail_" + detail.getCodiceBiglietto(), detail);
                request.setAttribute("ticket_" + detail.getCodiceBiglietto(), ticket);
                request.setAttribute("event_" + ticket.getCodiceEvento(), event);
            }
        }

        request.getRequestDispatcher("/orderDetails.jsp").forward(request, response);
    }
}

